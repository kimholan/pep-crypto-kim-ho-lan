package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationimport;

import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationImportExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileServiceProviderKeyStoreExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationTargetPrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT_KEY_SET_VERSION;

class MicroprofilePseudonymMigrationImportExchange
        extends AbstractMicroprofileServiceProviderKeyStoreExchange<Asn1MigrationIntermediaryPseudonym>
        implements ApiMigrationImportExchange {

    private ECPoint convertedEcPoint;

    private PemEcPrivateKey migrationTargetKey;

    MicroprofilePseudonymMigrationImportExchange(OASPseudonymMigrationImportRequest request) {
        super(request, request::getMigrationIntermediaryPseudonym);
    }

    void setMigrationTargetKey(PemEcPrivateKey targetMigrationKey) {
        migrationTargetKey = targetMigrationKey;
    }

    @Override
    public ECPoint getEcPoint() {
        return PepCrypto.decodeEcPoint(getMappedInput().getPseudonymValue());
    }

    @Override
    public ECPoint getDecryptedPseudonymResultEcPoint() {
        return convertedEcPoint;
    }

    @Override
    public void setConvertedEcPoint(ECPoint convertedEcPoint) {
        this.convertedEcPoint = convertedEcPoint;
    }

    @Override
    public PepEpMigrationTargetPrivateKey getSelectedConversionKey() {
        return PepEpMigrationTargetPrivateKey.newInstance(migrationTargetKey.getPrivateKey());
    }

    @Override
    public Asn1Pseudonym getDecryptedPseudonymResultAsn1Pseudonym() {
        return getMappedInput();
    }

    @Override
    public PepRecipientKeyId getDecryptedPseudonymResultPepRecipientKeyId() {
        var targetRecipient = migrationTargetKey.getSpecifiedHeader(TARGET_MIGRANT);
        var targetRecipientKeySetVersion = migrationTargetKey.getSpecifiedHeader(TARGET_MIGRANT_KEY_SET_VERSION);

        return getMappedInput().asTargetAsn1RecipientKeyId()
                               .recipient(targetRecipient)
                               .recipientKeySetVersion(targetRecipientKeySetVersion);
    }

}
