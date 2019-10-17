package nl.logius.pepcrypto.application.microprofile.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import javax.interceptor.InvocationContext;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileExceptionMapperInterceptorTest {

    @InjectMocks
    private MicroprofileExceptionMapperInterceptor interceptor;

    private Object expected;

    @Test
    public void aroundInvoke() throws Exception {
        var invocationContext = mock(InvocationContext.class);
        var method = MicroprofileExceptionMapperInterceptorTest.class.getDeclaredMethod("method");
        expected = new Object();

        when(invocationContext.getMethod()).thenReturn(method);
        when(invocationContext.proceed()).thenReturn(expected);

        var actual = interceptor.aroundInvoke(invocationContext);

        assertSame(expected, actual);
    }

    @MicroprofileExceptionMapper(MICROPROFILE_MODULE)
    Object method() {
        return expected;
    }

}
