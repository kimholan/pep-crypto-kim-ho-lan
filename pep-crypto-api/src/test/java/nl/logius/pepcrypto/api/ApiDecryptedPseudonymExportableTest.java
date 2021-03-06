package nl.logius.pepcrypto.api;

import org.junit.Test;

import java.util.function.UnaryOperator;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ApiDecryptedPseudonymExportableTest {

    @Test
    public void getConversionFunction() {
        var mock = mock(ApiDecryptedPseudonymExportable.class);

        when(mock.getConversionFunction()).thenCallRealMethod();

        var function = mock.getConversionFunction();
        assertNotNull(function);

        verify(mock).getConversionFunction();
        verify(mock).getSelectedConversionKey();
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void convert() {
        var mock = mock(ApiDecryptedPseudonymExportable.class);

        doCallRealMethod().when(mock).convert();

        var function = mock(UnaryOperator.class);
        when(mock.getConversionFunction()).thenReturn(function);

        mock.convert();

        verify(mock).convert();
        verify(mock).getEcPoint();
        verify(mock).getConversionFunction();
        verify(mock).setConvertedEcPoint(any());
        verifyNoMoreInteractions(mock);
    }

}

