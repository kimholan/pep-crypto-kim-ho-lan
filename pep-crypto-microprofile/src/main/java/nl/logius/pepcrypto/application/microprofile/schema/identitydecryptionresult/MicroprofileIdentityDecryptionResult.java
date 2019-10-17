package nl.logius.pepcrypto.application.microprofile.schema.identitydecryptionresult;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASIdentityDecryptionResult;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded;

@JsonPropertyOrder({
        "bytes",
        "version",
        "type",
        "length",
        "identifier",
})
public class MicroprofileIdentityDecryptionResult
        extends OASIdentityDecryptionResult {

    public static MicroprofileIdentityDecryptionResult newInstance(PepIdentityOaepDecoded source) {
        var target = new MicroprofileIdentityDecryptionResult();

        target.version(source.getVersion())
              .identifier(source.getIdentifier())
              .type(source.getType())
              .length(source.getLength())
              .bytes(source.getBytes());

        return target;
    }

}
