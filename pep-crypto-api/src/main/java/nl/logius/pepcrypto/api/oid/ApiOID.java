package nl.logius.pepcrypto.api.oid;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.INVALID_OID;

@Qualifier
@Retention(RUNTIME)
@Target({METHOD, TYPE, PARAMETER, FIELD})
public @interface ApiOID {

    ApiOIDType value();

    enum ApiOIDType {
        /**
         * EC-Signature Value - EC-Schnorr - Deprecated.
         */
        OID_0_4_0_127_0_7_1_1_4_3_3("0.4.0.127.0.7.1.1.4.3.3"),

        /**
         * EC-Signature Value - EC-Schnorr Digital Signature Algorithm (ECSDSA).
         */
        OID_0_4_0_127_0_7_1_1_4_4_3("0.4.0.127.0.7.1.1.4.4.3"),

        /**
         * EC-Signature Value - EC Digital Signature Algorithm (ECDSA).
         */
        OID_1_2_840_10045_4_3_3("1.2.840.10045.4.3.3"),

        /**
         * EncryptedIdentity.
         */
        OID_2_16_528_1_1003_10_1_2_1("2.16.528.1.1003.10.1.2.1"),

        /**
         * EncryptedPseudonym.
         */
        OID_2_16_528_1_1003_10_1_2_2("2.16.528.1.1003.10.1.2.2"),

        /**
         * DirectEncryptedPseudonym.
         */
        OID_2_16_528_1_1003_10_1_2_5("2.16.528.1.1003.10.1.2.5"),

        /**
         * DirectEncryptedIdentity.
         */
        OID_2_16_528_1_1003_10_1_2_9_2("2.16.528.1.1003.10.1.2.9.2"),

        /**
         * (Deprecated) SignedEncryptedIdentity - EC-Schnorr.
         */
        OID_2_16_528_1_1003_10_1_2_3("2.16.528.1.1003.10.1.2.3"),

        /**
         * (Deprecated) SignedEncryptedPseudonym - EC-Schnorr.
         */
        OID_2_16_528_1_1003_10_1_2_4("2.16.528.1.1003.10.1.2.4"),

        /**
         * SignedDirectEncryptedPseudonym
         */
        OID_2_16_528_1_1003_10_1_2_6("2.16.528.1.1003.10.1.2.6"),

        /**
         * SignedEncryptedIdentity - EC-Schnorr Digital Signature Algorithm (ECSDSA).
         */
        OID_2_16_528_1_1003_10_1_2_7("2.16.528.1.1003.10.1.2.7"),

        /**
         * SignedEncryptedIdentity-v2 - EC-Schnorr Digital Signature Algorithm (ECSDSA).
         */
        OID_2_16_528_1_1003_10_1_2_7_2("2.16.528.1.1003.10.1.2.7.2"),

        /**
         * SignedEncryptedPseudonym - EC-Schnorr Digital Signature Algorithm (ECSDSA).
         */
        OID_2_16_528_1_1003_10_1_2_8("2.16.528.1.1003.10.1.2.8"),

        /**
         * SignedEncryptedPseudonym-v2 - EC-Schnorr Digital Signature Algorithm (ECSDSA).
         */
        OID_2_16_528_1_1003_10_1_2_8_2("2.16.528.1.1003.10.1.2.8.2"),

        /**
         * SignedDirectEncryptedIdentity.
         */
        OID_2_16_528_1_1003_10_1_2_10_2("2.16.528.1.1003.10.1.2.10.2"),

        /**
         * Decrypted Pseudonym.
         */
        OID_2_16_528_1_1003_10_1_3_2("2.16.528.1.1003.10.1.3.2"),

        /**
         * Migration Intermediary Pseudonym.
         */
        OID_2_16_528_1_1003_10_1_3_3("2.16.528.1.1003.10.1.3.3");

        private final String oid;

        ApiOIDType(String oid) {
            this.oid = oid;
        }

        public boolean isMatchingOid(String oid) {
            return this.oid.equals(oid);
        }

        public void requireMatchingOid(String oid) {
            INVALID_OID.requireEquals(this.oid, oid);
        }

    }

    final class Literal
            extends AnnotationLiteral<ApiOID>
            implements ApiOID {

        private static final long serialVersionUID = -8796229925200350790L;

        private final ApiOIDType value;

        private Literal(ApiOIDType type) {
            value = type;
        }

        public static Literal of(ApiOIDType type) {
            return new Literal(type);
        }

        @Override
        public ApiOIDType value() {
            return value;
        }

    }

}
