package nl.logius.pepcrypto.api;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ApiDirectEncryptedPseudonymMigratableTest {

    @Test
    public void getDecryptionFunction() {
        var mock = mock(ApiDirectEncryptedPseudonymMigratable.class);

        when(mock.getDecryptionFunction()).thenCallRealMethod();

        var decryptionFunction = mock.getDecryptionFunction();
        assertNotNull(decryptionFunction);

        verify(mock).getDecryptionPepPrivateKey();
        verify(mock).getClosingPepPrivateKey();
        verify(mock).getDecryptionFunction();
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void isAuthorizedParty() {
        var mock = mock(ApiDirectEncryptedPseudonymMigratable.class);

        when(mock.isAuthorizedParty()).thenCallRealMethod();

        assertFalse(mock.isAuthorizedParty());

        verify(mock).isAuthorizedParty();
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void decrypt() {
        var mock = mock(ApiDirectEncryptedPseudonymMigratable.class);

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

}

