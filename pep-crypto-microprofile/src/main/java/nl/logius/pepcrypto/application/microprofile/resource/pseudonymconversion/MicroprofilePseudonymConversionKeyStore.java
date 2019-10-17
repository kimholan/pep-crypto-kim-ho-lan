package nl.logius.pepcrypto.application.microprofile.resource.pseudonymconversion;

import nl.logius.pepcrypto.api.event.ApiEvent;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileAbstractKeyStore;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymClosingKeySelector;
import nl.logius.pepcrypto.lib.lang.PepExceptionCollector;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.AFTER;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_CLOSING_KEY_REQUIRED;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;

@ApplicationScoped
class MicroprofilePseudonymConversionKeyStore
        extends MicroprofileAbstractKeyStore {

    @Inject
    private MicroprofilePseudonymClosingKeySelector closingKeySelector;

    void processKeySelection(@Observes @ApiEvent(value = ASN1_MAPPING, phase = AFTER) MicroprofilePseudonymConversionExchange exchange) {
        var collector = new PepExceptionCollector();

        collector.ignore(() -> parseServiceProviderKeys(exchange));
        collector.ignore(() -> requireUniqueRecipient(exchange));
        collector.ignore(() -> requireAtLeastTwoServiceProviderKeys(exchange));
        collector.ignore(() -> processServiceProviderKeySelection(exchange));

        collector.requireNoExceptions(MICROPROFILE_MODULE);
    }

    private void requireAtLeastTwoServiceProviderKeys(MicroprofilePseudonymConversionExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        MATCHING_CLOSING_KEY_REQUIRED.requireTrue(parsedServiceProviderKeys.size() > 1);
    }

    private void processServiceProviderKeySelection(MicroprofilePseudonymConversionExchange exchange) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();

        var sourceRecipientKeyId = exchange.getMappedInput().asAsn1RecipientKeyId();
        var sourceClosingKeys = closingKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, sourceRecipientKeyId);
        var sourceClosingKey = closingKeySelector.requireUniqueMatch(sourceClosingKeys);
        exchange.setSourceClosingKey(sourceClosingKey);

        var targetRecipientKeyId = exchange.getTargetClosingKeyAsRecipientKeyId();
        var targetClosingKeys = closingKeySelector.filterByRecipientKeyId(parsedServiceProviderKeys, targetRecipientKeyId);
        var targetClosingKey = closingKeySelector.requireUniqueMatch(targetClosingKeys);

        exchange.setTargetClosingKey(targetClosingKey);
    }

}

