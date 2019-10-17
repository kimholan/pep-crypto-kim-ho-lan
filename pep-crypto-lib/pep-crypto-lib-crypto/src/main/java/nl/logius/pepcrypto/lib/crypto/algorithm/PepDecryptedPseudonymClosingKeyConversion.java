package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.key.PepDpClosingKeyConversionClosingPrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

public enum PepDecryptedPseudonymClosingKeyConversion
        implements PepExceptionDetail {

    /**
     * Pseudonym closing key conversion failed.
     */
    CLOSING_KEY_CONVERSION_FAILED;

    /**
     * Pseudonym conversion due to closing key change.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 9 Pseudonym Conversion, Protocol 12.
     *
     * @param ecPoint              Encoded pseudonym.
     * @param closingKeyConversion Derived conversion key.
     * @return ecPoint converted using the derived conversion key.
     */
    public static ECPoint dpClosingKeyConversion(ECPoint ecPoint, PepDpClosingKeyConversionClosingPrivateKey closingKeyConversion) {
        return CLOSING_KEY_CONVERSION_FAILED.call(() -> ecPoint.multiply(closingKeyConversion.getValue()));
    }

    public static UnaryOperator<ECPoint> asFunction(PepDpClosingKeyConversionClosingPrivateKey closingKeyConversionPrivateKey) {
        return it -> dpClosingKeyConversion(it, closingKeyConversionPrivateKey);
    }

}
