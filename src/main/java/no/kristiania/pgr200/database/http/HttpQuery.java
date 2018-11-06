package no.kristiania.pgr200.database.http;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpQuery {

    Map<String, String> parameters = new LinkedHashMap<>();
    String uri;
    HttpPath httpPath;

    public HttpQuery(String uri) {
        httpPath = new HttpPath(uri);
        try {

            this.uri = uri;

            int questionPos = uri.indexOf("?");
            String parameter = uri.substring(questionPos + 1);


            String key, value;

            for (String s : parameter.split("&")) {
                int equalPos = s.indexOf("=");
                if (equalPos != -1) {
                    key = s.substring(0, equalPos);
                    value = s.substring(equalPos + 1);
                    switch (key) {
                        case "status":
                            parameters.put(key, value);
                            parameters.put("body", httpPath.getStatusMessage(value));

                            break;
                        case "Location":
                            value = URLDecoder.decode(value, "UTF-8");
                            parameters.put(key, value);
                            break;
                        case "body":
                          parameters.putIfAbsent("status", "200");
                            value = URLDecoder.decode(value, "UTF-8");
                            parameters.put(key, value);
                            break;
                    }
                }
            }
        } catch (Exception e) {
        }
    }


    public String getParameter(String key) {
       if(parameters.get(key) != null){
         return parameters.get(key);
       }
       return null;
    }


    @Override
    public String toString() {

        String query = "";

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if(uri.contains("status")) {
                query += entry.getKey() + "=" + entry.getValue();
            }
            else if(uri.contains("body")) {
                query += "&" + entry.getKey() + entry.getValue();
            }
        }
        return query;
    }
}
