package nl.logius.pepcrypto.lib.urn;

import java.util.Optional;

public interface PepSchemeKeyUrn {

    private static boolean isMatchingString(String expected, Object actual) {
        return Optional.ofNullable(actual)
                       .map(Object::toString)
                       .filter(it -> it.equals(expected))
                       .isPresent();
    }

    String getEnvironment();

    default boolean isMatchingEnvironment(Object actual) {
        return isMatchingString(getEnvironment(), actual);
    }

    String getSchemeKeySetVersion();

    default boolean isMatchingSchemeKeySetVersionString(Object actual) {
        return isMatchingString(getSchemeKeySetVersion(), actual);
    }

    String getSchemeKeyVersion();

    default boolean isMatchingSchemeKeyVersionString(Object actual) {
        return isMatchingString(getSchemeKeyVersion(), actual);
    }

    String getSchemeKeyName();

    default boolean isMatchingSchemeKeyName(Object actual) {
        return isMatchingString(getSchemeKeyName(), actual);
    }

    boolean matches();

}
