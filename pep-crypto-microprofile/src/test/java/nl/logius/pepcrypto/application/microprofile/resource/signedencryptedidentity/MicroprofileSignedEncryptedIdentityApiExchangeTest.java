package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedIdentityRequest;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1SignedEncryptedIdentityEnvelope;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class MicroprofileSignedEncryptedIdentityApiExchangeTest {

    @Test
    public void coverage() {
        var seiResource = "/legacy/ei02.asn1";
        var rawInput = TestResources.resourceToByteArray(seiResource);
        var mappedInput = Asn1SignedEncryptedIdentityEnvelope.fromByteArray(rawInput);

        var request = mock(OASSignedEncryptedIdentityRequest.class);
        var exchange = new MicroprofileSignedEncryptedIdentityExchange(request);

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
    }

}
