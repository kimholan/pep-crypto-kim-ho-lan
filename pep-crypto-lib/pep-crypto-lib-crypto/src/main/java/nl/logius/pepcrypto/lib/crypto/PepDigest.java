package nl.logius.pepcrypto.lib.crypto;

import java.security.MessageDigest;
import java.util.Objects;
import java.util.stream.Stream;

import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.LIB_CRYPTO_MODULE;

public enum PepDigest {
    SHA384("SHA-384");

    private final String digestName;

    PepDigest(String digestName) {
        this.digestName = digestName;
    }

    public byte[] digest(byte[] first) {
        return digest(first, null, null);
    }

    public byte[] digest(byte[] first, byte[] second) {
        return digest(first, second, null);
    }

    public byte[] digest(byte[] first, byte[] second, byte[] third) {
        var md = newMessageDigest();

        md.update(first);
        Stream.of(second, third)
              .filter(Objects::nonNull)
              .forEach(md::update);

        return md.digest();
    }

    private MessageDigest newMessageDigest() {
        return LIB_CRYPTO_MODULE.call(MessageDigest::getInstance, digestName);
    }

}
