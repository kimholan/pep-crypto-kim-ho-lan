package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

class MicroprofileRequireAnyResult {

    private final MicroprofileExceptionDetail exceptionDetail;

    MicroprofileRequireAnyResult(MicroprofileExceptionDetail exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    private <T> List<T> filterByPredicate(List<T> items, Predicate<T> predicate) {
        var result = items.stream()
                          .filter(predicate)
                          .collect(Collectors.toList());

        exceptionDetail.requireFalse(result.isEmpty());

        return result;
    }

    <T> UnaryOperator<List<T>> withPredicate(Predicate<T> predicate) {
        return it -> filterByPredicate(it, predicate);
    }

}
