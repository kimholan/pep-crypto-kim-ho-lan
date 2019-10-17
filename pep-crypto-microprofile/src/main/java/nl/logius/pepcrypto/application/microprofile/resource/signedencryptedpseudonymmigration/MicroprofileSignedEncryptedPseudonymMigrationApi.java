package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedPseudonymMigrationService;
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
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_4;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_8;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.OID_NOT_SUPPORTED;

/**
 * Exposes the decryption of SignedEncryptedPseudonym to a Pseudonym migrated to another recipient as a JAX-RS resource.
 */
@ApplicationScoped
@Path("/signed-encrypted-pseudonym-migration")
public class MicroprofileSignedEncryptedPseudonymMigrationApi {

    @Inject
    @Any
    private Instance<ApiSignedEncryptedPseudonymMigrationService> serviceInstance;

    @Inject
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Inject
    private MicroprofileSignedEncryptedPseudonymMigrationResponseMapper responseMapper;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public OASDecryptedPseudonymResponse processRequest(@Valid OASSignedEncryptedPseudonymMigrationRequest request) {
        // Obtain OID from raw input since it's not handled by the OpenAPI-binding
        var asn1SequenceDecodable = new Asn1SequenceDecodable(request::getSignedEncryptedPseudonym);
        asn1SequenceDecoder.decodeRawInput(asn1SequenceDecodable);
        var oidAnnotationLiteral = asn1SequenceDecodable.requireMatchingOidType(OID_NOT_SUPPORTED
                , OID_2_16_528_1_1003_10_1_2_4
                , OID_2_16_528_1_1003_10_1_2_8
        );

        var exchange = new MicroprofileSignedEncryptedPseudonymMigrationExchange(request);
        var service = serviceInstance.select(oidAnnotationLiteral).get();
        service.processExchange(exchange);

        // Map the processed values to the response
        return responseMapper.mapToResponse(exchange);
    }

}



