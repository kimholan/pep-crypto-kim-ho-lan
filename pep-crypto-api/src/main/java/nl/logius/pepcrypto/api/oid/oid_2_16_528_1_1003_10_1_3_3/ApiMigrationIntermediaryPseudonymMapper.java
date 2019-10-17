package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_3_3;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.exception.ApiExceptionDetailMapper;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.ASN1_MAPPING_FAILED;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_3_3;
import static nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym.fromByteArray;

@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_3_3)
public class ApiMigrationIntermediaryPseudonymMapper
        implements ApiAsn1Mapper<Asn1MigrationIntermediaryPseudonym> {

    @Override
    @ApiEventSource(ASN1_MAPPING)
    @ApiExceptionDetailMapper(ASN1_MAPPING_FAILED)
    public void processRawInput(ApiExchange<Asn1MigrationIntermediaryPseudonym> exchange) {
        var decodedAsn1Sequence = exchange.getRawInput();
        var migrationIntermediaryPseudonym = fromByteArray(decodedAsn1Sequence);

        exchange.setMappedInput(migrationIntermediaryPseudonym);
    }

}
