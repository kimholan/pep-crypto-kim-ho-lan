package nl.logius.pepcrypto.api.exception;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.API_MODULE;

@Inherited
@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface ApiExceptionDetailMapper {

    @Nonbinding ApiExceptionDetail value() default API_MODULE;

}

