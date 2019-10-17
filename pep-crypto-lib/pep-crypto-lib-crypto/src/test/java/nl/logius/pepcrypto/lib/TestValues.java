package nl.logius.pepcrypto.lib;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

public interface TestValues {

    static long randomLong() {
        return Math.round(Math.abs(System.currentTimeMillis() * Math.random()));
    }

    static BigInteger anyBigInteger() {
        return BigInteger.valueOf(randomLong());
    }

    static String anyString() {
        return String.valueOf(randomLong());
    }

    static byte[] anyBytes() {
        return anyBigInteger().toByteArray();
    }

    static ECPoint anyEcPoint() {
        return PepCrypto.getBasePoint().multiply(anyBigInteger());
    }

}
