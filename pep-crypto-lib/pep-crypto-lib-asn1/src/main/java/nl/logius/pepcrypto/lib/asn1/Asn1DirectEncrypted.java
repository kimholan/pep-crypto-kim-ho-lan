package nl.logius.pepcrypto.lib.asn1;

import java.math.BigInteger;

public interface Asn1DirectEncrypted {

    BigInteger asn1SigningKeyVersion();

    String asn1AuthorizedParty();

}
