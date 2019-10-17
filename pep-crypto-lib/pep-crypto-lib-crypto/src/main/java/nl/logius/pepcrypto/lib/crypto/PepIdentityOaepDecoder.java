package nl.logius.pepcrypto.lib.crypto;

import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Optional;

import static java.util.Arrays.copyOfRange;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getKeyLengthBitCount;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getKeyLengthByteCount;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.OAEP_ENCODED_IDENTITY_BIT_LENGTH;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.OAEP_ENCODED_IDENTITY_PADDING;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.OAEP_ENCODED_IDENTITY_RAW_INPUT;
import static nl.logius.pepcrypto.lib.crypto.PepDigest.SHA384;

public enum PepIdentityOaepDecoder {
    ;

    private static final int OAEP_MESSAGE_LENGTH = 18;

    private static final int OAEP_HASH_LENGTH = 10;

    private static final int OAEP_DB_LENGTH = OAEP_HASH_LENGTH + 1 + OAEP_MESSAGE_LENGTH;

    private static final BigInteger BI_256 = BigInteger.valueOf(256);

    /**
     * Decode decrypted ECPoint as an 'Identity'.
     *
     * @param ecPoint Elliptic Curve point that encodes the Identity
     * @return decoded Identity
     */
    public static byte[] oaepDecode(ECPoint ecPoint) {
        var oaepArray = toOaepArray(ecPoint);

        oaepArray = requireValidOaepEncodedInput(oaepArray);

        var maskedDb = oaepCalulateMaskedDb(oaepArray);
        var db = oaepCalculateDb(oaepArray, maskedDb);
        db = requireValidOaepPadding(db);

        return copyOfRange(db, OAEP_HASH_LENGTH + 1, OAEP_DB_LENGTH);
    }

    private static void requireValidOaepBitLength(BigInteger oaepEncoded) {
        if (oaepEncoded.bitLength() > getKeyLengthBitCount()) {
            throw new PepRuntimeException(OAEP_ENCODED_IDENTITY_BIT_LENGTH);
        }
    }

    private static byte[] toOaepArray(ECPoint ecPoint) {
        var oaepEncoded = ecPoint.normalize().getXCoord().toBigInteger();

        requireValidOaepBitLength(oaepEncoded);

        var result = new byte[getKeyLengthByteCount()];

        for (var i = 0; i < getKeyLengthByteCount(); ++i) {
            result[getKeyLengthByteCount() - 1 - i] = (byte) oaepEncoded.mod(BI_256).intValue();
            oaepEncoded = oaepEncoded.divide(BI_256);
        }

        return result;
    }

    private static byte[] oaepCalculateDb(byte[] oaepArray, byte[] maskedDb) {
        var db = new byte[OAEP_DB_LENGTH];
        var seed = oaepCalculateSeed(oaepArray, maskedDb);
        var dbMask = SHA384.digest(seed, new byte[]{0, 0, 0, 0});

        for (var i = 0; i < OAEP_DB_LENGTH; ++i) {
            db[i] = (byte) (maskedDb[i] ^ dbMask[i]);
        }

        return db;
    }

    private static byte[] oaepCalulateMaskedDb(byte[] oaepArray) {
        var maskedDB = new byte[OAEP_DB_LENGTH];

        for (var i = 0; i < OAEP_DB_LENGTH; ++i) {
            maskedDB[i] = oaepArray[i + 1 + OAEP_HASH_LENGTH];
        }

        return maskedDB;
    }

    private static byte[] oaepCalculateSeed(byte[] oaepArray, byte[] maskedDB) {
        var seed = new byte[OAEP_HASH_LENGTH];
        var seedMask = SHA384.digest(maskedDB, new byte[]{0, 0, 0, 0});
        var maskedSeed = new byte[OAEP_HASH_LENGTH];

        System.arraycopy(oaepArray, 1, maskedSeed, 0, OAEP_HASH_LENGTH);
        for (var i = 0; i < OAEP_HASH_LENGTH; ++i) {
            seed[i] = (byte) (maskedSeed[i] ^ seedMask[i]);
        }

        return seed;
    }

    private static byte[] requireValidOaepEncodedInput(byte[] oaepArray) {
        return Optional.ofNullable(oaepArray)
                       .filter(PepIdentityOaepDecoder::isValidOaepInput)
                       .orElseThrow(OAEP_ENCODED_IDENTITY_RAW_INPUT);
    }

    private static boolean isValidOaepInput(byte[] oaepArray) {
        var isValidInput = oaepArray.length % 2 == 0;

        isValidInput &= oaepArray.length - OAEP_MESSAGE_LENGTH > 0;
        isValidInput &= OAEP_HASH_LENGTH == (oaepArray.length - OAEP_MESSAGE_LENGTH - 2) / 2;
        isValidInput &= oaepArray[0] == 0;

        return isValidInput;
    }

    private static byte[] requireValidOaepPadding(byte[] db) {
        return Optional.ofNullable(db)
                       .filter(PepIdentityOaepDecoder::isValidOaepPadding)
                       .orElseThrow(OAEP_ENCODED_IDENTITY_PADDING);
    }

    private static boolean isValidOaepPadding(byte[] db) {
        var isValid = db[OAEP_HASH_LENGTH] == 1;
        var emptyStringHash = SHA384.digest(new byte[0]);

        for (var i = 0; isValid && i < OAEP_HASH_LENGTH; i++) {
            isValid = emptyStringHash[i] == db[i];
        }

        return isValid;
    }

}
