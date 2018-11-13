package no.kristiania.pgr200.database.http;

import java.io.IOException;

public class HttpDeleteRequest extends HttpRequest {

    private String method = "DELETE";

    public HttpDeleteRequest(String hostname, int port, String requestTarget) throws IOException {
        super(hostname, port, requestTarget);
    }
}