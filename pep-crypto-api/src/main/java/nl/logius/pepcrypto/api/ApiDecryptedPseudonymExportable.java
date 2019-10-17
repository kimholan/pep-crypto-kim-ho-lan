package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.algorithm.PepDecryptedPseudonymExport;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationSourcePrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

/**
 * Convertable ECPoint to change it to an value importable by another service provider.
 */
public interface ApiDecryptedPseudonymExportable
        extends ApiConvertable<PepEpMigrationSourcePrivateKey> {

    @Override
    default UnaryOperator<ECPoint> getConversionFunction() {
        return PepDecryptedPseudonymExport.asFunction(getSelectedConversionKey());
    }

}
