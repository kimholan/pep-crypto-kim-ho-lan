package nl.logius.pepcrypto.lib;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.math.ec.ECPoint;

import static nl.logius.pepcrypto.lib.crypto.PepCrypto.decodeEcPoint;

public interface TestSignedEncryptedIdentity
        extends PepEcPointTriplet {

    static TestSignedEncryptedIdentity fromResource(String resource) {
        return () -> TestResources.resourceToByteArray(resource);
    }

    byte[] getBytes();

    @Override
    default ECPoint getEcPointA() {
        return decodeEcPoint(getEcPointABytes());
    }

    @Override
    default ECPoint getEcPointB() {
        return decodeEcPoint(getEcPointBBytes());
    }

    @Override
    default ECPoint getEcPointC() {
        return decodeEcPoint(TestLib.getASN1OctetString(getEcPointsAsn1Sequence(), 2));
    }

    default ASN1Sequence toAsn1Sequence() {
        return TestLib.getAsn1Sequence(getBytes());
    }

    default ASN1Sequence getSignedAsn1Sequence() {
        return TestLib.getAsn1Sequence(toAsn1Sequence(), 1);
    }

    default ASN1Sequence getEncryptedIdentityAsn1Sequence() {
        return TestLib.getAsn1Sequence(getSignedAsn1Sequence(), 0);
    }

    default ASN1Sequence getEcPointsAsn1Sequence() {
        return TestLib.getAsn1Sequence(getEncryptedIdentityAsn1Sequence(), 6);
    }

    default byte[] getEcPointABytes() {
        return TestLib.getASN1OctetString(getEcPointsAsn1Sequence(), 0);
    }

    default byte[] getEcPointBBytes() {
        return TestLib.getASN1OctetString(getEcPointsAsn1Sequence(), 1);
    }

    default byte[] getEcPointCBytes() {
        return TestLib.getASN1OctetString(getEcPointsAsn1Sequence(), 2);
    }

}
