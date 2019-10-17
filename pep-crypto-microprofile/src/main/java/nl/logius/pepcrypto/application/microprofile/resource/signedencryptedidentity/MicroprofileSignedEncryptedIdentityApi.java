package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedIdentityResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedIdentityRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedIdentityService;
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
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_3;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_7;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_7_2;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.OID_NOT_SUPPORTED;

/**
 * Exposes the decryption of SignedEncryptedIdentity as a JAX-RS resource.
 */
@ApplicationScoped
@Path("/signed-encrypted-identity")
public class MicroprofileSignedEncryptedIdentityApi {

    @Inject
    @Any
    private Instance<ApiSignedEncryptedIdentityService> serviceInstance;

    @Inject
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Inject
    private MicroprofileSignedEncryptedIdentityResponseMapper responseMapper;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public @Valid OASDecryptedIdentityResponse processRequest(@Valid OASSignedEncryptedIdentityRequest request) {
        // Obtain OID from raw input since it's not handled by the OpenAPI-binding
        var asn1SequenceDecodable = new Asn1SequenceDecodable(request::getSignedEncryptedIdentity);
        asn1SequenceDecoder.decodeRawInput(asn1SequenceDecodable);
        var oidAnnotationLiteral = asn1SequenceDecodable.requireMatchingOidType(OID_NOT_SUPPORTED
                , OID_2_16_528_1_1003_10_1_2_3
                , OID_2_16_528_1_1003_10_1_2_7
                , OID_2_16_528_1_1003_10_1_2_7_2
        );

        var exchange = new MicroprofileSignedEncryptedIdentityExchange(request);
        var service = serviceInstance.select(oidAnnotationLiteral).get();
        service.processExchange(exchange);

        // Map the processed values to the response
        return responseMapper.mapToResponse(exchange);
    }

}
