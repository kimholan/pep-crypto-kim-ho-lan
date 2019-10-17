package spec.scenario.signedencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import spec.BasicStep;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class RequireMatchingMigrationIdForSignedEncryptedPseudonymMigrationStep
        extends BasicStep {

    private String migrationID;

    @Step("Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest")
    public void requireIdenticalMigrationIdForAllEpMigrationKeys() {
        putScenarioData(RequireMatchingMigrationIdForSignedEncryptedPseudonymMigrationStep.class, this);

        var templateRequest = getScenarioData(OASSignedEncryptedPseudonymMigrationRequest.class);
        var serviceProviderKeys = templateRequest.getServiceProviderKeys();
        migrationID = templateRequest.getMigrationID();

        assertIdenticalMigrationIdForAllEpMigrationKeys(serviceProviderKeys);
    }

    @Step("Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest-template")
    public void requireIdenticalMigrationIdForAllEpMigrationKeysInTemplate() {
        putScenarioData(RequireMatchingMigrationIdForSignedEncryptedPseudonymMigrationStep.class, this);

        var template = getScenarioData(TemplateOASSignedEncryptedPseudonymMigrationRequestStep.class);
        var request = template.newOasSignedEncryptedPseudonymMigrationRequestFromTemplate();
        var serviceProviderKeys = request.getServiceProviderKeys();
        migrationID = request.getMigrationID();

        assertIdenticalMigrationIdForAllEpMigrationKeys(serviceProviderKeys);
    }

    public void assertIdenticalMigrationIdForAllEpMigrationKeys(List<String> serviceProviderKeys) {
        var asserter = getAsserter();

        var sourceForOtherMigrationId = Optional.ofNullable(serviceProviderKeys).orElseGet(Collections::emptyList)
                                                .stream()
                                                .filter(filterByHeader("Type", "EP migration source"))
                                                .filter(filterByHeader("MigrationID", migrationID).negate())
                                                .collect(toList());
        asserter.addAssertTrue("\nMigrationID for 'EP migration source'-keys must match MigrationID '" + migrationID + "':\n\t" + serviceProviderKeys, sourceForOtherMigrationId.isEmpty());

        var targetForOtherMigrationId = Optional.ofNullable(serviceProviderKeys).orElseGet(Collections::emptyList)
                                                .stream()
                                                .filter(filterByHeader("Type", "EP migration target"))
                                                .filter(filterByHeader("MigrationID", migrationID).negate())
                                                .collect(toList());
        asserter.addAssertTrue("\nMigrationID for 'EP migration target'-keys must match MigrationID '" + migrationID + "':\n\t" + serviceProviderKeys, targetForOtherMigrationId.isEmpty());
    }

    private Predicate<String> filterByHeader(String headerName, String headerValue) {
        return it -> Optional.of(headerValue)
                             .map(itt -> headerName + ": " + itt)
                             .filter(it::contains)
                             .isPresent();
    }

}
