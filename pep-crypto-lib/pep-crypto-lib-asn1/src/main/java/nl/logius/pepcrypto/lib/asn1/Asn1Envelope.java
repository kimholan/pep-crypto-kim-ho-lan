package nl.logius.pepcrypto.lib.asn1;

import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.PepSchemeKeySetId;

public interface Asn1Envelope<T extends Asn1SignedBody>
        extends Asn1PepType {

    T asn1SignedBody();

    Asn1Signature asn1Signature();

    /**
     * Data signed by the signature in the envelope.
     */
    default byte[] asn1SignedData() {
        return asn1SignedBody().getRawInput();
    }

    default Asn1Body asn1Body() {
        return asn1SignedBody().asn1Body();
    }

    default byte[] asn1AuditElement() {
        return asn1SignedBody().getAuditElement();
    }

    default String asn1BodyOid() {
        return asn1Body().asn1Oid();
    }

    default PepSchemeKeySetId pepSchemeKeySetId() {
        return asAsn1RecipientKeyId();
    }

    default Asn1RecipientKeyId asAsn1RecipientKeyId() {
        return asn1Body().asAsn1RecipientKeyId();
    }

    default String asn1SignatureOid() {
        return asn1Signature().asn1SignatureOid();
    }

    default PepEcPointTriplet asn1EcPointTriplet() {
        return asn1Body();
    }

}
