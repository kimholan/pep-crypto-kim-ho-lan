package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationexport;

import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationExportExchange;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeyStoreExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileServiceProviderKeyStoreExchange;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationSourcePrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Optional;

import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.MIGRATION_ID;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT_KEY_SET_VERSION;

class MicroprofilePseudonymMigrationExportExchange
        extends AbstractMicroprofileServiceProviderKeyStoreExchange<Asn1DecryptedPseudonym>
        implements ApiMigrationExportExchange, MicroprofileServiceProviderKeyStoreExchange {

    private final OASPseudonymMigrationExportRequest request;

    private ECPoint convertedEcPoint;

    private PemEcPrivateKey migrationSourceKey;

    MicroprofilePseudonymMigrationExportExchange(OASPseudonymMigrationExportRequest request) {
        super(request, request::getPseudonym);

        this.request = request;
    }

    String getMigrationSourceTargetMigrant() {
        return migrationSourceKey.getSpecifiedHeader(TARGET_MIGRANT);
    }

    BigInteger getMigrationSourceTargetKeySetVersion() {
        return new BigInteger(migrationSourceKey.getSpecifiedHeader(TARGET_MIGRANT_KEY_SET_VERSION));
    }

    String getMigrationSourceMigrationId() {
        return migrationSourceKey.getSpecifiedHeader(MIGRATION_ID);
    }

    void setMigrationSourceKey(PemEcPrivateKey migrationSourceKey) {
        this.migrationSourceKey = migrationSourceKey;
    }

    @Override
    public ECPoint getEcPoint() {
        return getMappedInput().asn1PseudonymValueAsEcPoint();
    }

    public ECPoint getConvertedEcPoint() {
        return convertedEcPoint;
    }

    @Override
    public void setConvertedEcPoint(ECPoint convertedEcPoint) {
        this.convertedEcPoint = convertedEcPoint;
    }

    @Override
    public PepEpMigrationSourcePrivateKey getSelectedConversionKey() {
        return PepEpMigrationSourcePrivateKey.newInstance(migrationSourceKey.getPrivateKey());
    }

    String getRequestMigrationId() {
        return request.getMigrationID();
    }

    boolean isMigrationTargetSelectionInvalid() {
        return getRequestTargetMigrant() == null ^ getRequestTargetMigrantKeySetVersion() == null;
    }

    String getRequestTargetMigrant() {
        return request.getTargetMigrant();
    }

    BigInteger getRequestTargetMigrantKeySetVersion() {
        return Optional.of(request)
                       .map(OASPseudonymMigrationExportRequest::getTargetMigrantKeySetVersion)
                       .map(BigInteger::new)
                       .orElse(null);
    }

}
