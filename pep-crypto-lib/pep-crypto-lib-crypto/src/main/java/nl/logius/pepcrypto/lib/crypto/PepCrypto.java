package nl.logius.pepcrypto.lib.crypto;

import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.Base64;
import java.util.Optional;

import static java.math.BigInteger.ZERO;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.ELGAMAL_DECRYPTION_FAILED;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.ELGAMAL_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.ELGAMAL_RESHUFFLE_FAILED;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.ELGAMAL_RESHUFFLE_PRIVATE_KEY_WITHIN_BOUNDS;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.LIB_CRYPTO_MODULE;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.PUBLIC_KEY_ECDSA;

/**
 * PEP cryptographic functions.
 * <p>
 * Implemented as enum to enforce singleton and single point of entry for adding the JCE provider.
 */
public enum PepCrypto {
    BOUNCY_CASTLE(new BouncyCastleProvider());

    private static final String CURVE_NAME = "brainpoolp320r1";

    private static final String CURVE_OID = "1.3.36.3.3.2.8.1.1.9";

    private static final int KEY_LENGTH_BITS = 320;

    private static final Integer KEY_LENGTH_BYTES = KEY_LENGTH_BITS / 8;

    private static final Integer SCHEME_KEY_LENGTH_BYTES = KEY_LENGTH_BYTES * 2 + 1;

    private static final Byte UNCOMPRESSED_INDICATOR = 4;

    private static final String KEY_FACTORY_ECDSA = "ECDSA";

    private static final String SIGNATURE_ALGORITHM_SHA_384_WITH_ECDSA = "SHA384withECDSA";

    private final Provider provider;

    private final transient ECParameterSpec ecParameterSpec;

    private final transient X9ECParameters ecParameters;

    PepCrypto(Provider provider) {
        Security.addProvider(provider);
        this.provider = provider;
        ecParameters = TeleTrusTNamedCurves.getByName(CURVE_NAME);
        ecParameterSpec = new ECNamedCurveParameterSpec(CURVE_NAME,
                ecParameters.getCurve(),
                ecParameters.getG(), // G
                ecParameters.getN(), // n / qB
                ecParameters.getH() // h
        );
    }

    public static int getKeyLengthByteCount() {
        return KEY_LENGTH_BYTES;
    }

    public static boolean isMatchingCurveOid(String oid) {
        return getCurveOid().equals(oid);
    }

    public static String getCurveOid() {
        return CURVE_OID;
    }

    public static boolean isMatchingKeyLengthBytes(byte[] bytes) {
        return Optional.ofNullable(bytes)
                       .map(it -> it.length)
                       .filter(KEY_LENGTH_BYTES::equals)
                       .isPresent();
    }

    public static int getKeyLengthBitCount() {
        return KEY_LENGTH_BITS;
    }

    public static boolean isBitLengthWithinBounds(BigInteger actual) {
        return Optional.ofNullable(actual)
                       .filter(it -> it.bitLength() > KEY_LENGTH_BITS)
                       .isEmpty();
    }

    public static boolean isMatchingSchemeKeyLengthBytes(byte[] bytes) {
        return Optional.ofNullable(bytes)
                       .map(it -> it.length)
                       .filter(SCHEME_KEY_LENGTH_BYTES::equals)
                       .isPresent();
    }

    public static ECPoint getInfinity() {
        return getEcCurve().getInfinity();
    }

    public static boolean isUncompressedEcPoint(byte[] bytes) {
        return Optional.ofNullable(bytes)
                       .map(it -> it[0])
                       .filter(UNCOMPRESSED_INDICATOR::equals)
                       .isPresent();
    }

    public static boolean isLessThanQb(BigInteger value) {
        return Optional.ofNullable(value)
                       .filter(it -> it.compareTo(getOrder()) < 0)
                       .isPresent();
    }

    public static BigInteger getOrder() {
        return BOUNCY_CASTLE.getEcParameters().getN();
    }

