package nl.logius.pepcrypto.lib.pem;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.crypto.PepSchemeKeySetId;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.ECPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.io.pem.PemHeader;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.isMatchingCurveOid;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_EC_PRIVATE_KEY_BYTE_LENGTH;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_EC_PRIVATE_KEY_CURVE_OID;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_EC_PRIVATE_KEY_DECODE;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_EC_PRIVATE_KEY_NOT_FOUND;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_EC_PRIVATE_KEY_SCHEME_VERSION;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_HEADERS_INVALID;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_KEY_RECIPIENT_KEY_PUBLIC_KEY_INVALID;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_KEY_UNSUPPORTED_PEM_TYPE;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_KEY_UNSUPPORTED_RECIPIENT_KEY_TYPE;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_OBJECT_READ_FAILED;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_PRIVATE_KEY_VALUE_DECODE;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_PUBLIC_KEY_VALUE_DECODE;
import static org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey;

public class PemEcPrivateKeyReader {

    private static final String PEM_TYPE_EC_PRIVATE_KEY = "EC PRIVATE KEY";

    private static PemObject readPemObject(String pemString) {
        var stringReader = new StringReader(pemString);
        var reader = new PEMParser(stringReader);
        var pemObject = PEM_OBJECT_READ_FAILED.call(reader::readPemObject);

        PEM_KEY_UNSUPPORTED_PEM_TYPE.requireEquals(PEM_TYPE_EC_PRIVATE_KEY, pemObject.getType());

        return pemObject;
    }

    private static PemEcPrivateKeyType toPemEcPrivateKeyType(List<Map.Entry<String, String>> pemHeaderEntries) {
        var candidates = PemEcPrivateKeyType.pemEcPrivateKeyTypeCandidates(pemHeaderEntries);

        PEM_KEY_UNSUPPORTED_RECIPIENT_KEY_TYPE.requireFalse(candidates.isEmpty());

        return candidates.stream()
                         .filter(it -> it.valid(pemHeaderEntries))
                         .findFirst()
                         .orElseThrow(PEM_HEADERS_INVALID);
    }

    private static List<Map.Entry<String, String>> asListOfEntries(PemObject pemObject) {
        List<PemHeader> headers = pemObject.getHeaders();
        return headers.stream()
                      .map(PemEcPrivateKeyReader::newSanitizedEntry)
                      .collect(toList());
    }

    private static Map.Entry<String, String> newSanitizedEntry(PemHeader pemHeader) {
        var name = StringUtils.trimToEmpty(pemHeader.getName());
        var value = StringUtils.trimToEmpty(pemHeader.getValue());
        return new AbstractMap.SimpleImmutableEntry<>(name, value);
    }

    private static ECPrivateKey decodeEcPrivateKey(byte[] content) {
        return PEM_EC_PRIVATE_KEY_DECODE.call(() -> doDecodeEcPrivateKey(content));
    }

    private static ECPrivateKey doDecodeEcPrivateKey(byte[] content) {
        var asn1Encodables = ASN1Sequence.getInstance(content);
        return ECPrivateKey.getInstance(asn1Encodables);
    }

    private static BigInteger decodePrivateKeyValue(ECPrivateKey ecPrivateKey) {
        return PEM_PRIVATE_KEY_VALUE_DECODE.call(() -> doDecodePrivateKeyValue(ecPrivateKey));
    }

    private static BigInteger doDecodePrivateKeyValue(ECPrivateKey ecPrivateKey) throws IOException {
        var algId = new AlgorithmIdentifier(id_ecPublicKey, ecPrivateKey.getParameters());
        var privateKeyInfo = new PrivateKeyInfo(algId, ecPrivateKey);
        var privateKey = (ASN1Sequence) privateKeyInfo.parsePrivateKey();

        requireSupportedSchemeVersion(privateKey);
        requireBrainpoolp320r1Oid(privateKey);

        var privateKeyValue = Optional.of(privateKey)
                                      .map(it -> it.getObjectAt(1))
                                      .map(DEROctetString.class::cast)
                                      .map(ASN1OctetString::getOctets)
                                      .orElseThrow(PEM_EC_PRIVATE_KEY_NOT_FOUND);

        requireMatchingKeyLengthBytes(privateKeyValue);

        return new BigInteger(1, privateKeyValue);
    }

