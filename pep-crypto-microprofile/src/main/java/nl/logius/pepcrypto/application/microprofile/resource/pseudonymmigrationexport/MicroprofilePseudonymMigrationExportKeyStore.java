package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationexport;

import nl.logius.pepcrypto.api.event.ApiEvent;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileAbstractKeyStore;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationSourceKeySelector;
import nl.logius.pepcrypto.lib.lang.PepExceptionCollector;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.AFTER;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.INVALID_MIGRATION_TARGET_KEY_SELECTION;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_MIGRATION_SOURCE_KEY_REQUIRED;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;

@ApplicationScoped
class MicroprofilePseudonymMigrationExportKeyStore
        extends MicroprofileAbstractKeyStore {

    @Inject
    private MicroprofileMigrationSourceKeySelector migrationSourceKeySelector;

    void processKeySelection(@Observes @ApiEvent(value = ASN1_MAPPING, phase = AFTER) MicroprofilePseudonymMigrationExportExchange exchange) {
        var collector = new PepExceptionCollector();

        collector.ignore(() -> requireCompleteMigrationTargetKeySelection(exchange));
        collector.ignore(() -> parseServiceProviderKeys(exchange));
        collector.ignore(() -> requireUniqueSourceOrRecipient(exchange));
        collector.ignore(() -> requireAtLeastOneServiceProviderKey(exchange));
        collector.ignore(() -> processServiceProviderKeySelection(exchange));

        collector.requireNoExceptions(MICROPROFILE_MODULE);
    }

    private void requireCompleteMigrationTargetKeySelection(MicroprofilePseudonymMigrationExportExchange exchange) {
        INVALID_MIGRATION_TARGET_KEY_SELECTION.requireFalse(exchange.isMigrationTargetSelectionInvalid());
    }

    private void requireAtLeastOneServiceProviderKey(MicroprofilePseudonymMigrationExportExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        MATCHING_MIGRATION_SOURCE_KEY_REQUIRED.requireFalse(parsedServiceProviderKeys.isEmpty());
    }

    private void processServiceProviderKeySelection(MicroprofilePseudonymMigrationExportExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        var mappedInput = exchange.getMappedInput();
        var requestMigrationId = exchange.getRequestMigrationId();
        var sourceRecipientKeyId = mappedInput.asAsn1RecipientKeyId()
                                              .migrationId(requestMigrationId);

        var requestTargetMigrant = exchange.getRequestTargetMigrant();
        var requestTargetMigrantKeySetVersion = exchange.getRequestTargetMigrantKeySetVersion();

        var migrationSourceKeys = migrationSourceKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, sourceRecipientKeyId);
        var migrationSourceKey = migrationSourceKeySelector.requireUniqueMatch(migrationSourceKeys, requestTargetMigrant, requestTargetMigrantKeySetVersion);

        exchange.setMigrationSourceKey(migrationSourceKey);

    }

}

