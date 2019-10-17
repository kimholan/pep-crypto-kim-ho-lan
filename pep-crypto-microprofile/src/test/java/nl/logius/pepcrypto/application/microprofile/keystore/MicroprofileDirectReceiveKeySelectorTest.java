package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileDirectReceiveKeySelectorTest {

    @InjectMocks
    private MicroprofileDirectReceiveKeySelector selector;

    @Test
    public void filterByRecipientKeyId() {
        var expected = mock(PemEcPrivateKey.class);
        var serviceProviderKeys = List.of(expected);
        var recipientKeyId = mock(PepRecipientKeyId.class);

        when(expected.isDrkiKey()).thenReturn(true);
        when(expected.isMatchingPepRecipientKeyId(any(), any())).thenReturn(true);

        var actual = selector.filterByRecipientKeyId(serviceProviderKeys, recipientKeyId);

        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    public void requireUniqueMatch() {
        var expected = mock(PemEcPrivateKey.class);
        var serviceProviderKeys = List.of(expected);

        var actual = selector.requireUniqueMatch(serviceProviderKeys);

        assertSame(expected, actual);
    }

}

