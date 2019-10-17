package nl.logius.pepcrypto.lib.asn1;

import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;

public enum Asn1ExceptionDetail
        implements PepExceptionDetail {

    /**
     * Decoding/unmarshalling raw input as a ASN.1 schema class.
     */
    PEP_SCHEMA_ASN1_DECODE,

    /**
     * Unspecified exception from this module.
     */
    ASN1_MODULE

}
