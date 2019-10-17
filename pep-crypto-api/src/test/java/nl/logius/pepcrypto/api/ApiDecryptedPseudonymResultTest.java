package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import org.junit.Test;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ApiDecryptedPseudonymResultTest {

    @Test
    public void asAsn1DecryptedPseudonym() {
        var mock = mock(ApiDecryptedPseudonymResult.class);
        when(mock.asAsn1DecryptedPseudonym()).thenCallRealMethod();

        when(mock.getDecryptedPseudonymResultPseudonymValue()).thenReturn("pseudonymValue");

        var recipientKeyId = mock(PepRecipientKeyId.class);
        when(recipientKeyId.getSchemeVersion()).thenReturn(ONE);
        when(recipientKeyId.getSchemeKeySetVersion()).thenReturn(TWO);
        when(recipientKeyId.getRecipient()).thenReturn("recipient");
        when(recipientKeyId.getRecipientKeySetVersion()).thenReturn(TEN);
        when(mock.getDecryptedPseudonymResultPepRecipientKeyId()).thenReturn(recipientKeyId);

        var asn1Pseudonym = mock(Asn1Pseudonym.class);
        when(mock.getDecryptedPseudonymResultAsn1Pseudonym()).thenReturn(asn1Pseudonym);
        when(asn1Pseudonym.getType()).thenReturn(ZERO);

        var actual = mock.asAsn1DecryptedPseudonym();
        assertEquals("generated.asn1.Pseudonym(ID_BSNK_DECRYPTED_PSEUDONYM, 1, 2, recipient, 10, 0, pseudonymValue, null)", actual.toString());

        verify(mock).asAsn1DecryptedPseudonym();
        verify(mock).getDecryptedPseudonymResultPseudonymValue();
        verify(mock).getDecryptedPseudonymResultPepRecipientKeyId();
        verify(mock).getDecryptedPseudonymResultAsn1Pseudonym();

        verifyNoMoreInteractions(mock);
    }

    @Test
    public void getDecryptedPseudonymResultPseudonymValue() {
        var mock = mock(ApiDecryptedPseudonymResult.class);

        when(mock.getDecryptedPseudonymResultPseudonymValue()).thenCallRealMethod();
        when(mock.getDecryptedPseudonymResultEcPoint()).thenReturn(PepCrypto.getBasePoint());

        var actual = mock.getDecryptedPseudonymResultPseudonymValue();
        assertEquals("BEO9fpr7U9i4Uom8xI7lv+byATfRCgh+tueHHioQpZnHEK+NDTniBhEU/dBVRewcyKtAkyR/dydeB0P/7RFxguqpx3h3qqxqx9NSRdFpLo7h", actual);

        verify(mock).getDecryptedPseudonymResultPseudonymValue();
        verify(mock).getDecryptedPseudonymResultEcPoint();

        verifyNoMoreInteractions(mock);
    }

}
