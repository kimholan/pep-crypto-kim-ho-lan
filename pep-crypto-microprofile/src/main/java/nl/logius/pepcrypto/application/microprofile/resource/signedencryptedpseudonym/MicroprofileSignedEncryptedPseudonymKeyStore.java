package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonym;

import nl.logius.pepcrypto.api.event.ApiEvent;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileAbstractKeyStore;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymClosingKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymDecryptionKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeySelector;
import nl.logius.pepcrypto.lib.lang.PepExceptionCollector;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Optional;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.AFTER;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;

@ApplicationScoped
class MicroprofileSignedEncryptedPseudonymKeyStore
        extends MicroprofileAbstractKeyStore {

    @Inject
    private MicroprofilePseudonymDecryptionKeySelector decryptionKeySelector;

    @Inject
    private MicroprofilePseudonymClosingKeySelector closingKeySelector;

    @Inject
    private MicroprofileSchemeKeySelector schemeKeySelector;

    void processKeySelection(@Observes @ApiEvent(value = ASN1_MAPPING, phase = AFTER) MicroprofileSignedEncryptedPseudonymExchange exchange) {
        var collector = new PepExceptionCollector();

        collector.ignore(() -> requireValidSchemeKeyNames(exchange));
        collector.ignore(() -> requireValidSchemeKeyStructure(exchange));
        collector.ignore(() -> parseSchemeKeys(exchange));
        collector.ignore(() -> parseServiceProviderKeys(exchange));
        collector.ignore(() -> requireUniqueRecipient(exchange));
        collector.ignore(() -> processSchemeKeySelection(exchange));
        collector.ignore(() -> processServiceProviderKeySelection(exchange));

        collector.requireNoExceptions(MICROPROFILE_MODULE);
    }

    private void processSchemeKeySelection(MicroprofileSignedEncryptedPseudonymExchange exchange) {
        var parsedSchemeKeys = exchange.getParsedSchemeKeys();
        var selectedSchemeKey = schemeKeySelector.selectSchemeKey(parsedSchemeKeys, exchange::isMatchingSchemeKeyUrn);

        exchange.setSelectedSchemeKey(selectedSchemeKey);
    }

    private void processServiceProviderKeySelection(MicroprofileSignedEncryptedPseudonymExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        // PD_D-selection
        var selectedDecryptionKeyRecipientKeyId = exchange.getSelectedDecryptionKeyRecipientKeyId();
        var decryptionKeys = decryptionKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, selectedDecryptionKeyRecipientKeyId);
        var decryptionKey = decryptionKeySelector.requireUniqueMatch(decryptionKeys);
        exchange.setSelectedDecryptionKey(decryptionKey);

        // PC_D-selection
        var selectedClosingKeyRecipientKeyId = Optional.of(exchange)
                                                       .map(MicroprofileSignedEncryptedPseudonymExchange::getTargetClosingKeyAsRecipientKeyId)
                                                       .orElse(selectedDecryptionKeyRecipientKeyId);
        var closingKeys = closingKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, selectedClosingKeyRecipientKeyId);
        var closingKey = closingKeySelector.requireUniqueMatch(closingKeys);
        exchange.setSelectedClosingKey(closingKey);
    }

}

