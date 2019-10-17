package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail;

import java.util.List;
import java.util.Optional;

class MicroprofileRequireUniqueResult {

    private MicroprofileExceptionDetail exceptionDetail;

    MicroprofileRequireUniqueResult(MicroprofileExceptionDetail exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    <T> T requireUniqueResult(List<T> serviceProviderKeys) {
        var uniqueMatch = Optional.ofNullable(serviceProviderKeys)
                                  .filter(it -> it.size() == 1)
                                  .map(it -> it.get(0));

        exceptionDetail.requireTrue(uniqueMatch.isPresent());

        return uniqueMatch.orElse(null);
    }

}
