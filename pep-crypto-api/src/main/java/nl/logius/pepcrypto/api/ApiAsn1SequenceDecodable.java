package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.api.oid.ApiOID;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.API_MODULE;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.ASN1_SEQUENCE_OID_NOT_FOUND;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType;
import static nl.logius.pepcrypto.api.oid.ApiOID.Literal;

/**
 * ASN.1 data assumed to be a sequence containing an OID type identifier as the first element.
 */
public interface ApiAsn1SequenceDecodable {

    private static ASN1Sequence asAsn1Sequence(byte[] bytes) throws IOException {
        var asn1Primitive = ASN1Primitive.fromByteArray(bytes);
        return (ASN1Sequence) asn1Primitive;
    }

    /**
     * Decodes the raw input assumed to be encoded ASN.1.
     */
    static void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        var rawAsn1Input = decodable.getRawInput();
        var decodedAsn1Input = API_MODULE.call(ApiAsn1SequenceDecodable::asAsn1Sequence, rawAsn1Input);

        // XXX DER-encoding of input is not enforced
        var oid = Optional.of(decodedAsn1Input)
                          .map(it -> it.getObjectAt(0))
                          .filter(ASN1ObjectIdentifier.class::isInstance)
                          .map(ASN1ObjectIdentifier.class::cast)
                          .map(ASN1ObjectIdentifier::getId)
                          .orElseThrow(ASN1_SEQUENCE_OID_NOT_FOUND);

        decodable.setOid(oid);
    }

    byte[] getRawInput();

    String getOid();

    void setOid(String oid);

    default ApiOIDType getMatchingOidType(ApiOIDType... types) {
        return Stream.of(types)
                     .filter(Objects::nonNull)
                     .filter(it -> it.isMatchingOid(getOid()))
                     .findFirst()
                     .orElse(null);
    }

    default <T extends Exception> ApiOID requireMatchingOidType(Supplier<T> exceptionSupplier, ApiOIDType... types) throws T {
        // Obtain OID from parsed input to select the processing method
        var matchingOidType = getMatchingOidType(types);
        return Optional.ofNullable(matchingOidType)
                       .map(Literal::of)
                       .orElseThrow(exceptionSupplier);
    }

}
