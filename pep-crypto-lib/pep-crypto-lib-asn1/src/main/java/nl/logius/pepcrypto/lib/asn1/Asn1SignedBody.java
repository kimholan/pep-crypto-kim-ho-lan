package nl.logius.pepcrypto.lib.asn1;

public interface Asn1SignedBody {

    Asn1Body asn1Body();

    byte[] getRawInput();

    byte[] getAuditElement();

}
