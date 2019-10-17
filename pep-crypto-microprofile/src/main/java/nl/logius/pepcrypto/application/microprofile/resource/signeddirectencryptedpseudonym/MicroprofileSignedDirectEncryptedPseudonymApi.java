package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedPseudonymService;
import nl.logius.pepcrypto.api.oid.Asn1SequenceDecodable;
import nl.logius.pepcrypto.application.microprofile.oid.MicroprofileAsn1SequenceDecoder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_6;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.OID_NOT_SUPPORTED;

/**
 * Exposes the decryption of SignedDirectEncryptedPseudonym as a JAX-RS resource.
 */
@ApplicationScoped
@Path("/signed-direct-encrypted-pseudonym")
public class MicroprofileSignedDirectEncryptedPseudonymApi {

    @Inject
    @Any
    private Instance<ApiSignedDirectEncryptedPseudonymService> serviceInstance;

    @Inject
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Inject
    private MicroprofileSignedDirectEncryptedPseudonymResponseMapper responseMapper;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public @Valid OASDecryptedPseudonymResponse processRequest(@Valid OASSignedDirectEncryptedPseudonymRequest request) {
        // Obtain OID from raw input since it's not handled by the OpenAPI-binding
        var asn1SequenceDecodable = new Asn1SequenceDecodable(request::getSignedDirectEncryptedPseudonym);
        asn1SequenceDecoder.decodeRawInput(asn1SequenceDecodable);
        var oidAnnotationLiteral = asn1SequenceDecodable.requireMatchingOidType(OID_NOT_SUPPORTED
                , OID_2_16_528_1_1003_10_1_2_6
        );

        var exchange = new MicroprofileSignedDirectEncryptedPseudonymExchange(request);
        var service = serviceInstance.select(oidAnnotationLiteral).get();
        service.processExchange(exchange);

        // Map the processed values to the response
        return responseMapper.mapToResponse(exchange);
    }

}



