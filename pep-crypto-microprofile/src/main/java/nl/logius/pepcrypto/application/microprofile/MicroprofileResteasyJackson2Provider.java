package nl.logius.pepcrypto.application.microprofile;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

import javax.annotation.Priority;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import static com.fasterxml.jackson.core.JsonParser.Feature.STRICT_DUPLICATE_DETECTION;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY;
import static java.lang.Integer.MAX_VALUE;

@Priority(MAX_VALUE)
@Provider
public class MicroprofileResteasyJackson2Provider
        extends ResteasyJackson2Provider {

    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
        var mapper = super.locateMapper(type, mediaType);

        mapper.enable(STRICT_DUPLICATE_DETECTION);
        mapper.enable(FAIL_ON_READING_DUP_TREE_KEY);

        return mapper;
    }

}
