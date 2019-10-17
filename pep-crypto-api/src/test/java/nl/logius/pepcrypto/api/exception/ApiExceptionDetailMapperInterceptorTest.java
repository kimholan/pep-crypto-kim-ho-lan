package nl.logius.pepcrypto.api.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import javax.interceptor.InvocationContext;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class ApiExceptionDetailMapperInterceptorTest {

    @InjectMocks
    private ApiExceptionDetailMapperInterceptor interceptor;

    private Object expected;

    @Test
    public void aroundInvoke() throws Exception {
        var invocationContext = mock(InvocationContext.class);
        var method = ApiExceptionDetailMapperInterceptorTest.class.getDeclaredMethod("method");
        expected = new Object();

        when(invocationContext.getMethod()).thenReturn(method);
        when(invocationContext.proceed()).thenReturn(expected);

        var actual = interceptor.aroundInvoke(invocationContext);

        assertSame(expected, actual);
    }

    @ApiExceptionDetailMapper(ApiExceptionDetail.API_MODULE)
    Object method() {
        return expected;
    }

}
