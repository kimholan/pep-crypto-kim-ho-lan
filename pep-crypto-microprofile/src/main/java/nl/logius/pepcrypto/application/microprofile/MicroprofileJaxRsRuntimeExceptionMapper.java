package nl.logius.pepcrypto.application.microprofile;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MicroprofileJaxRsRuntimeExceptionMapper
        implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        return Response.serverError().entity(e.getMessage()).build();
    }

}
