package nl.logius.pepcrypto.lib.asn1;

import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static nl.logius.pepcrypto.lib.TestValues.anyBigInteger;
import static nl.logius.pepcrypto.lib.TestValues.anyString;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class Asn1PepRecipientKeyIdTest {

    @Test
    public void constructorCoverageFromPepSignedMessage() {
        var pepRecipientKeyId = mock(PepRecipientKeyId.class);

        var schemeVersion = anyBigInteger();

        when(pepRecipientKeyId.getSchemeVersion()).thenReturn(schemeVersion);

        var schemeKeySetVersion = anyBigInteger();
        when(pepRecipientKeyId.getSchemeKeySetVersion()).thenReturn(schemeKeySetVersion);

        var recipient = anyString();
        when(pepRecipientKeyId.getRecipient()).thenReturn(recipient);

        var recipientKeySetVersion = anyBigInteger();
        when(pepRecipientKeyId.getRecipientKeySetVersion()).thenReturn(recipientKeySetVersion);

        var actual = new Asn1RecipientKeyId(pepRecipientKeyId);

        verify(pepRecipientKeyId).getSchemeVersion();
        verify(pepRecipientKeyId).getSchemeKeySetVersion();
        verify(pepRecipientKeyId).getRecipient();
        verify(pepRecipientKeyId).getRecipientKeySetVersion();
        verify(pepRecipientKeyId).getDiversifier();
        verify(pepRecipientKeyId).getMigrationId();
        verifyNoMoreInteractions(pepRecipientKeyId);

        assertEquals(schemeVersion, actual.getSchemeVersion());
        assertEquals(schemeKeySetVersion, actual.getSchemeKeySetVersion());
        assertEquals(recipient, actual.getRecipient());
        assertEquals(recipientKeySetVersion, actual.getRecipientKeySetVersion());

    }

    @Test
    public void recipientKeySetVersionString() {
        var recipientKeyId = mock(PepRecipientKeyId.class);

        var schemeVersion = anyBigInteger();
        when(recipientKeyId.getSchemeVersion()).thenReturn(schemeVersion);

        var schemeKeySetVersion = anyBigInteger();
        when(recipientKeyId.getSchemeKeySetVersion()).thenReturn(schemeKeySetVersion);

        var recipient = anyString();
        when(recipientKeyId.getRecipient()).thenReturn(recipient);

        var recipientKeySetVersion = anyBigInteger();
        when(recipientKeyId.getRecipientKeySetVersion()).thenReturn(recipientKeySetVersion);

        var newRecipientKeySetVersion = anyString();

        assertNotEquals(String.valueOf(recipientKeySetVersion), newRecipientKeySetVersion);
        var actual = new Asn1RecipientKeyId(recipientKeyId).recipientKeySetVersion(newRecipientKeySetVersion);

        assertEquals(newRecipientKeySetVersion, String.valueOf(actual.getRecipientKeySetVersion()));

        verify(recipientKeyId).getSchemeVersion();
        verify(recipientKeyId).getSchemeKeySetVersion();
        verify(recipientKeyId).getRecipient();
        verify(recipientKeyId).getRecipientKeySetVersion();
        verify(recipientKeyId).getMigrationId();
        verify(recipientKeyId).getDiversifier();
        verifyNoMoreInteractions(recipientKeyId);
    }

    @Test
    public void recipientKeySetVersionBigInteger() {
        var recipientKeyId = mock(PepRecipientKeyId.class);

        var schemeVersion = anyBigInteger();
        when(recipientKeyId.getSchemeVersion()).thenReturn(schemeVersion);

        var schemeKeySetVersion = anyBigInteger();
        when(recipientKeyId.getSchemeKeySetVersion()).thenReturn(schemeKeySetVersion);

        var recipient = anyString();
        when(recipientKeyId.getRecipient()).thenReturn(recipient);

        var recipientKeySetVersion = anyBigInteger();
        when(recipientKeyId.getRecipientKeySetVersion()).thenReturn(recipientKeySetVersion);

        var newRecipientKeySetVersion = anyBigInteger();

        assertNotEquals(String.valueOf(recipientKeySetVersion), newRecipientKeySetVersion);
        var actual = new Asn1RecipientKeyId(recipientKeyId).recipientKeySetVersion(newRecipientKeySetVersion);

        assertEquals(newRecipientKeySetVersion, actual.getRecipientKeySetVersion());

        verify(recipientKeyId).getSchemeVersion();
        verify(recipientKeyId).getSchemeKeySetVersion();
        verify(recipientKeyId).getRecipient();
        verify(recipientKeyId).getRecipientKeySetVersion();
        verify(recipientKeyId).getMigrationId();
        verify(recipientKeyId).getDiversifier();
        verifyNoMoreInteractions(recipientKeyId);
    }

}
