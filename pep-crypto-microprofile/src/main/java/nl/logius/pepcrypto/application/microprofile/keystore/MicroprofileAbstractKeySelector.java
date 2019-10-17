package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

abstract class MicroprofileAbstractKeySelector {

    private final Comparator<PepRecipientKeyId> comparator;

    private final MicroprofileRequireUniqueResult requireUnique;

    private final MicroprofileRequireAnyResult requireAny;

    MicroprofileAbstractKeySelector(MicroprofileRequireAnyResult requireAny, MicroprofileRequireUniqueResult requireUnique) {
        this(PepRecipientKeyId.comparator(), requireAny, requireUnique);
    }

    MicroprofileAbstractKeySelector(Comparator<PepRecipientKeyId> comparator, MicroprofileRequireAnyResult requireAny, MicroprofileRequireUniqueResult requireUnique) {
        this.comparator = comparator;
        this.requireAny = requireAny;
        this.requireUnique = requireUnique;
    }

    UnaryOperator<List<PemEcPrivateKey>> doRequireAny(Predicate<PemEcPrivateKey> predicate) {
        return it -> requireAny.withPredicate(predicate)
                               .apply(it);
    }

    PemEcPrivateKey doRequireUniqueMatch(List<PemEcPrivateKey> serviceProviderKeys) {
        return requireUnique.requireUniqueResult(serviceProviderKeys);
    }

    Predicate<PemEcPrivateKey> predicateForMatchingPepRecipientKeyId(Predicate<PemEcPrivateKey> predicate, PepRecipientKeyId recipientKeyId) {
        return predicate.and(it -> it.isMatchingPepRecipientKeyId(comparator, recipientKeyId));
    }

}

