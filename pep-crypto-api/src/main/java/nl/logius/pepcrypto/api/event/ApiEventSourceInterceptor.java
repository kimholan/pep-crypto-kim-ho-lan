package nl.logius.pepcrypto.api.event;

import nl.logius.pepcrypto.api.event.ApiEvent.Literal;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.AFTER;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.BEFORE;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.EXCEPTION;

@Interceptor
@Dependent
@ApiEventSource
public class ApiEventSourceInterceptor {

    @Inject
    private Event<Object> event;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext invocationContext) throws Exception {
        var method = invocationContext.getMethod();
        var annotation = method.getAnnotation(ApiEventSource.class);
        var eventType = annotation.value();
        var parameter = invocationContext.getParameters()[0];

        event.select(Literal.of(eventType, BEFORE)).fire(parameter);
        try {
            var returnValue = invocationContext.proceed();
            event.select(Literal.of(eventType, AFTER)).fire(parameter);
            return returnValue;
        } catch (Exception cause) {
            event.select(Literal.of(eventType, EXCEPTION)).fire(parameter);
            throw cause;
        }
    }

}
