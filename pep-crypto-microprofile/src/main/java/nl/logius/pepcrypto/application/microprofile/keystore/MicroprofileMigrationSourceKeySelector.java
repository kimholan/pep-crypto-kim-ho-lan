package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionMapper;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Predicate;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_MIGRATION_SOURCE_KEY_REQUIRED;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.NO_MATCHES_MIGRATION_SOURCE_KEY;
import static nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId.comparatorWithMigrationId;

@ApplicationScoped
public class MicroprofileMigrationSourceKeySelector
        extends MicroprofileAbstractKeySelector {

    public MicroprofileMigrationSourceKeySelector() {
        super(comparatorWithMigrationId(),
                new MicroprofileRequireAnyResult(NO_MATCHES_MIGRATION_SOURCE_KEY),
                new MicroprofileRequireUniqueResult(NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY)
        );
    }

    @MicroprofileExceptionMapper(MATCHING_MIGRATION_SOURCE_KEY_REQUIRED)
    public List<PemEcPrivateKey> filterByRecipientKeyId(List<PemEcPrivateKey> serviceProviderKeys, PepRecipientKeyId recipientKeyId) {
        var predicate = predicateForMatchingPepRecipientKeyId(PemEcPrivateKey::isEpMigrationSourceKey, recipientKeyId);

        return doRequireAny(predicate).apply(serviceProviderKeys);
    }

    @MicroprofileExceptionMapper(MATCHING_MIGRATION_SOURCE_KEY_REQUIRED)
    public PemEcPrivateKey requireUniqueMatch(List<PemEcPrivateKey> serviceProviderKeys, String targetMigrant, BigInteger targetMigrantKeySetVersion) {
        var predicate = predicateForMatchingTargetMigrant(targetMigrant, targetMigrantKeySetVersion);
        var result = doRequireAny(predicate).apply(serviceProviderKeys);

        return doRequireUniqueMatch(result);
    }

    private Predicate<PemEcPrivateKey> predicateForMatchingTargetMigrant(String targetMigrant, BigInteger targetMigrantKeySetVersion) {
        return it -> isAnyTargetMigrant(targetMigrant, targetMigrantKeySetVersion)
                             || isSpecificTargetMigrant(it, targetMigrant, targetMigrantKeySetVersion);
    }

    private boolean isAnyTargetMigrant(String targetMigrant, BigInteger targetMigrantKeySetVersion) {
        return targetMigrant == null && targetMigrantKeySetVersion == null;
    }

    private boolean isSpecificTargetMigrant(PemEcPrivateKey key, String targetMigrant, BigInteger targetMigrantKeySetVersion) {
        return key.isMatchingTargetMigrant(targetMigrant) && key.isMatchingTargetMigrantKeySetVersion(targetMigrantKeySetVersion);
    }

}

