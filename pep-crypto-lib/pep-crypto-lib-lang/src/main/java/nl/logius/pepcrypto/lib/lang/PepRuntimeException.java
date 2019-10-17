package nl.logius.pepcrypto.lib.lang;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Unchecked exceptions specifying their cause as an {@link PepExceptionDetail}.
 */
public class PepRuntimeException
        extends RuntimeException
        implements PepThrowables<PepRuntimeException> {

    private static final long serialVersionUID = 1285345099349687316L;

    private final PepExceptionDetail exceptionDetail;

    private String details;

    private Throwable initCause;

    public PepRuntimeException(PepExceptionDetail detail) {
        exceptionDetail = detail;
        details = detail.getDetailName();
    }

    public PepRuntimeException(PepExceptionDetail detail, Exception cause) {
        super(cause);

        exceptionDetail = Objects.requireNonNull(detail);
        details = detail.getDetailName();
    }

    public boolean isExceptionDetail(PepExceptionDetail exceptionDetail) {
        return this.exceptionDetail == Objects.requireNonNull(exceptionDetail);
    }

    public boolean hasExceptionDetail(PepExceptionDetail exceptionDetail) {
        return Arrays.stream(ExceptionUtils.getThrowables(this))
                     .filter(PepRuntimeException.class::isInstance)
                     .map(PepRuntimeException.class::cast)
                     .anyMatch(it -> it.isExceptionDetail(exceptionDetail));
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public Throwable[] getSuppressedExceptions() {
        return getSuppressed();
    }

    @Override
    public String getMessage() {
        return getAllMessages();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        if (initCause != cause && cause != this) {
            initCause = cause;
        }
        super.initCause(cause);
        return this;
    }

}
