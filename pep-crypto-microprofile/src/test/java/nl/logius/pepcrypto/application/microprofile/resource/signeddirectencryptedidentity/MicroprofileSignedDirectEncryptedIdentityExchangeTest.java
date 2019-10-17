package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedIdentityRequest;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;

import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.mockito.Mockito.mock;

public class MicroprofileSignedDirectEncryptedIdentityExchangeTest {

    @Test
    public void coverage() {
        var rawInput = resourceToByteArray("/v2/happy_flow/dei02.asn1");
        var mappedInput = Asn1SignedDirectEncryptedIdentityv2Envelope.fromByteArray(rawInput);

        var request = mock(OASSignedDirectEncryptedIdentityRequest.class);
        var exchange = new MicroprofileSignedDirectEncryptedIdentityExchange(request);

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

    }

}
