package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import nl.logius.pepcrypto.api.event.ApiEvent;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileAbstractKeyStore;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationSourceKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationTargetKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymClosingKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymDecryptionKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeySelector;
import nl.logius.pepcrypto.lib.lang.PepExceptionCollector;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.AFTER;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.INVALID_MIGRATION_TARGET_KEY_SELECTION;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SERVICE_PROVIDER_KEYS_UNRELATED_TO_MIGRATION;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT_KEY_SET_VERSION;

@ApplicationScoped
class MicroprofileSignedEncryptedPseudonymMigrationKeyStore
        extends MicroprofileAbstractKeyStore {

    @Inject
    private MicroprofilePseudonymDecryptionKeySelector decryptionKeySelector;

    @Inject
    private MicroprofilePseudonymClosingKeySelector closingKeySelector;

    @Inject
    private MicroprofileMigrationSourceKeySelector migrationSourceKeySelector;

    @Inject
    private MicroprofileMigrationTargetKeySelector migrationTargetKeySelector;

    @Inject
    private MicroprofileSchemeKeySelector schemeKeySelector;

    void processKeySelection(@Observes @ApiEvent(value = ASN1_MAPPING, phase = AFTER) MicroprofileSignedEncryptedPseudonymMigrationExchange exchange) {
        var collector = new PepExceptionCollector();

        collector.ignore(() -> requireValidSchemeKeyNames(exchange));
        collector.ignore(() -> requireValidSchemeKeyStructure(exchange));
        collector.ignore(() -> requireCompleteMigrationTargetKeySelection(exchange));
        collector.ignore(() -> parseSchemeKeys(exchange));
        collector.ignore(() -> parseServiceProviderKeys(exchange));
        collector.ignore(() -> processSchemeKeySelection(exchange));
        collector.ignore(() -> processServiceProviderKeySelection(exchange));
        collector.ignore(() -> requireSourceOrTarget(exchange));

        collector.requireNoExceptions(MICROPROFILE_MODULE);
    }

    private void requireSourceOrTarget(MicroprofileSignedEncryptedPseudonymMigrationExchange exchange) {
        var serviceProviderKeys = exchange.getParsedServiceProviderKeys();

        var migrationSourceKey = exchange.getMigrationSourceKey();
        var source = migrationSourceKey.getSourceOrRecipient();
        var target = migrationSourceKey.getTargetOrRecipient();
        Predicate<PemEcPrivateKey> isSource = it -> source.equals(it.getSourceOrRecipient()) || source.equals(it.getTargetOrRecipient());
        Predicate<PemEcPrivateKey> isTarget = it -> target.equals(it.getSourceOrRecipient()) || target.equals(it.getTargetOrRecipient());
        var isSourceOrTarget = isSource.or(isTarget);

        var unrelated = serviceProviderKeys.stream()
                                           .filter(isSourceOrTarget.negate())
                                           .collect(Collectors.toList());

        SERVICE_PROVIDER_KEYS_UNRELATED_TO_MIGRATION.requireTrue(unrelated.isEmpty());

    }

    private void requireCompleteMigrationTargetKeySelection(MicroprofileSignedEncryptedPseudonymMigrationExchange exchange) {
        INVALID_MIGRATION_TARGET_KEY_SELECTION.requireFalse(exchange.isMigrationTargetSelectionInvalid());
    }

    private void processSchemeKeySelection(MicroprofileSignedEncryptedPseudonymMigrationExchange exchange) {
        var parsedSchemeKeys = exchange.getParsedSchemeKeys();
        var selectedSchemeKey = schemeKeySelector.selectSchemeKey(parsedSchemeKeys, exchange::isMatchingSchemeKeyUrn);

        exchange.setSelectedSchemeKey(selectedSchemeKey);
    }

    private void processServiceProviderKeySelection(MicroprofileSignedEncryptedPseudonymMigrationExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        var mappedInput = exchange.getMappedInput();
        var requestMigrationId = exchange.getRequestMigrationId();
        var sourceRecipientKeyId = mappedInput.asAsn1RecipientKeyId()
                                              .migrationId(requestMigrationId);
        var requestTargetMigrant = exchange.getRequestTargetMigrant();
        var requestTargetMigrantKeySetVersion = exchange.getRequestTargetMigrantKeySetVersion();

        // PD_D-selection
        var decryptionKeys = decryptionKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, sourceRecipientKeyId);
        var decryptionKey = decryptionKeySelector.requireUniqueMatch(decryptionKeys);
        exchange.setSelectedDecryptionKey(decryptionKey);

        // PC_D-selection
        var closingKeys = closingKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, sourceRecipientKeyId);
        var closingKey = closingKeySelector.requireUniqueMatch(closingKeys);
        exchange.setSelectedClosingKey(closingKey);

        // Migration Source Key selection
        var migrationSourceKeys = migrationSourceKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, sourceRecipientKeyId);
        var migrationSourceKey = migrationSourceKeySelector.requireUniqueMatch(migrationSourceKeys, requestTargetMigrant, requestTargetMigrantKeySetVersion);

        exchange.setMigrationSourceKey(migrationSourceKey);

        // Migration Target Key Selection
        var targetMigrant = Optional.ofNullable(migrationSourceKey)
                                    .map(it -> it.getSpecifiedHeader(TARGET_MIGRANT))
                                    .orElse(null);
        var targetMigrantKeySetVersion = Optional.ofNullable(migrationSourceKey)
                                                 .map(it -> it.getSpecifiedHeader(TARGET_MIGRANT_KEY_SET_VERSION))
                                                 .orElse(null);

        var migrationTargetTarget = sourceRecipientKeyId.recipient(targetMigrant)
                                                        .recipientKeySetVersion(targetMigrantKeySetVersion);
        var migrationTargetKeys = migrationTargetKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, migrationTargetTarget);

        var sourceMigrant = sourceRecipientKeyId.getRecipient();
        var sourceMigrantKeySetVersion = sourceRecipientKeyId.getRecipientKeySetVersion();
        var migrationTargetKey = migrationTargetKeySelector.filterByUniqueRecipientKeyId(migrationTargetKeys, sourceMigrant, sourceMigrantKeySetVersion);

        exchange.setMigrationTargetKey(migrationTargetKey);
    }

}

