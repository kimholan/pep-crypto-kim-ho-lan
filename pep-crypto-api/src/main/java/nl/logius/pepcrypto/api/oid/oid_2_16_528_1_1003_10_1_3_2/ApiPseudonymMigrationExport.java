package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_3_2;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationExportExchange;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationExportService;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_3_2;

@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_3_2)
public class ApiPseudonymMigrationExport
        implements ApiMigrationExportService {

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_3_2)
    private ApiAsn1Mapper<Asn1DecryptedPseudonym> asn1Mapper;

    @Override
    public void processExchange(ApiMigrationExportExchange exchange) {
        asn1Mapper.processRawInput(exchange);

        exchange.convert();
    }

}
