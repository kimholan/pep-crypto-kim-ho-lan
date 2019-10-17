package nl.logius.pepcrypto.lib;

import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getPublicKey;

public interface TestVerificationKeys {

    static PepPublicVerificationKey newPublicVerificationKey(ECPoint ecPoint) {
        return () -> ecPoint;
    }

    static PepEcSchnorrVerificationKey newSchnorrVerificationKey(ECPoint ecPoint, BigInteger privateKey) {
        return new PepEcSchnorrVerificationKey() {
            @Override
            public ECPoint getSchemePublicKey() {
                return ecPoint;
            }

            @Override
            public ECPoint getRecipientPublicKey() {
                return getPublicKey(privateKey);
            }
        };
    }

}
