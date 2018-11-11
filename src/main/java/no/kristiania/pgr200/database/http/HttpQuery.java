package no.kristiania.pgr200.database.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class HttpQuery {

    private Map<String, String> parameters = new LinkedHashMap<>();

    public HttpQuery(String query) {
        if(query != null) {
            for (String parameter : query.split("&")) {
                parseParameter(parameter);
            }
        }
        else{parameters.put("status", "500");}
    }

    public HttpQuery() {
    }

    public void parseParameter(String parameter) {
        int equalsPos = parameter.indexOf('=');
        parameters.put(
                urlDecode(parameter.substring(0, equalsPos)),
                urlDecode(parameter.substring(equalsPos+1)));
    }

    @Override
    public String toString() {
        if (parameters.isEmpty()) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (String key : parameters.keySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result
                    .append(urlEncode(key))
                    .append("=")
                    .append(urlEncode(parameters.get(key)));
        }
        return result.toString();
    }

    private String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 URLDecode should always be supported", e);
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 URLDecode should always be supported", e);
        }
    }

    public HttpQuery put(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(parameters.get(key));
    }
}

