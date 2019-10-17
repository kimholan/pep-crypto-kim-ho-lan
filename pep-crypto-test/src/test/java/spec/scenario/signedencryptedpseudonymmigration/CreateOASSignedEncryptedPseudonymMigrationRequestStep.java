package spec.scenario.signedencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymRequest;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1DeprecatedSignedEncryptedPseudonymEnvelope;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class CreateOASSignedEncryptedPseudonymMigrationRequestStep
        extends BasicStep {

    @Step("Create OASSignedEncryptedPseudonymMigrationRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);

        // Request data
        var serviceProviderKeys = (List<String>) requestData.get("serviceProviderKeys");
        var schemeKeys = (Map<String, byte[]>) requestData.get("schemeKeys");
        var signedEncryptedPseudonym = (byte[]) requestData.get("signedEncryptedPseudonym");
        var migrationID = (String) requestData.get("migrationID");
        var targetMigrant = (String) requestData.get("targetMigrant");
        var targetMigrantKeySetVersion = (String) requestData.get("targetMigrantKeySetVersion");

        var pseudonymEnvelope = Asn1DeprecatedSignedEncryptedPseudonymEnvelope.fromByteArray(signedEncryptedPseudonym);
        var asn1Body = pseudonymEnvelope.asn1Body();
        var sourceMigrant = asn1Body.getRecipient();
        var sourceMigrantKeySetVersion = asn1Body.getRecipientKeySetVersion();

        // SEPM
        var sepmRequest = new OASSignedEncryptedPseudonymMigrationRequest();

        sepmRequest.setSignedEncryptedPseudonym(signedEncryptedPseudonym);
        sepmRequest.setServiceProviderKeys(serviceProviderKeys);
        sepmRequest.setSchemeKeys(schemeKeys);
        sepmRequest.setMigrationID(migrationID);
        sepmRequest.setTargetMigrant(targetMigrant);
        sepmRequest.setTargetMigrantKeySetVersion(targetMigrantKeySetVersion);

        putScenarioData(OASSignedEncryptedPseudonymMigrationRequest.class, sepmRequest);

        // SEP
        var sepRequest = new OASSignedEncryptedPseudonymRequest();
        var sepServiceProviderKeys = serviceProviderKeys.stream()
                                                        .filter(it -> !it.contains("EP migration"))
                                                        .collect(toList());
        sepRequest.setSignedEncryptedPseudonym(signedEncryptedPseudonym);
        sepRequest.setServiceProviderKeys(sepServiceProviderKeys);
        sepRequest.setSchemeKeys(schemeKeys);

        putScenarioData(OASSignedEncryptedPseudonymRequest.class, sepRequest);

        // PE
        var peRequest = new OASPseudonymMigrationExportRequest();
        var peServiceProviderKeys = filterByMigrationKey(serviceProviderKeys,
                "EP migration source",
                migrationID,
                sourceMigrant, sourceMigrantKeySetVersion,
                targetMigrant, targetMigrantKeySetVersion
        );

        peRequest.setServiceProviderKeys(peServiceProviderKeys);
        peRequest.setMigrationID(migrationID);
        peRequest.setTargetMigrant(targetMigrant);
        peRequest.setTargetMigrantKeySetVersion(targetMigrantKeySetVersion);

        putScenarioData(OASPseudonymMigrationExportRequest.class, peRequest);

        // PI
        var piRequest = new OASPseudonymMigrationImportRequest();
        var piServiceProviderKeys = filterByMigrationKey(serviceProviderKeys,
                "EP migration target",
                migrationID,
                sourceMigrant, sourceMigrantKeySetVersion,
                targetMigrant, targetMigrantKeySetVersion
        );
        piRequest.setServiceProviderKeys(piServiceProviderKeys);

        putScenarioData(OASPseudonymMigrationImportRequest.class, piRequest);
    }

    private List<String> filterByMigrationKey(List<String> serviceProviderKeys, String keyType, String migrationID, String sourceMigrant, BigInteger sourceMigrantKeySetVersion, String targetMigrant, String targetMigrantKeySetVersion) {
        return serviceProviderKeys.stream()
                                  .filter(filterByHeader("Type", keyType))
                                  .filter(filterByHeader("SourceMigrant", sourceMigrant))
                                  .filter(filterByHeader("SourceMigrantKeySetVersion", String.valueOf(sourceMigrantKeySetVersion)))
                                  .filter(optionalFilterByHeader("TargetMigrant", targetMigrant))
                                  .filter(optionalFilterByHeader("TargetMigrantKeySetVersion", targetMigrantKeySetVersion))
                                  .filter(optionalFilterByHeader("MigrationID", migrationID))
                                  .collect(toList());
    }

    private Predicate<String> optionalFilterByHeader(String headerName, String headerValue) {
        return it -> headerValue == null || filterByHeader(headerName, headerValue).test(it);
    }

    private Predicate<String> filterByHeader(String headerName, String headerValue) {
        return it -> Optional.of(headerValue)
                             .map(itt -> headerName + ": " + itt)
                             .filter(it::contains)
                             .isPresent();
    }

}
