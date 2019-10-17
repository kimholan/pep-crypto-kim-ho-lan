package nl.logius.pepcrypto.lib.lang;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Exception details.
 */
public interface PepExceptionDetail
        extends Supplier<PepRuntimeException>, Serializable {

    /**
     * Wrap all exceptions caused by the callable in the supplied runtime get.
     *
     * @param exceptionDetail Supplies the exception that wraps exceptions from the callable function
     * @param callable        callable function that might throw an exception to be wrapped
     * @param <R>             return type of the callable function
     * @return Returns the return value of the callable function
     */
    static <R> R call(PepExceptionDetail exceptionDetail, Callable<R> callable) {
        try {
            return callable.call();
        } catch (Exception cause) {  // Catch Exception noodzakelijk voor generieke afhandeling
            throw exceptionDetail.get()
                                 .details(cause.getMessage())
                                 .cause(cause);
        }
    }

    /**
     * Wrap all exceptions caused by the callable in the supplied runtime get.
     *
     * @param exceptionDetail Supplies the exception that suppresses exceptions from the callable function
     * @param callable        callable function that might throw an exception to be wrapped
     * @param <R>             return type of the callable function
     * @return Returns the return value of the callable function
     */
    static <R> R suppress(PepExceptionDetail exceptionDetail, Callable<R> callable) {
        try {
            return callable.call();
        } catch (PepRuntimeException cause) {
            var pepRuntimeException = exceptionDetail.get();
            pepRuntimeException.addSuppressed(cause);
            throw pepRuntimeException;
        } catch (Exception cause) {
            throw new PepRuntimeException(exceptionDetail, cause);
        }
    }

    /**
     * Wrap all exceptions caused by the callable in the supplied runtime get.
     *
     * @param exceptionDetail Supplies the exception that wraps exceptions from the callable function
     * @param function        callable function that might throw an exception to be wrapped
     * @param <X>             type of function argument
     * @param <R>             return type of the callable function
     * @return Returns the return value of the callable function
     */
    static <X, R> R call(PepExceptionDetail exceptionDetail, PepFunction<X, R> function, X argument) {
        try {
            return function.apply(argument);
        } catch (Exception cause) {  // Catch Exception noodzakelijk voor generieke afhandeling
            throw exceptionDetail.get()
                                 .details(cause.getMessage())
                                 .cause(cause);
        }
    }

    /**
     * Calls callable wrapping all exceptions as a runtime exception with this exception detail.
     *
     * @param callable Callable to call.
     * @return Return value of callable.
     */
    default <R> R call(Callable<R> callable) {
        return call(this, callable);
    }

    /**
     * Calls callable suppressing all exceptions as a runtime exception with this exception detail.
     *
     * @param callable Callable to call.
     * @return Return value of callable.
     */
    default <R> R suppress(Callable<R> callable) {
        return suppress(this, callable);
    }

    /**
     * Wrap all exceptions caused by the callable in the supplied runtime get.
     *
     * @param function callable function that might throw an exception to be wrapped
     * @param <X>      type of function argument
     * @param <R>      return type of the callable function
     * @return Returns the return value of the callable function
     */
    default <X, R> R call(PepFunction<X, R> function, X value) {
        return call(this, function, value);
    }

    /**
     * Exception detail name.
     * <p>
     * Preferably a symbolic constant.
     *
     * @return Name of exception detail.
     */
    default String getDetailName() {
        return name();
    }

    /**
     * Throw this detail if value is false.
     *
     * @param value Value to assert.
     */
    default void requireTrue(boolean value) {
        requireFalse(!value);
    }

    /**
     * Throw this detail if value is true.
     *
     * @param value Value to assert.
     */
    default void requireFalse(boolean value) {
        if (value) {
            throw get();
        }
    }

    /**
     * Throw this detail if {@link Objects#equals(Object, Object)}  evaluates to false.
     *
     * @param loperand Object to compare to roperand.
     * @param roperand Object to compare to loperand.
     */
    default void requireEquals(Object loperand, Object roperand) {
        requireTrue(Objects.equals(loperand, roperand));
    }

    /**
     * Exception detail name.
     *
     * @return Name of exception detail.
     */
    String name();

    /**
     * Supply a runtime exception having this exception detail.
     */
    @Override
    default PepRuntimeException get() {
        return new PepRuntimeException(this);
    }

}
