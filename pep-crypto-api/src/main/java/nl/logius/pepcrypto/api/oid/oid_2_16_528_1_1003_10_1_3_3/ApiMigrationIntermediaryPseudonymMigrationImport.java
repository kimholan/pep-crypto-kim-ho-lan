package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_3_3;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationImportExchange;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationImportService;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_3_3;

@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_3_3)
public class ApiMigrationIntermediaryPseudonymMigrationImport
        implements ApiMigrationImportService {

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_3_3)
    private ApiAsn1Mapper<Asn1MigrationIntermediaryPseudonym> asn1Mapper;

    @Override
    public void processExchange(ApiMigrationImportExchange exchange) {
        asn1Mapper.processRawInput(exchange);

        exchange.convert();
    }

}
