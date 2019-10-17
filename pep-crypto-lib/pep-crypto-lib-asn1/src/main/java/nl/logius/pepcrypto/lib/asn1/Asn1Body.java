package nl.logius.pepcrypto.lib.asn1;

import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import org.bouncycastle.math.ec.ECPoint;

import java.util.List;

import static nl.logius.pepcrypto.lib.crypto.PepCrypto.decodeEcPoint;

public interface Asn1Body
        extends Asn1RecipientKey, PepEcPointTriplet, Asn1PepType {

    List<generated.asn1.ECPoint> getPoints();

    String getCreator();

    @Override
    default ECPoint getEcPointA() {
        return decodeEcPoint(asn1PointValue(0));
    }

    @Override
    default ECPoint getEcPointB() {
        return decodeEcPoint(asn1PointValue(1));
    }

    @Override
    default ECPoint getEcPointC() {
        return decodeEcPoint(asn1PointValue(2));
    }

    default byte[] asn1PointValue(int n) {
        return getPoints().get(n).getValue();
    }

    default Asn1RecipientKeyId asAsn1RecipientKeyId() {
        return new Asn1RecipientKeyId(this);
    }

}