    private static void requireMatchingKeyLengthBytes(byte[] privateKeyBytes) {
        var matches = Optional.of(privateKeyBytes)
                              .filter(PepCrypto::isMatchingKeyLengthBytes)
                              .isPresent();

        PEM_EC_PRIVATE_KEY_BYTE_LENGTH.requireTrue(matches);
    }

    private static void requireSupportedSchemeVersion(ASN1Sequence sequence) {
        var asn1Integer = (ASN1Integer) sequence.getObjectAt(0);
        var schemeVersion = asn1Integer.getValue().intValueExact();
        var supportedSchemeVersion = PepSchemeKeySetId.isSupportedSchemeVersion(schemeVersion);

        PEM_EC_PRIVATE_KEY_SCHEME_VERSION.requireTrue(supportedSchemeVersion);
    }

    private static void requireBrainpoolp320r1Oid(ASN1Sequence sequence) {
        var taggedObject = (DERTaggedObject) sequence.getObjectAt(2);
        var objectIdentifier = (ASN1ObjectIdentifier) taggedObject.getObject();
        var id = objectIdentifier.getId();
        var matchingCurveOid = isMatchingCurveOid(id);

        PEM_EC_PRIVATE_KEY_CURVE_OID.requireTrue(matchingCurveOid);
    }

    private static ECPoint decodePublicKeyValue(ECPrivateKey ecPrivateKey) {
        return PEM_PUBLIC_KEY_VALUE_DECODE.call(() -> doDecodePublicKeyValue(ecPrivateKey));
    }

    private static ECPoint doDecodePublicKeyValue(ECPrivateKey ecPrivateKey) {
        return Optional.of(ecPrivateKey)
                       .map(ECPrivateKey::getPublicKey)
                       .map(ASN1BitString::getOctets)
                       .map(PepCrypto::decodeEcPoint)
                       .orElseThrow(PEM_PUBLIC_KEY_VALUE_DECODE);
    }

    private static ECPoint requireMatchingPublicKeyValue(ECPoint publicKeyValue, ECPrivateKey ecPrivateKey) {
        return PEM_KEY_RECIPIENT_KEY_PUBLIC_KEY_INVALID.call(() -> doRequireMatchingPublicKeyValue(publicKeyValue, ecPrivateKey));

    }

    private static ECPoint doRequireMatchingPublicKeyValue(ECPoint expectedPublicKeyValue, ECPrivateKey ecPrivateKey) {
        return Optional.ofNullable(ecPrivateKey)
                       .map(PemEcPrivateKeyReader::decodePublicKeyValue)
                       .filter(expectedPublicKeyValue::equals)
                       .orElseThrow(PEM_KEY_RECIPIENT_KEY_PUBLIC_KEY_INVALID);
    }

    private static ECPoint decodePublicKeyValue(BigInteger privateKeyValue, ECPrivateKey ecPrivateKey) {
        var publicKeyValue = PepCrypto.getPublicKey(privateKeyValue);

        return requireMatchingPublicKeyValue(publicKeyValue, ecPrivateKey);
    }

    /**
     * Decode PEM-key from a string.
     *
     * @param pemString PEM-key as string, must conform to the DV-key format specifications.
     * @return Decoded PEM-key.
     */
    public PemEcPrivateKeyValue parsePemEcPrivateKey(String pemString) {
        var pemObject = readPemObject(pemString);

        // Determine key type and filter out the specified headers
        var pemHeaderEntries = asListOfEntries(pemObject);
        var pemEcPrivateKeyType = toPemEcPrivateKeyType(pemHeaderEntries);

        // Content of the private key must conform to the specification
        var content = pemObject.getContent();
        var ecPrivateKey = decodeEcPrivateKey(content);
        var privateKeyValue = decodePrivateKeyValue(ecPrivateKey);
        var publicKeyValue = decodePublicKeyValue(privateKeyValue, ecPrivateKey);

        return new PemEcPrivateKeyValue(pemEcPrivateKeyType, pemHeaderEntries, privateKeyValue, publicKeyValue);
    }

}
