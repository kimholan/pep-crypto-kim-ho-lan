package nl.logius.pepcrypto.lib.urn;

import java.util.Optional;
import java.util.function.Predicate;

import static nl.logius.pepcrypto.lib.urn.PredicatedPatternSchemeKeyUrn.PATTERN_SCHEME_KEY_URN;

public enum PepSchemeKeyUrns {

    SCHEME_KEY_U("U"::equals),
    SCHEME_KEY_IPP("IP_P"::equals),
    SCHEME_KEY_PPP("PP_P"::equals),
    SCHEME_KEY_ANY(it -> true);

    private final Predicate<String> keyNamePredicate;

    PepSchemeKeyUrns(Predicate<String> keyNamePredicate) {
        this.keyNamePredicate = keyNamePredicate;
    }

    public PepSchemeKeyUrn asPepSchemeKeyUrn(String urn) {
        var matcher = Optional.ofNullable(urn)
                              .map(PATTERN_SCHEME_KEY_URN::matcher)
                              .orElse(null);
        return new PredicatedPatternSchemeKeyUrn(matcher, keyNamePredicate);
    }

}
