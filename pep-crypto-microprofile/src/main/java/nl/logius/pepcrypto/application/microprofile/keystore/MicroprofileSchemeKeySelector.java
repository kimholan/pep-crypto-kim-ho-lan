package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionMapper;
import org.bouncycastle.math.ec.ECPoint;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_SCHEME_KEY_NOT_FOUND;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.NO_SCHEME_KEY_VALUE;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SCHEME_KEY_SELECTOR;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.UNIQUE_MATCHING_SCHEME_KEY_REQUIRED;

@ApplicationScoped
public class MicroprofileSchemeKeySelector {

    @MicroprofileExceptionMapper(SCHEME_KEY_SELECTOR)
    public ECPoint selectSchemeKey(Map<String, ECPoint> schemeKeys, Predicate<String> matchingSchemeKeyPredicate) {
        var matchingUrns = schemeKeys.keySet().stream()
                                     .filter(matchingSchemeKeyPredicate)
                                     .collect(Collectors.toList());

        var nonEmptyMatchingUrns = Optional.of(matchingUrns)
                                           .filter(it -> !it.isEmpty())
                                           .orElseThrow(MATCHING_SCHEME_KEY_NOT_FOUND);

        var uniqueMatchingUrn = Optional.of(nonEmptyMatchingUrns)
                                        .filter(it -> it.size() == 1)
                                        .map(it -> it.get(0))
                                        .orElseThrow(UNIQUE_MATCHING_SCHEME_KEY_REQUIRED);

        return Optional.of(uniqueMatchingUrn)
                       .map(schemeKeys::get)
                       .orElseThrow(NO_SCHEME_KEY_VALUE);
    }

}
