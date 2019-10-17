package spec.scenario.signeddirectencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymMigrationRequest;
import spec.BasicStep;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class RequireMatchingMigrationIdForSignedDirectEncryptedPseudonymMigrationStep
        extends BasicStep {

    private String migrationID;

    @Step("Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest")
    public void requireIdenticalMigrationIdForAllEpMigrationKeys() {
        putScenarioData(RequireMatchingMigrationIdForSignedDirectEncryptedPseudonymMigrationStep.class, this);

        var templateRequest = getScenarioData(OASSignedDirectEncryptedPseudonymMigrationRequest.class);
        var serviceProviderKeys = templateRequest.getServiceProviderKeys();
        migrationID = templateRequest.getMigrationID();

        assertIdenticalMigrationIdForAllEpMigrationKeys(serviceProviderKeys);
    }

    @Step("Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template")
    public void requireIdenticalMigrationIdForAllEpMigrationKeysInTemplate() {
        putScenarioData(RequireMatchingMigrationIdForSignedDirectEncryptedPseudonymMigrationStep.class, this);

        var template = getScenarioData(TemplateOASSignedDirectEncryptedPseudonymMigrationRequestStep.class);
        var request = template.newOasSignedDirectEncryptedPseudonymMigrationRequestFromTemplate();
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
