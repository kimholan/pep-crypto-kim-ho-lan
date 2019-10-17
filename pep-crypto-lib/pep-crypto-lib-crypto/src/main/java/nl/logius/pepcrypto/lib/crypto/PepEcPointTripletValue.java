package nl.logius.pepcrypto.lib.crypto;

import org.bouncycastle.math.ec.ECPoint;

import static java.util.Objects.requireNonNull;

/**
 * PEP decryption commonly acts on triplets.
 */
public class PepEcPointTripletValue
        implements PepEcPointTriplet {

    private final ECPoint ecPointA;

    private final ECPoint ecPointB;

    private final ECPoint ecPointC;

    public PepEcPointTripletValue(ECPoint ecPointA, ECPoint ecPointB, ECPoint ecPointC) {
        this.ecPointA = requireNonNull(ecPointA);
        this.ecPointB = requireNonNull(ecPointB);
        this.ecPointC = requireNonNull(ecPointC);
    }

    public PepEcPointTripletValue(PepEcPointTriplet triplet) {
        this(triplet.getEcPointA(), triplet.getEcPointB(), triplet.getEcPointC());
    }

    @Override
    public ECPoint getEcPointA() {
        return ecPointA;
    }

    @Override
    public ECPoint getEcPointB() {
        return ecPointB;
    }

    @Override
    public ECPoint getEcPointC() {
        return ecPointC;
    }

}
