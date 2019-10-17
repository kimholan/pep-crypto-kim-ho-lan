package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.ObjectIdentifiers;

/**
 * Top-level types identified using a notationIdentifier in the PEP ASN.1-schema.
 */
public interface Asn1PepType {

    ObjectIdentifiers getNotationIdentifier();

    /**
     * Encode the structure as a byte array.
     */
    byte[] encodeByteArray();

    default String asn1Oid() {
        return getNotationIdentifier().getId();
    }

}
