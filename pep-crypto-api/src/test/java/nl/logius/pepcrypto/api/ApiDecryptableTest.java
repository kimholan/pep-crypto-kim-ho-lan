package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;

import java.util.function.Function;

import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ApiDecryptableTest {

    @Test
    public void decrypt() {
        var mock = mock(ApiDecryptable.class);

        doCallRealMethod().when(mock).decrypt();

        var function = mock(Function.class);
        when(mock.getDecryptionFunction()).thenReturn(function);

        mock.decrypt();

        verify(mock).decrypt();
        verify(mock).requireAuthorizedParty();
        verify(mock).getEncryptedEcPointTriplet();
        verify(mock).getDecryptionFunction();
        verify(mock).setDecryptedEcPoint(any());
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void requireAuthorizedPartyDefaultUnauthorized() {
        var mock = mock(ApiDecryptable.class);

        doCallRealMethod().when(mock).requireAuthorizedParty();

        try {
            mock.requireAuthorizedParty();
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.hasExceptionDetail(DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED));
        }

        verify(mock).requireAuthorizedParty();
        verify(mock).isAuthorizedParty();
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void requireAuthorizedPartyAuthorized() {
        var mock = mock(ApiDecryptable.class);

        doCallRealMethod().when(mock).requireAuthorizedParty();
        when(mock.isAuthorizedParty()).thenReturn(true);

        mock.requireAuthorizedParty();

        verify(mock).requireAuthorizedParty();
        verify(mock).isAuthorizedParty();
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void isAuthorizedPartyDefaultFalse() {
        var mock = mock(ApiDecryptable.class);

        doCallRealMethod().when(mock).isAuthorizedParty();

        assertFalse(mock.isAuthorizedParty());

        verify(mock).isAuthorizedParty();
        verifyNoMoreInteractions(mock);
    }

}
