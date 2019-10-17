package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymRequest;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1SignedEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class MicroprofileSignedEncryptedPseudonymExchangeTest {

    @Test
    public void coverage() {
        var seiResource = "/legacy/ep02.asn1";
        var rawInput = TestResources.resourceToByteArray(seiResource);
        var mappedInput = Asn1SignedEncryptedPseudonymEnvelope.fromByteArray(rawInput);

        var request = mock(OASSignedEncryptedPseudonymRequest.class);
        var exchange = new MicroprofileSignedEncryptedPseudonymExchange(request);

        exchange.getRawInput();

        exchange.getRawSchemeKeys();
        exchange.getParsedSchemeKeys();
        exchange.setParsedSchemeKeys(null);

        exchange.setSelectedSchemeKey(null);

        exchange.getSchemePublicKey();

        var decryptionKey = mock(PemEcPrivateKey.class);
        exchange.getSelectedDecryptionKey();
        exchange.setSelectedDecryptionKey(decryptionKey);
        exchange.getRecipientPublicKey();

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
