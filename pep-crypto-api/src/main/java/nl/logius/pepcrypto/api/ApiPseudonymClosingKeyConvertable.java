package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.algorithm.PepDecryptedPseudonymClosingKeyConversion;
import nl.logius.pepcrypto.lib.crypto.key.PepDpClosingKeyConversionClosingPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

public interface ApiPseudonymClosingKeyConvertable
        extends ApiConvertable<PepDpClosingKeyConversionClosingPrivateKey> {

    @Override
    default UnaryOperator<ECPoint> getConversionFunction() {
        return PepDecryptedPseudonymClosingKeyConversion.asFunction(getSelectedConversionKey());
    }

}
