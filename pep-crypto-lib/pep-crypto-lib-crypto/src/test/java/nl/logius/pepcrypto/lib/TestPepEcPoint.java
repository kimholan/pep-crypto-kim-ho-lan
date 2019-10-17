package nl.logius.pepcrypto.lib;

import org.bouncycastle.math.ec.ECPoint;

import java.util.Base64;

public interface TestPepEcPoint {

    /**
     * Binary uncompressed representation of EC-Point encoded in Base64.
     */
    static String toBase64(ECPoint ecPoint) {
        var encoded = ecPoint.getEncoded(false);
        return Base64.getEncoder().encodeToString(encoded);
    }

}
