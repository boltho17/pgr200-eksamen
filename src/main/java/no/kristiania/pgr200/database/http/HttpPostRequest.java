package no.kristiania.pgr200.database.http;

import java.io.IOException;

public class HttpPostRequest extends HttpRequest {

    private String method = "POST";

    public HttpPostRequest(String hostname, int port, String requestTarget) throws IOException {
        super(hostname, port, requestTarget);
    }
}
