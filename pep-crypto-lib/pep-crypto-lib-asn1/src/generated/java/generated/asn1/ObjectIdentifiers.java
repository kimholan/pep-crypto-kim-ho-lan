package generated.asn1;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;

public enum ObjectIdentifiers
        implements ASN1Encodable {

    ECDSA_WITH_SHA384("1.2.840.10045.4.3.3"),

    ID_BSNK_SCHEME_NL("2.16.528.1.1003.10"),

    ID_BSNK_IDENTIFIERS("2.16.528.1.1003.10.1"),

    ID_BSNK_POLYMORPHICS("2.16.528.1.1003.10.1.1"),

    ID_BSNK_POLYMORPHIC_IDENTITY("2.16.528.1.1003.10.1.1.1"),

    ID_BSNK_POLYMORPHIC_PSEUDONYM("2.16.528.1.1003.10.1.1.2"),

    ID_BSNK_POLYMORPHIC_IDENTITY_SIGNED("2.16.528.1.1003.10.1.1.3"),

    ID_BSNK_POLYMORPHIC_IDENTITY_SIGNED_V2("2.16.528.1.1003.10.1.1.3.2"),

    ID_BSNK_POLYMORPHIC_PSEUDONYM_SIGNED("2.16.528.1.1003.10.1.1.4"),

    ID_BSNK_POLYMORPHIC_PSEUDONYM_SIGNED_V2("2.16.528.1.1003.10.1.1.4.2"),

    ID_BSNK_POLYMORPHIC_PIP("2.16.528.1.1003.10.1.1.5"),

    ID_BSNK_POLYMORPHIC_PIP_SIGNED("2.16.528.1.1003.10.1.1.6"),

    ID_BSNK_POLYMORPHIC_PIP_SIGNED_V2("2.16.528.1.1003.10.1.1.6.2"),

    ID_PCA("2.16.528.1.1003.10.9"),

    ID_BSNK_POLYMORPHIC_PIP_VERIFIABLE("2.16.528.1.1003.10.1.1.11"),

    ID_BSNK_POLYMORPHIC_PIP_VERIFIABLE_V2("2.16.528.1.1003.10.1.1.11.2"),

    BSI_DE("0.4.0.127.0.7"),

    ID_ECC("0.4.0.127.0.7.1.1"),

    ECSDSA_PLAIN_SIGNATURES("0.4.0.127.0.7.1.1.4.4"),

    ECSDSA_PLAIN_SHA384("0.4.0.127.0.7.1.1.4.4.3"),

    ECSCHNORR_PLAIN_SIGNATURES("0.4.0.127.0.7.1.1.4.3"),

    ECSCHNORR_PLAIN_SHA384("0.4.0.127.0.7.1.1.4.3.3"),

    ID_BSNK_ENCRYPTED("2.16.528.1.1003.10.1.2"),

    ID_BSNK_ENCRYPTED_IDENTITY("2.16.528.1.1003.10.1.2.1"),

    ID_BSNK_ENCRYPTED_PSEUDONYM("2.16.528.1.1003.10.1.2.2"),

    ID_BSNK_ENCRYPTED_IDENTITY_SIGNED("2.16.528.1.1003.10.1.2.3"),

    ID_BSNK_ENCRYPTED_PSEUDONYM_SIGNED("2.16.528.1.1003.10.1.2.4"),

    ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM("2.16.528.1.1003.10.1.2.5"),

    ID_BSNK_ENCRYPTED_DIRECT_IDENTITY("2.16.528.1.1003.10.1.2.9"),

    ID_BSNK_ENCRYPTED_DIRECT_IDENTITY_V2("2.16.528.1.1003.10.1.2.9.2"),

    ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM_V2("2.16.528.1.1003.10.1.2.5.2"),

    ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM_SIGNED("2.16.528.1.1003.10.1.2.6"),

    ID_BSNK_ENCRYPTED_DIRECT_IDENTITY_SIGNED("2.16.528.1.1003.10.1.2.10"),

    ID_BSNK_ENCRYPTED_DIRECT_IDENTITY_SIGNED_V2("2.16.528.1.1003.10.1.2.10.2"),

    ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM_SIGNED_V2("2.16.528.1.1003.10.1.2.6.2"),

    ID_BSNK_ENCRYPTED_IDENTITY_ECSDSA_SIGNED("2.16.528.1.1003.10.1.2.7"),

    ID_BSNK_ENCRYPTED_IDENTITY_ECSDSA_SIGNED_V2("2.16.528.1.1003.10.1.2.7.2"),

    ID_BSNK_ENCRYPTED_PSEUDONYM_ECSDSA_SIGNED("2.16.528.1.1003.10.1.2.8"),

    ID_BSNK_ENCRYPTED_PSEUDONYM_ECSDSA_SIGNED_V2("2.16.528.1.1003.10.1.2.8.2"),

    ID_BSNK_DECRYPTED("2.16.528.1.1003.10.1.3"),

    ID_BSNK_DECRYPTED_IDENTIFIER("2.16.528.1.1003.10.1.3.1"),

    ID_BSNK_DECRYPTED_PSEUDONYM("2.16.528.1.1003.10.1.3.2"),

    ID_BSNK_DECRYPTED_MIGRATIONPSEUDONYM("2.16.528.1.1003.10.1.3.3"),

    ECSTDCURVESANDGENERATION("1.3.36.3.3.2.8"),

    ELLIPTICCURVE("1.3.36.3.3.2.8.1"),

    VERSIONONE("1.3.36.3.3.2.8.1.1"),

    BRAINPOOLP320R1("1.3.36.3.3.2.8.1.1.9");

    private ASN1ObjectIdentifier asn1ObjectIdentifier;

    ObjectIdentifiers(String oid) {
        asn1ObjectIdentifier = new ASN1ObjectIdentifier(oid);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return asn1ObjectIdentifier.toASN1Primitive();
    }

    public String getId() {
        return asn1ObjectIdentifier.getId();
    }

}