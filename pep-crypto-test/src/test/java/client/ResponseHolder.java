package client;

import groovy.json.JsonSlurper;

import java.net.http.HttpResponse;
import java.util.HashMap;

public class ResponseHolder
        extends HashMap<String, Object> {

    public ResponseHolder(HttpResponse<String> response) {
        put("body", response.body());
        put("statusCode", response.statusCode());

        try {
            put("json", new JsonSlurper().parseText(response.body()));
        } catch (RuntimeException cause) {
            // ignored
        }
    }

}
