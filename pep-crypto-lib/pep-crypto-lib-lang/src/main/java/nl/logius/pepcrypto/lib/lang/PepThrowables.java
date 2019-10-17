package nl.logius.pepcrypto.lib.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Collects exception details and messages.
 */
public interface PepThrowables<T extends Throwable & PepThrowables<T>> {

    static <T extends Throwable & PepThrowables<T>> T causeFor(Throwable cause, T thrown) {
        return Optional.ofNullable(thrown)
                       .filter(it -> Objects.isNull(it.getCause()))
                       .map(it -> (T) it.initCause(cause))
                       .orElse(thrown);
    }

    String getDetails();

    void setDetails(String message);

    Throwable[] getSuppressedExceptions();

    default T details(String details) {
        if (getDetails() == null) {
            setDetails(details);
        }
        return (T) this;
    }

    default T cause(Throwable cause) {
        return causeFor(cause, (T) this);
    }

    default String getAllMessages() {
        return StringUtils.trimToEmpty(String.join("; ",
                Stream.concat(
                        Stream.of(getDetails()),
                        Stream.of(getSuppressedExceptions())
                              .map(Throwable::getMessage)
                ).toArray(String[]::new)
        ));
    }

}
