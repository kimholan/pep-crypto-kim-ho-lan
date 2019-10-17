package nl.logius.pepcrypto.lib.crypto;

import org.bouncycastle.math.ec.ECPoint;

public interface PepEcSchnorrVerificationKey
        extends PepPublicVerificationKey {

    ECPoint getRecipientPublicKey();

}
