package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionMapper;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_DIRECT_RECEIVE_KEY_REQUIRED;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.NON_UNIQUE_MATCHES_DIRECT_RECEIVE_KEY;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.NO_MATCHES_DIRECT_RECEIVE_KEY;
import static nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId.comparatorWithDiversifier;

@ApplicationScoped
public class MicroprofileDirectReceiveKeySelector
        extends MicroprofileAbstractKeySelector {

    public MicroprofileDirectReceiveKeySelector() {
        super(comparatorWithDiversifier(),
                new MicroprofileRequireAnyResult(NO_MATCHES_DIRECT_RECEIVE_KEY),
                new MicroprofileRequireUniqueResult(NON_UNIQUE_MATCHES_DIRECT_RECEIVE_KEY)
        );
    }

    @MicroprofileExceptionMapper(MATCHING_DIRECT_RECEIVE_KEY_REQUIRED)
    public List<PemEcPrivateKey> filterByRecipientKeyId(List<PemEcPrivateKey> serviceProviderKeys, PepRecipientKeyId recipientKeyId) {
        var predicate = predicateForMatchingPepRecipientKeyId(PemEcPrivateKey::isDrkiKey, recipientKeyId);

        return doRequireAny(predicate).apply(serviceProviderKeys);
    }

    @MicroprofileExceptionMapper(MATCHING_DIRECT_RECEIVE_KEY_REQUIRED)
    public PemEcPrivateKey requireUniqueMatch(List<PemEcPrivateKey> serviceProviderKeys) {
        return doRequireUniqueMatch(serviceProviderKeys);
    }

}

