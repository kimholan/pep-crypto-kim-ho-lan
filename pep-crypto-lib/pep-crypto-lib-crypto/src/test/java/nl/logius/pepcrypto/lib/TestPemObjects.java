package nl.logius.pepcrypto.lib;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.crypto.key.PepDrkiPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEiDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationSourcePrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationTargetPrivateKey;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.ECPrivateKey;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.Security;
import java.util.Optional;

import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.LIB_CRYPTO_MODULE;

public interface TestPemObjects {

    static PemObject toPemObject(String string) {
        var stringReader = new StringReader(string);
        var reader = new PEMParser(stringReader);
        return LIB_CRYPTO_MODULE.call(reader::readPemObject);
    }

    static ECPoint readSchemePublicKeyValueFromResource(String resource) {
        var pemString = TestResources.resourceToString(resource);
        var pemObject = toPemObject(pemString);

        try {
            var content = pemObject.getContent();

            // conversie naar ec point
            var sequence = (ASN1Sequence) ASN1Sequence.fromByteArray(content);
            var bitString = (DERBitString) sequence.getObjectAt(1);
            var octets = bitString.getOctets();

            var bouncyCastleProvider = new BouncyCastleProvider();
            Security.addProvider(bouncyCastleProvider);
            var CURVE_NAME = "brainpoolp320r1";
            var EC_CURVE_PARAMETERS = TeleTrusTNamedCurves.getByName(CURVE_NAME);
            var CURVE = EC_CURVE_PARAMETERS.getCurve();

            // Public key
            return CURVE.decodePoint(octets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static PepDrkiPrivateKey drkiFromResource(String string) {
        return PepDrkiPrivateKey.newInstance(readPrivateKeyValueFromResource(string));
    }

    static PepEpClosingPrivateKey epClosingFromResource(String string) {
        return PepEpClosingPrivateKey.newInstance(readPrivateKeyValueFromResource(string));
    }

    static PepEpDecryptionPrivateKey epDecryptionFromResource(String string) {
        return PepEpDecryptionPrivateKey.newInstance(readPrivateKeyValueFromResource(string));
    }

    static PepEpMigrationTargetPrivateKey epMigrationTargetFromResource(String string) {
        return PepEpMigrationTargetPrivateKey.newInstance(readPrivateKeyValueFromResource(string));
    }

    static PepEpMigrationSourcePrivateKey epMigrationSourceFromResource(String string) {
        return PepEpMigrationSourcePrivateKey.newInstance(readPrivateKeyValueFromResource(string));
    }

    static PepEiDecryptionPrivateKey eiDecryptionFromResource(String string) {
        return PepEiDecryptionPrivateKey.newInstance(readPrivateKeyValueFromResource(string));
    }

    static BigInteger readPrivateKeyValueFromResource(String string) {
        var pemString = TestResources.resourceToString(string);
        var pemObject = toPemObject(pemString);

        var e = ASN1Sequence.getInstance(pemObject.getContent());
        var privateKey = ECPrivateKey.getInstance(e);
        var algId = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, privateKey.getParameters());
        var privateKeyInfo = LIB_CRYPTO_MODULE.call(() -> new PrivateKeyInfo(algId, privateKey));

        // public key can be empty for EC Closing key
        var key = LIB_CRYPTO_MODULE.call(privateKeyInfo::parsePrivateKey);
        return requireValidPrivateKeyValue(key);
    }

    static ECPoint readPublicKeyValueFromResource(String string) {
        var pemString = TestResources.resourceToString(string);
        var pemObject = toPemObject(pemString);

        var e = ASN1Sequence.getInstance(pemObject.getContent());
        var privateKey = ECPrivateKey.getInstance(e);
        var algId = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, privateKey.getParameters());
        var privateKeyInfo = LIB_CRYPTO_MODULE.call(() -> new PrivateKeyInfo(algId, privateKey));
        var publicKeyInfo = Optional.of(privateKey)
                                    .map(ECPrivateKey::getPublicKey)
                                    .map(it -> new SubjectPublicKeyInfo(algId, privateKey.getPublicKey().getBytes()))
                                    .orElse(null);
        var key = LIB_CRYPTO_MODULE.call(privateKeyInfo::parsePrivateKey);
        var privateKeyValue = requireValidPrivateKeyValue(key);
        return Optional.ofNullable(publicKeyInfo)
                       .map(it -> requireValidPublicKeyValue(publicKeyInfo, privateKeyValue))
                       .orElse(null);

    }

    private static ECPoint requireValidPublicKeyValue(SubjectPublicKeyInfo publicKeyInfo, BigInteger privateKeyValue) {
        return Optional.ofNullable(publicKeyInfo)
                       .map(SubjectPublicKeyInfo::getPublicKeyData)
                       .map(ASN1BitString::getBytes)
                       .map(PepCrypto::decodeEcPoint)
                       .filter(PepCrypto.getPublicKey(privateKeyValue)::equals)
                       .orElseThrow(RuntimeException::new);
    }

    private static BigInteger requireValidPrivateKeyValue(ASN1Encodable key) {
        return Optional.of(key)
                       .filter(ASN1Sequence.class::isInstance)
                       .map(ASN1Sequence.class::cast)
                       .map(it -> it.getObjectAt(1))
                       .map(DEROctetString.class::cast)
                       .map(ASN1OctetString::getOctets)
                       .map(it -> new BigInteger(1, it)) // Unsigned
                       .orElseThrow(RuntimeException::new);
    }

}
