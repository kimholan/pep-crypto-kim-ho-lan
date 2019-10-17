package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;

import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.mockito.Mockito.mock;

public class MicroprofileSignedDirectEncryptedPseudonymExchangeTest {

    @Test
    public void coverage() {
        var rawInput = resourceToByteArray("/v1/happy_flow/dep12.asn1");
        var mappedInput = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(rawInput);

        var request = mock(OASSignedDirectEncryptedPseudonymRequest.class);
        var exchange = new MicroprofileSignedDirectEncryptedPseudonymExchange(request);

        exchange.getRawInput();

        exchange.getRawSchemeKeys();
        exchange.getParsedSchemeKeys();
        exchange.setParsedSchemeKeys(null);

        exchange.setSelectedSchemeKey(null);

        exchange.getSchemePublicKey();

        var decryptionKey = mock(PemEcPrivateKey.class);
        exchange.getSelectedDecryptionKey();
        exchange.setSelectedDecryptionKey(decryptionKey);

        exchange.getRawServiceProviderKeys();
        exchange.getParsedServiceProviderKeys();
        exchange.setParsedServiceProviderKeys(null);

        exchange.getVerificationKeys();

        exchange.setMappedInput(mappedInput);
        exchange.getSelectedSchemeKeySetVersion();
        exchange.getSelectedDecryptionKeyRecipientKeyId();
        exchange.getSignature();
        exchange.getSignedData();
        exchange.isMatchingSchemeKeyUrn("");

        exchange.getTargetClosingKeyAsRecipientKeyId();
    }

}
