package nl.logius.pepcrypto.lib.asn1;

import java.math.BigInteger;

public interface Asn1RecipientKey {

    BigInteger getSchemeVersion();

    BigInteger getSchemeKeySetVersion();

    String getRecipient();

    BigInteger getRecipientKeySetVersion();

}
