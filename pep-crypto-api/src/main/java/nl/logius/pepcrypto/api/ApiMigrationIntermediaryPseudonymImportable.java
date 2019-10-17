package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.algorithm.PepMigrationIntermediaryPseudonymImport;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationTargetPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

/**
 * Convertable ECPoint to change it to an value importable by another service provider.
 */
public interface ApiMigrationIntermediaryPseudonymImportable
        extends ApiConvertable<PepEpMigrationTargetPrivateKey> {

    @Override
    default UnaryOperator<ECPoint> getConversionFunction() {
        return PepMigrationIntermediaryPseudonymImport.asFunction(getSelectedConversionKey());
    }

}
