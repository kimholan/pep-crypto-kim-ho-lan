package nl.logius.pepcrypto.application.microprofile.exception;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MICROPROFILE_MODULE;

@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface MicroprofileExceptionMapper {

    @Nonbinding MicroprofileExceptionDetail value() default MICROPROFILE_MODULE;

}

