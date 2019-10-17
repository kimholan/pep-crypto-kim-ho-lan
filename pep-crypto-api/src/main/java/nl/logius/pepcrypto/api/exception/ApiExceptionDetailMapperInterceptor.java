package nl.logius.pepcrypto.api.exception;

import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Dependent
@ApiExceptionDetailMapper
public class ApiExceptionDetailMapperInterceptor {

    @AroundInvoke
    public Object aroundInvoke(InvocationContext invocationContext) {
        var method = invocationContext.getMethod();
        var annotation = method.getAnnotation(ApiExceptionDetailMapper.class);
        var exceptionDetail = annotation.value();

        return exceptionDetail.suppress(invocationContext::proceed);
    }

}
