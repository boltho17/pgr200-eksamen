package no.kristiania.pgr200.database.http;

import java.util.Optional;

public class HttpPath {

    private String path;
    private HttpQuery query = new HttpQuery();

    public HttpPath(String fullPath) {
        int questionPos = fullPath.indexOf('?');
        if (questionPos > 0) {
            this.path = fullPath.substring(0, questionPos);
            this.query = new HttpQuery(fullPath.substring(questionPos+1));
        } else {
            this.path = fullPath;
        }
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query.toString();
    }

    public Optional<String> getParameter(String key) {
        return query.get(key);
    }

    public void setParameter(String key, String value) {
        query.put(key, value);
    }

    @Override
    public String toString() {
        return getPath() + (getQuery() != null ? "?" + getQuery() : "");
    }

    public HttpQuery query() {
        return query;
    }
}
