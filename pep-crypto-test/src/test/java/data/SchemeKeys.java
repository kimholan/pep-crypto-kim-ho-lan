package data;

import nl.logius.pepcrypto.lib.TestResources;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.Security;
import java.util.AbstractMap;
import java.util.Map.Entry;

public interface SchemeKeys {

    static Entry<String, byte[]> fromResource(Entry<String, String> entry) {
        var resource = entry.getValue();
        var pemString = TestResources.resourceToString(resource);
        var value = readPemObjectAsByteArray(pemString);

        return new AbstractMap.SimpleEntry<>(entry.getKey(), value);
    }

    static String toValue(Entry<String, String> entry) {
        var value = entry.getValue();

        if (value.startsWith("hex:")) {
            value = value.substring(4);
            value = value.replaceAll("\\s", "");

            // EC spul ophalen
            var bouncyCastleProvider = new BouncyCastleProvider();
            Security.addProvider(bouncyCastleProvider);
            var CURVE_NAME = "brainpoolp320r1";
            var EC_CURVE_PARAMETERS = TeleTrusTNamedCurves.getByName(CURVE_NAME);
            var BASE_POINT = EC_CURVE_PARAMETERS.getG();

            // Conversie
            var decode = Hex.decode(value);
            var privateKey = new BigInteger(1, decode);// +1 ?
            var publicKey = BASE_POINT.multiply(privateKey);
            var encodedPublicKey = publicKey.getEncoded(false);

            value = Base64.toBase64String(encodedPublicKey);
        } else {
            value = TestResources.resourceToString(value);
            value = readPemObject(value);
        }

        return value;
    }

    private static byte[] readPemObjectAsByteArray(String value) {
        var stringReader = new StringReader(value);
        var reader = new PEMParser(stringReader);

        try {
            var pemObject = reader.readPemObject();
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
            var ecPoint = CURVE.decodePoint(octets);
            return ecPoint.getEncoded(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readPemObject(String value) {
        var stringReader = new StringReader(value);
        var reader = new PEMParser(stringReader);

        try {
            var pemObject = reader.readPemObject();
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
            var ecPoint = CURVE.decodePoint(octets);
            var encodedPublicKey = ecPoint.getEncoded(false);

            return Base64.toBase64String(encodedPublicKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
