package nl.logius.pepcrypto.lib.asn1.decryptedpseudonym;

import generated.asn1.Pseudonym;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import org.junit.Test;

import static nl.logius.pepcrypto.lib.TestValues.anyBigInteger;
import static nl.logius.pepcrypto.lib.TestValues.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class Asn1DecryptedPseudonymTest {

    private static Asn1DecryptedPseudonym mockPepDecryptedPseudonym() {
        var schemeVersion = anyBigInteger();
        var schemeKeySetVersion = anyBigInteger();
        var recipient = anyString();
        var recipientKeySetVersion = anyBigInteger();
        var type = anyBigInteger();
        var pseudonymValue = PepCrypto.encodeEcPointAsBase64(PepCrypto.getBasePoint().multiply(anyBigInteger()));

        return Pseudonym.builder()
                        .schemeVersion(schemeVersion)
                        .schemeKeySetVersion(schemeKeySetVersion)
                        .recipient(recipient)
                        .recipientKeySetVersion(recipientKeySetVersion)
                        .type(type)
                        .pseudonymValue(pseudonymValue)
                        .build();
    }

    @Test
    public void asDerEncodedSequence() {
        var expected = mockPepDecryptedPseudonym();
        var derEncoded = expected.encodeByteArray();

        var actual = Asn1DecryptedPseudonym.fromByteArray(derEncoded);

        assertEquals(expected.getNotationIdentifier().getId(), actual.getNotationIdentifier().getId());
        assertEquals(expected.getSchemeVersion(), actual.getSchemeVersion());
        assertEquals(expected.getSchemeKeySetVersion(), actual.getSchemeKeySetVersion());
        assertEquals(expected.getRecipient(), actual.getRecipient());
        assertEquals(expected.getRecipientKeySetVersion(), actual.getRecipientKeySetVersion());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getPseudonymValue(), actual.getPseudonymValue());
        assertNull(actual.getDiversifier());
        assertNotNull(actual.asn1PseudonymValueAsEcPoint());
        assertNotNull(actual.asAsn1RecipientKeyId());
    }

}
