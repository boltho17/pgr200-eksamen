package no.kristiania.pgr200.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpPath {

    String path;
    Map<String, String> statusMessages = new HashMap<>();

    public HttpPath(String path) {
        this.path = path;
        populateStatusMessages();
    }

    public HttpQuery getQuery() {
        if (path.contains("?")) {
            return new HttpQuery(path);
        } else return null;
    }

    public String getPath() {

        if (path.contains("?")) {
            int questionPos = path.indexOf("?");
            String substring = path.substring(0, questionPos);
            return substring;
        }
        return path;
    }

    public ArrayList<String> getPathParts() {
        int questionPos = path.indexOf("?");
        String pathParts = path.substring(0, questionPos);

        String[] partsArray = pathParts.split("/");
        ArrayList<String> pathPartsArrayList = new ArrayList<>(Arrays.asList(partsArray));
        pathPartsArrayList.remove(0);

        return pathPartsArrayList;
    }

    public String getParameter(String key) {
        HttpQuery httpQuery = new HttpQuery(path);
        return httpQuery.getParameter(key);
    }

    public void populateStatusMessages() {
        statusMessages.put("100", "Continue");
        statusMessages.put("101", "Switching Protocols");
        statusMessages.put("200", "OK");
        statusMessages.put("201", "Created");
        statusMessages.put("202", "Accepted");
        statusMessages.put("203", " Non - Authoritative Information");
        statusMessages.put("204", "No Content");
        statusMessages.put("205", " Reset Content");
        statusMessages.put("206", "Partial Content");
        statusMessages.put("300", "Multiple Choices");
        statusMessages.put("301", "Moved Permanently");
        statusMessages.put("302", "Found");
        statusMessages.put("303", "See Other");
        statusMessages.put("304", "Not Modified");
        statusMessages.put("305", "Use Proxy");
        statusMessages.put("307", "Temporary Redirect");
        statusMessages.put("400", "Bad Request");
        statusMessages.put("401", "Unauthorized");
        statusMessages.put("402", "Payment Required");
        statusMessages.put("403", "Forbidden");
        statusMessages.put("404", "Not Found");
        statusMessages.put("405", "Method Not Allowed");
        statusMessages.put("406", "Not Acceptable");
        statusMessages.put("407", "Proxy Authentication Required");
        statusMessages.put("408", "Request Timeout");
        statusMessages.put("409", "Conflict");
        statusMessages.put("410", "Gone");
        statusMessages.put("411", "Length Required");
        statusMessages.put("412", "Precondition Failed");
        statusMessages.put("413", "Payload Too Large");
        statusMessages.put("414", "URI Too Long");
        statusMessages.put("415", "Unsupported Media Type");
        statusMessages.put("416", "Range Not Satisfiable");
        statusMessages.put("417", "Expectation Failed");
        statusMessages.put("426", "Upgrade Required");
        statusMessages.put("500", "Internal Server Error");
        statusMessages.put("501", "Not Implemented");
        statusMessages.put("502", "Bad Gateway");
        statusMessages.put("503", "Service Unavailable");
        statusMessages.put("504", "Gateway Timeout");
        statusMessages.put("505", "HTTP Version Not Supported");
    }

    public String getStatusMessage(String key) {
        return statusMessages.get(key);
    }
}
