package nl.logius.pepcrypto.api.event;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import javax.enterprise.event.Event;
import javax.interceptor.InvocationContext;
import java.math.BigInteger;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.SIGNATURE_VERIFICATION;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class ApiEventSourceInterceptorTest {

    @InjectMocks
    private ApiEventSourceInterceptor service;

    @Mock
    private Event<Object> event;

    @ApiEventSource(SIGNATURE_VERIFICATION)
    public static void intercepted(Object argument) {
        // NOOP
    }

    @Test
    public void intercept() throws Exception {
        var invocationContext = mock(InvocationContext.class);
        var method = MethodUtils.getMatchingMethod(ApiEventSourceInterceptorTest.class, "intercepted", Object.class);
        var parameters = new Object[]{BigInteger.ONE};

        when(invocationContext.getMethod()).thenReturn(method);
        when(invocationContext.getParameters()).thenReturn(parameters);
        when(event.select(any())).thenReturn(event);

        service.aroundInvoke(invocationContext);

        verify(event, times(2)).fire(any());
    }

    @Test
    public void interceptOnException() throws Exception {
        var invocationContext = mock(InvocationContext.class);
        var method = MethodUtils.getMatchingMethod(ApiEventSourceInterceptorTest.class, "intercepted", Object.class);
        var parameters = new Object[]{BigInteger.ONE};
        var exception = new IllegalArgumentException();

        when(invocationContext.getMethod()).thenReturn(method);
        when(invocationContext.getParameters()).thenReturn(parameters);
        when(event.select(any())).thenReturn(event);
        when(invocationContext.proceed()).thenThrow(exception);

        try {
            service.aroundInvoke(invocationContext);
        } catch (IllegalArgumentException cause) {
            assertSame(exception, cause);
        }

        verify(event, times(2)).fire(any());
    }

}
