package nl.logius.pepcrypto.application.microprofile.exception;

import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Dependent
@MicroprofileExceptionMapper
public class MicroprofileExceptionMapperInterceptor {

    @AroundInvoke
    public Object aroundInvoke(InvocationContext invocationContext) {
        var method = invocationContext.getMethod();
        var annotation = method.getAnnotation(MicroprofileExceptionMapper.class);
        var exceptionDetail = annotation.value();

        return exceptionDetail.suppress(invocationContext::proceed);
    }

}
