package nl.logius.pepcrypto.lib.urn;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PredicatedPatternSchemeKeyUrn
        implements PepSchemeKeyUrn {

    private static final String GROUP_NAME_ENVIRONMENT = "ENV";

    private static final String GROUP_NAME_SCHEME_KEY_SET_VERSION = "SKSV";

    private static final String GROUP_NAME_SCHEME_KEY_VERSION = "SKV";

    private static final String GROUP_NAME_SCHEME_KEY_NAME = "SKN";

    private static final String COLON = ":";

    private static final String GROUP_START = "(";

    private static final String GROUP_END = ")";

    private static final String GROUP_NAME_START = "?<";

    private static final String GROUP_NAME_END = ">";

    private static final String GREEDY_NON_COLONS = "[^:]+";

    private static final String GREEDY_POSITIVE_NUMBER = "[1-9][0-9]*";

    private static final String REGEX_SCHEME_KEY_URN = System.getProperty(
            "PepUrns.regexSchemeKeyUrn",
            "urn:nl-gdi-eid:1.0:pp-key"
                    + COLON
                    + GROUP_START + GROUP_NAME_START + GROUP_NAME_ENVIRONMENT + GROUP_NAME_END + GREEDY_NON_COLONS + GROUP_END
                    + COLON
                    + GROUP_START + GROUP_NAME_START + GROUP_NAME_SCHEME_KEY_SET_VERSION + GROUP_NAME_END + GREEDY_POSITIVE_NUMBER + GROUP_END
                    + COLON
                    + GROUP_START + GROUP_NAME_START + GROUP_NAME_SCHEME_KEY_NAME + GROUP_NAME_END + GREEDY_NON_COLONS + GROUP_END
                    + COLON
                    + GROUP_START + GROUP_NAME_START + GROUP_NAME_SCHEME_KEY_VERSION + GROUP_NAME_END + GREEDY_POSITIVE_NUMBER + GROUP_END
                    + "$"
    );

    static final Pattern PATTERN_SCHEME_KEY_URN = Pattern.compile(REGEX_SCHEME_KEY_URN);

    private final Matcher schemeKeyUrnMatcher;

    private final boolean matches;

    PredicatedPatternSchemeKeyUrn(Matcher schemeKeyUrnMatcher, Predicate<String> keyNamePredicate) {
        this.schemeKeyUrnMatcher = schemeKeyUrnMatcher;
        matches = Optional.ofNullable(schemeKeyUrnMatcher)
                          .filter(Matcher::matches)
                          .map(it -> it.group(GROUP_NAME_SCHEME_KEY_NAME))
                          .filter(keyNamePredicate)
                          .isPresent();
    }

    @Override
    public boolean matches() {
        return matches;
    }

    @Override
    public String getEnvironment() {
        return getGroup(GROUP_NAME_ENVIRONMENT);
    }

    @Override
    public String getSchemeKeySetVersion() {
        return getGroup(GROUP_NAME_SCHEME_KEY_SET_VERSION);
    }

    @Override
    public String getSchemeKeyVersion() {
        return getGroup(GROUP_NAME_SCHEME_KEY_VERSION);
    }

    @Override
    public String getSchemeKeyName() {
        return getGroup(GROUP_NAME_SCHEME_KEY_NAME);
    }

    private String getGroup(String groupName) {
        return Optional.ofNullable(schemeKeyUrnMatcher)
                       .filter(it -> matches)
                       .map(it -> it.group(groupName))
                       .orElse(null);
    }

}
