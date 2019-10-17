package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationimport;

import nl.logius.pepcrypto.api.event.ApiEvent;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileAbstractKeyStore;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationTargetKeySelector;
import nl.logius.pepcrypto.lib.lang.PepExceptionCollector;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.AFTER;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_MIGRATION_TARGET_KEY_REQUIRED;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;

@ApplicationScoped
class MicroprofilePseudonymMigrationImportKeyStore
        extends MicroprofileAbstractKeyStore {

    @Inject
    private MicroprofileMigrationTargetKeySelector migrationTargetKeySelector;

    void processKeySelection(@Observes @ApiEvent(value = ASN1_MAPPING, phase = AFTER) MicroprofilePseudonymMigrationImportExchange exchange) {
        var collector = new PepExceptionCollector();

        collector.ignore(() -> parseServiceProviderKeys(exchange));
        collector.ignore(() -> requireUniqueTargetOrRecipient(exchange));
        collector.ignore(() -> requireAtLeastOneServiceProviderKey(exchange));
        collector.ignore(() -> processServiceProviderKeySelection(exchange));

        collector.requireNoExceptions(MICROPROFILE_MODULE);
    }

    private void requireAtLeastOneServiceProviderKey(MicroprofilePseudonymMigrationImportExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        MATCHING_MIGRATION_TARGET_KEY_REQUIRED.requireFalse(parsedServiceProviderKeys.isEmpty());
    }

    private void processServiceProviderKeySelection(MicroprofilePseudonymMigrationImportExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        var mappedInput = exchange.getMappedInput();
        var migrationTargetTarget = mappedInput.asTargetAsn1RecipientKeyId();
        var migrationTargetKeys = migrationTargetKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, migrationTargetTarget);

        var sourceMigrant = exchange.getMappedInput().getSource();
        var sourceMigrantKeySetVersion = exchange.getMappedInput().getSourceKeySetVersion();
        var migrationTargetKey = migrationTargetKeySelector.filterByUniqueRecipientKeyId(migrationTargetKeys, sourceMigrant, sourceMigrantKeySetVersion);

        exchange.setMigrationTargetKey(migrationTargetKey);
    }

}

