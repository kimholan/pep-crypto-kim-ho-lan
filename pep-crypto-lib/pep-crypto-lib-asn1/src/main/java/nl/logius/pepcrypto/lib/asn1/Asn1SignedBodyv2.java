package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.ExtraElements;

public interface Asn1SignedBodyv2
        extends Asn1SignedBody {

    String getIssuanceDate();

    ExtraElements getExtraElements();

}
