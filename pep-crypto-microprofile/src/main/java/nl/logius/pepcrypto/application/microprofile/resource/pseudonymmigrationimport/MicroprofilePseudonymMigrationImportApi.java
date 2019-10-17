package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationimport;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationImportService;
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
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_3_3;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.OID_NOT_SUPPORTED;

@ApplicationScoped
@Path("/pseudonym-migration-import")
public class MicroprofilePseudonymMigrationImportApi {

    @Inject
    @Any
    private Instance<ApiMigrationImportService> serviceInstance;

    @Inject
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Inject
    private MicroprofilePseudonymMigrationImportResponseMapper responseMapper;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public @Valid OASDecryptedPseudonymResponse processRequest(@Valid OASPseudonymMigrationImportRequest request) {
        // Obtain OID from raw input since it's not handled by the OpenAPI-binding
        var asn1SequenceDecodable = new Asn1SequenceDecodable(request::getMigrationIntermediaryPseudonym);
        asn1SequenceDecoder.decodeRawInput(asn1SequenceDecodable);
        var oidAnnotationLiteral = asn1SequenceDecodable.requireMatchingOidType(OID_NOT_SUPPORTED
                , OID_2_16_528_1_1003_10_1_3_3
        );

        var exchange = new MicroprofilePseudonymMigrationImportExchange(request);
        var service = serviceInstance.select(oidAnnotationLiteral).get();
        service.processExchange(exchange);

        return responseMapper.mapToResponse(exchange);
    }

}
