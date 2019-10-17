package nl.logius.pepcrypto.api.oid;

import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;

import java.util.function.Supplier;

public class Asn1SequenceDecodable
        implements ApiAsn1SequenceDecodable {

    private final byte[] rawInput;

    private String oid;

    public Asn1SequenceDecodable(Supplier<byte[]> rawInputSupplier) {
        rawInput = rawInputSupplier.get();
    }

    public Asn1SequenceDecodable(byte[] rawInput) {
        this.rawInput = rawInput;
    }

    @Override
    public byte[] getRawInput() {
        return rawInput.clone();
    }

    @Override
    public String getOid() {
        return oid;
    }

    @Override
    public void setOid(String oid) {
        this.oid = oid;
    }

}
