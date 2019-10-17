package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationexport;

import generated.nl.logius.pepcrypto.openapi.model.OASMigrationIntermediaryPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationExportService;
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
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_3_2;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.OID_NOT_SUPPORTED;

@ApplicationScoped
@Path("/pseudonym-migration-export")
public class MicroprofilePseudonymMigrationExportApi {

    @Inject
    @Any
    private Instance<ApiMigrationExportService> serviceInstance;

    @Inject
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Inject
    private MicroprofilePseudonymMigrationExportResponseMapper responseMapper;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public @Valid OASMigrationIntermediaryPseudonymResponse processRequest(@Valid OASPseudonymMigrationExportRequest request) {
        // Obtain OID from raw input since it's not handled by the OpenAPI-binding
        var asn1SequenceDecodable = new Asn1SequenceDecodable(request::getPseudonym);
        asn1SequenceDecoder.decodeRawInput(asn1SequenceDecodable);
        var oidAnnotationLiteral = asn1SequenceDecodable.requireMatchingOidType(OID_NOT_SUPPORTED
                , OID_2_16_528_1_1003_10_1_3_2
        );

        var exchange = new MicroprofilePseudonymMigrationExportExchange(request);
        var service = serviceInstance.select(oidAnnotationLiteral).get();
        service.processExchange(exchange);

        return responseMapper.mapToResponse(exchange);
    }

}
