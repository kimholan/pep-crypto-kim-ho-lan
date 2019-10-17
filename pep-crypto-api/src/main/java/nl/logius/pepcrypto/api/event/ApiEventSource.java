package nl.logius.pepcrypto.api.event;

import nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.UNSPECIFIED;

@InterceptorBinding
@Inherited
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface ApiEventSource {

    @Nonbinding ApiEventType value() default UNSPECIFIED;

}

