package nl.logius.pepcrypto.application.microprofile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileJaxRsRuntimeExceptionMapperTest {

    @InjectMocks
    private MicroprofileJaxRsRuntimeExceptionMapper mapper;

    @Test
    public void toResponse() {
        var message = "message";
        var exception = mock(RuntimeException.class);
        when(exception.getMessage()).thenReturn(message);

        var response = mapper.toResponse(exception);

        verify(exception).getMessage();

        assertEquals(500, response.getStatus());
        assertEquals(message, response.getEntity());

    }

}
