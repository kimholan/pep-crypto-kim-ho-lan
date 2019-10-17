package nl.logius.pepcrypto.lib.crypto;

import org.bouncycastle.math.ec.ECPoint;

/**
 * Decodes the identity data encoded in the byte representation of an ECPoint.
 */
public class PepIdentityOaepDecoded {

    private static final String STRING_FORMAT_HEX = "%02X";

    private static final int OFFSET_TO_DATA = 3;

    private static final int INDEX_OF_VERSION = 0;

    private static final int INDEX_OF_TYPE = 1;

    private static final int INDEX_OF_LENGTH = 2;

    private final String version;

    private final String type;

    private final int length;

    private final String identifier;

    private final byte[] bytes;

    public PepIdentityOaepDecoded(ECPoint ecPoint) {
        this(PepIdentityOaepDecoder.oaepDecode(ecPoint));
    }

    public PepIdentityOaepDecoded(byte[] bytes) {
        this.bytes = bytes.clone();
        version = String.format(STRING_FORMAT_HEX, bytes[INDEX_OF_VERSION]);
        type = Character.toString(bytes[INDEX_OF_TYPE]);
        length = bytes[INDEX_OF_LENGTH];
        identifier = new String(bytes, OFFSET_TO_DATA, length);
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public String getIdentifier() {
        return identifier;
    }

    public byte[] getBytes() {
        return bytes.clone();
    }

}
