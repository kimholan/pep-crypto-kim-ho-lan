package nl.logius.pepcrypto.lib;

import groovy.json.JsonOutput;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.Map;

import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.LIB_CRYPTO_MODULE;

public interface TestResources {

    static String resourceToString(String resource) {
        return LIB_CRYPTO_MODULE.call(() -> IOUtils.toString(TestResources.class.getResource(resource), Charset.defaultCharset()));
    }

    static String resourceToBase64(String resource) {
        var bytes = resourceToByteArray(resource);
        return Base64.getEncoder().encodeToString(bytes);
    }

    static byte[] resourceToByteArray(String resource) {
        return LIB_CRYPTO_MODULE.call(() -> IOUtils.toByteArray(TestResources.class.getResource(resource)));
    }

    static PemObject resourceToPemObject(String resource) {
        var string = resourceToString(resource);
        var stringReader = new StringReader(string);
        var reader = new PEMParser(stringReader);
        return LIB_CRYPTO_MODULE.call(reader::readPemObject);
    }

    static String resourceToSignatureValue(String resource) {
        try (var stream = TestResources.class.getResourceAsStream(resource);
             var reader = new InputStreamReader(stream)) {
            var strings = IOUtils.readLines(reader);
            return JsonOutput.toJson(Map.of(
                    "signatureType", strings.get(1).trim(),
                    "r", strings.get(3).trim(),
                    "s", strings.get(5).trim()
            ));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    static Map.Entry<String, byte[]> fromBase64Resource(Map.Entry<String, String> entry) {
        var resource = entry.getValue();
        var b64String = TestResources.resourceToString(resource);
        var value = Base64.getDecoder().decode(b64String);

        return new AbstractMap.SimpleEntry<>(entry.getKey(), value);
    }

}
