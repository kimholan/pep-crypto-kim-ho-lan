package nl.logius.pepcrypto.application.microprofile.oid;

import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionMapper;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.ASN1_SEQUENCE_DECODER;

/**
 * Default implementation to decode the binary encoded ASN.1-data to determine the OID to be processed.
 */
@ApplicationScoped
public class MicroprofileAsn1SequenceDecoder {

    @MicroprofileExceptionMapper(ASN1_SEQUENCE_DECODER)
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        ApiAsn1SequenceDecodable.decodeRawInput(decodable);
    }

}
