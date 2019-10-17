package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionMapper;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Predicate;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_MIGRATION_TARGET_KEY_REQUIRED;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.NON_UNIQUE_MATCHES_MIGRATION_TARGET_KEY;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.NO_MATCHES_MIGRATION_TARGET_KEY;
import static nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId.comparatorWithMigrationId;

@ApplicationScoped
public class MicroprofileMigrationTargetKeySelector
        extends MicroprofileAbstractKeySelector {

    public MicroprofileMigrationTargetKeySelector() {
        super(comparatorWithMigrationId(),
                new MicroprofileRequireAnyResult(NO_MATCHES_MIGRATION_TARGET_KEY),
                new MicroprofileRequireUniqueResult(NON_UNIQUE_MATCHES_MIGRATION_TARGET_KEY)
        );
    }

    @MicroprofileExceptionMapper(MATCHING_MIGRATION_TARGET_KEY_REQUIRED)
    public List<PemEcPrivateKey> filterByRecipientKeyId(List<PemEcPrivateKey> serviceProviderKeys, PepRecipientKeyId recipientKeyId) {
        var predicate = predicateForMatchingPepRecipientKeyId(PemEcPrivateKey::isEpMigrationTargetKey, recipientKeyId);

        return doRequireAny(predicate).apply(serviceProviderKeys);
    }

    @MicroprofileExceptionMapper(MATCHING_MIGRATION_TARGET_KEY_REQUIRED)
    public PemEcPrivateKey filterByUniqueRecipientKeyId(List<PemEcPrivateKey> serviceProviderKeys, String sourceMigrant, BigInteger sourceMigrantKeySetVersion) {
        var predicate = predicateForMatchingSourceMigrant(sourceMigrant, sourceMigrantKeySetVersion);
        var result = doRequireAny(predicate).apply(serviceProviderKeys);

        return doRequireUniqueMatch(result);
    }

    private Predicate<PemEcPrivateKey> predicateForMatchingSourceMigrant(String sourceMigrant, BigInteger sourceMigrantKeySetVersion) {
        return it -> isAnySourceMigrant(sourceMigrant, sourceMigrantKeySetVersion)
                             || isSpecificSourceMigrant(it, sourceMigrant, sourceMigrantKeySetVersion);
    }

    private boolean isAnySourceMigrant(String sourceMigrant, BigInteger sourceMigrantKeySetVersion) {
        return sourceMigrant == null && sourceMigrantKeySetVersion == null;
    }

    private boolean isSpecificSourceMigrant(PemEcPrivateKey key, String sourceMigrant, BigInteger sourceMigrantKeySetVersion) {
        return key.isMatchingSourceMigrant(sourceMigrant) && key.isMatchingSourceMigrantKeySetVersion(sourceMigrantKeySetVersion);
    }

}

