package nl.logius.pepcrypto.lib.crypto;

import org.bouncycastle.math.ec.ECPoint;

/**
 * Carries the scheme public key for message verification purposes.
 */
public interface PepPublicVerificationKey {

    ECPoint getSchemePublicKey();

}
