package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.net.Socket;

public class HttpResponse {

    private HttpHeaders responseHeaders = new HttpHeaders();
    private int statusCode;
    private String statusText;
    private String body;

    public HttpResponse(Socket socket) throws IOException {
        parseStatusLine(HttpIO.readLine(socket.getInputStream()));
        responseHeaders.readHeaders(socket.getInputStream());
        body = HttpIO.readBody(socket.getInputStream(), responseHeaders.getContentLength());
    }

    void parseStatusLine(String statusLine) {
        int firstSpacePos = statusLine.indexOf(' ');
        int secondSpacePos = statusLine.indexOf(' ', firstSpacePos+1);
        this.statusCode = Integer.parseInt(statusLine.substring(firstSpacePos+1, secondSpacePos));
        this.statusText = statusLine.substring(secondSpacePos+1);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getHeader(String key) {
        return responseHeaders.get(key);
    }

    public String getBody() {
        return body;
    }

}