    public static ECCurve getEcCurve() {
        return BOUNCY_CASTLE.getEcParameters().getCurve();
    }

    public static ECPoint decodeEcPoint(byte[] bytes) {
        return Optional.ofNullable(bytes)
                       .map(getEcCurve()::decodePoint)
                       .orElse(null);
    }

    public static ECPoint decodeEcPoint(String base64) {
        return Optional.ofNullable(base64)
                       .map(Base64.getDecoder()::decode)
                       .map(PepCrypto::decodeEcPoint)
                       .orElse(null);
    }

    public static ECPoint getPublicKey(BigInteger privateKey) {
        return getBasePoint().multiply(privateKey);
    }

    public static ECPoint getBasePoint() {
        return BOUNCY_CASTLE.getEcParameters().getG();
    }

    public static Signature newSignatureSha384WithEcdsa() {
        return LIB_CRYPTO_MODULE.call(() -> Signature.getInstance(SIGNATURE_ALGORITHM_SHA_384_WITH_ECDSA, BOUNCY_CASTLE.getProvider()));
    }

    public static ECPublicKeySpec newEcPublicKeySpec(ECPoint point) {
        return new ECPublicKeySpec(point, BOUNCY_CASTLE.getEcParameterSpec());
    }

    public static PublicKey newPublicKey(ECPoint messageVerificationKey) {
        var pubKeySpec = newEcPublicKeySpec(messageVerificationKey);
        var keyFactory = getKeyFactory(KEY_FACTORY_ECDSA);
        return PUBLIC_KEY_ECDSA.call(keyFactory::generatePublic, pubKeySpec);
    }

    public static KeyFactory getKeyFactory(String keyFactoryName) {
        return LIB_CRYPTO_MODULE.call(() -> KeyFactory.getInstance(keyFactoryName, BOUNCY_CASTLE.getProvider()));
    }

    public static String encodeEcPointAsBase64(ECPoint ecPoint) {
        return Optional.ofNullable(ecPoint)
                       .map(it -> it.getEncoded(false))
                       .map(Base64.getEncoder()::encodeToString)
                       .orElse(null);
    }

    public static boolean isPrivateKeyWithinBounds(BigInteger privateKey) {
        return Optional.ofNullable(privateKey)
                       .filter(it -> it.compareTo(ZERO) > 0)
                       .filter(PepCrypto::isLessThanQb)
                       .isPresent();
    }

    public static PepEcPointTriplet egReshuffle(PepEcPointTriplet ecPointTriplet, BigInteger privateKey) {
        ELGAMAL_RESHUFFLE_PRIVATE_KEY_WITHIN_BOUNDS.requireTrue(isPrivateKeyWithinBounds(privateKey));

        return ELGAMAL_RESHUFFLE_FAILED.call(() -> new PepEcPointTripletValue(
                ecPointTriplet.getEcPointA().multiply(privateKey),
                ecPointTriplet.getEcPointB().multiply(privateKey),
                ecPointTriplet.getEcPointC()
        ));
    }

    public static ECPoint egDecrypt(PepEcPointTriplet ecPointTriplet, BigInteger privateKey) {
        ELGAMAL_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS.requireTrue(isPrivateKeyWithinBounds(privateKey));

        return ELGAMAL_DECRYPTION_FAILED.call(() -> doEgDecrypt(ecPointTriplet, privateKey));
    }

    private static ECPoint doEgDecrypt(PepEcPointTriplet ecPointTriplet, BigInteger privateKey) {
        var yA = ecPointTriplet.getEcPointA().multiply(privateKey);
        var b = ecPointTriplet.getEcPointB();

        return b.subtract(yA);
    }

    private Provider getProvider() {
        return provider;
    }

    private X9ECParameters getEcParameters() {
        return ecParameters;
    }

    private ECParameterSpec getEcParameterSpec() {
        return ecParameterSpec;
    }

}
