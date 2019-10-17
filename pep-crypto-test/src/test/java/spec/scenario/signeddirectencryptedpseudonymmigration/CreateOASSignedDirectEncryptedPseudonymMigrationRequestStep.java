package spec.scenario.signeddirectencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymMigrationRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class CreateOASSignedDirectEncryptedPseudonymMigrationRequestStep
        extends BasicStep {

    @Step("Create OASSignedDirectEncryptedPseudonymMigrationRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);

        // Request data
        var serviceProviderKeys = (List<String>) requestData.get("serviceProviderKeys");
        var schemeKeys = (Map<String, byte[]>) requestData.get("schemeKeys");
        var signedDirectEncryptedPseudonym = (byte[]) requestData.get("signedDirectEncryptedPseudonym");
        var authorizedParty = (String) requestData.get("authorizedParty");
        var migrationID = (String) requestData.get("migrationID");
        var targetMigrant = (String) requestData.get("targetMigrant");
        var targetMigrantKeySetVersion = (String) requestData.get("targetMigrantKeySetVersion");

        var pseudonymEnvelope = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(signedDirectEncryptedPseudonym);
        var asn1Body = pseudonymEnvelope.asn1Body();
        var sourceMigrant = asn1Body.getRecipient();
        var sourceMigrantKeySetVersion = asn1Body.getRecipientKeySetVersion();

        // SDEPM
        var sdepmRequest = new OASSignedDirectEncryptedPseudonymMigrationRequest();

        sdepmRequest.setSignedDirectEncryptedPseudonym(signedDirectEncryptedPseudonym);
        sdepmRequest.setServiceProviderKeys(serviceProviderKeys);
        sdepmRequest.setSchemeKeys(schemeKeys);
        sdepmRequest.setMigrationID(migrationID);
        sdepmRequest.setAuthorizedParty(authorizedParty);
        sdepmRequest.setTargetMigrant(targetMigrant);
        sdepmRequest.setTargetMigrantKeySetVersion(targetMigrantKeySetVersion);

        putScenarioData(OASSignedDirectEncryptedPseudonymMigrationRequest.class, sdepmRequest);

        // SDEP
        var sdepRequest = new OASSignedDirectEncryptedPseudonymRequest();
        var sepServiceProviderKeys = serviceProviderKeys.stream()
                                                        .filter(it -> !it.contains("EP migration"))
                                                        .collect(toList());
        sdepRequest.setAuthorizedParty(authorizedParty);
        sdepRequest.setSignedDirectEncryptedPseudonym(signedDirectEncryptedPseudonym);
        sdepRequest.setServiceProviderKeys(sepServiceProviderKeys);
        sdepRequest.setSchemeKeys(schemeKeys);

        putScenarioData(OASSignedDirectEncryptedPseudonymRequest.class, sdepRequest);

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
