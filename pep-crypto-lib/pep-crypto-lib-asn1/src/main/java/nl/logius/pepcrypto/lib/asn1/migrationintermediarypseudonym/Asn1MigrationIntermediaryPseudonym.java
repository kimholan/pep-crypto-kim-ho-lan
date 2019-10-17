package nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym;

import generated.asn1.MigrationIntermediaryPseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1Diversifier;
import nl.logius.pepcrypto.lib.asn1.Asn1PepType;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepSchemeKeySetId;

import java.math.BigInteger;
import java.util.Optional;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1MigrationIntermediaryPseudonym
        extends PepSchemeKeySetId, Asn1Pseudonym, Asn1PepType {

    static Asn1MigrationIntermediaryPseudonym fromByteArray(byte[] bytes) {
        var pepPseudonym = new MigrationIntermediaryPseudonym();

        PEP_SCHEMA_ASN1_DECODE.call(pepPseudonym::decodeByteArray, bytes);

        return pepPseudonym;
    }

    default Asn1RecipientKeyId asTargetAsn1RecipientKeyId() {
        var diversifier = Optional.of(this)
                                  .map(Asn1MigrationIntermediaryPseudonym::getDiversifier)
                                  .map(Asn1Diversifier::asn1DiversifierString)
                                  .orElse(null);

        return new Asn1RecipientKeyId(this, getTarget(), getTargetKeySetVersion())
                       .diversifier(diversifier)
                       .migrationId(getMigrationID());
    }

    String getSource();

    BigInteger getSourceKeySetVersion();

    String getTarget();

    BigInteger getTargetKeySetVersion();

    String getMigrationID();

    String getPseudonymValue();

}
