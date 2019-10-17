package nl.logius.pepcrypto.lib.lang;

import java.util.ArrayList;
import java.util.List;

public class PepExceptionCollector {

    private List<Exception> exceptions;

    public PepExceptionCollector() {
        this(new ArrayList<>());
    }

    protected PepExceptionCollector(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public void ignore(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception cause) {
            exceptions.add(cause);
        }
    }

    public void requireNoExceptions(PepExceptionDetail exceptionDetail) {
        if (!exceptions.isEmpty()) {
            var exception = exceptionDetail.get();
            exceptions.forEach(exception::addSuppressed);
            throw exception;
        }
    }

}
