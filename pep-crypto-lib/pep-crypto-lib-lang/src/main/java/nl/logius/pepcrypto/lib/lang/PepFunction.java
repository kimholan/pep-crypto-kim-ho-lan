package nl.logius.pepcrypto.lib.lang;

/**
 * Functional interface for functions allowing checked exceptions.
 */
@FunctionalInterface
public interface PepFunction<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws Exception;

}
