package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedidentity;

import nl.logius.pepcrypto.api.event.ApiEvent;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileAbstractKeyStore;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileIdentityDecryptionKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeySelector;
import nl.logius.pepcrypto.lib.lang.PepExceptionCollector;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.AFTER;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;

@ApplicationScoped
class MicroprofileSignedEncryptedIdentityKeyStore
        extends MicroprofileAbstractKeyStore {

    @Inject
    private MicroprofileIdentityDecryptionKeySelector identityDecryptionKeySelector;

    @Inject
    private MicroprofileSchemeKeySelector schemeKeySelector;

    void processKeySelection(@Observes @ApiEvent(value = ASN1_MAPPING, phase = AFTER) MicroprofileSignedEncryptedIdentityExchange exchange) {
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

    private void processSchemeKeySelection(MicroprofileSignedEncryptedIdentityExchange exchange) {
        var parsedSchemeKeys = exchange.getParsedSchemeKeys();
        var selectedSchemeKey = schemeKeySelector.selectSchemeKey(parsedSchemeKeys, exchange::isMatchingSchemeKeyUrn);

        exchange.setSelectedSchemeKey(selectedSchemeKey);
    }

    private void processServiceProviderKeySelection(MicroprofileSignedEncryptedIdentityExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        // ID_D-selection
        var selectedDecryptionKeyRecipientKeyId = exchange.getSelectedDecryptionKeyRecipientKeyId();
        var decryptionKeys = identityDecryptionKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, selectedDecryptionKeyRecipientKeyId);
        var decryptionKey = identityDecryptionKeySelector.requireUniqueMatch(decryptionKeys);

        exchange.setSelectedDecryptionKey(decryptionKey);
    }

}

