package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.key.PepPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

/**
 * ECPoint convertable to another recipient.
 */
public interface ApiConvertable<T extends PepPrivateKey> {

    default void convert() {
        var sourceEcPoint = getEcPoint();
        var conversionFunction = getConversionFunction();
        var convertedEcPoint = conversionFunction.apply(sourceEcPoint);

        setConvertedEcPoint(convertedEcPoint);
    }

    UnaryOperator<ECPoint> getConversionFunction();

    ECPoint getEcPoint();

    void setConvertedEcPoint(ECPoint ecPoint);

    T getSelectedConversionKey();

}
