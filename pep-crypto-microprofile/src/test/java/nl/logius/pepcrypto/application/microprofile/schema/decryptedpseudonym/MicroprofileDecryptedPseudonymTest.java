package nl.logius.pepcrypto.application.microprofile.schema.decryptedpseudonym;

import generated.asn1.Pseudonym;
import org.junit.Test;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.TestValues.anyBigInteger;
import static nl.logius.pepcrypto.lib.TestValues.anyString;
import static org.junit.Assert.assertEquals;

public class MicroprofileDecryptedPseudonymTest {

    @Test
    public void newInstance() {
        var schemeVersion = anyBigInteger();
        var schemeKeySetVersion = anyBigInteger();
        var recipient = anyString();
        var recipientKeySetVersion = anyBigInteger();
        var pseudonymValue = anyString();

        var expected = Pseudonym.builder()
                                .schemeVersion(schemeVersion)
                                .schemeKeySetVersion(schemeKeySetVersion)
                                .recipient(recipient)
                                .recipientKeySetVersion(recipientKeySetVersion)
                                .type(BigInteger.valueOf(66))
                                .pseudonymValue(pseudonymValue)
                                .build();

        var actual = MicroprofileDecryptedPseudonym.newInstance(expected);

        assertEquals(expected.getNotationIdentifier().getId(), actual.getNotationIdentifier());
        assertEquals(expected.getSchemeVersion().toString(), actual.getSchemeVersion());
        assertEquals(expected.getSchemeKeySetVersion().toString(), actual.getSchemeKeySetVersion());
        assertEquals(expected.getRecipient(), actual.getRecipient());
        assertEquals(expected.getRecipientKeySetVersion().toString(), actual.getRecipientKeySetVersion());
        assertEquals(Character.toString(expected.getType().intValue()), actual.getType());
        assertEquals(expected.getPseudonymValue(), actual.getPseudonymValue());
    }

}
