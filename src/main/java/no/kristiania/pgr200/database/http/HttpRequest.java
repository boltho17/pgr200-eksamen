package no.kristiania.pgr200.database.http;
import no.kristiania.pgr200.database.commandline.ConferenceCommandLineClient;

import java.io.IOException;
import java.net.Socket;

public class HttpRequest {

    private String hostname;
    private int port;
    private String requestTarget;
    private String method = "GET";
    private String body;
    private HttpHeaders httpHeaders;
    ConferenceCommandLineClient conferenceCommandLineClient;


    public HttpRequest(String hostname, int port, String requestTarget) {
        this.hostname = hostname;
        this.port = port;
        this.requestTarget = requestTarget;
        this.httpHeaders = new HttpHeaders()
                .put("Connection", "close")
                .put("Host", hostname);
    }

    public static void main(String[] args) throws IOException {
        new HttpRequest("urlecho.appspot.com", 80, "/echo").execute();
    }

    public HttpResponse execute() throws IOException {
        try (Socket socket = new Socket(hostname, port)) {
            writeRequestLine(socket);

            if (body != null) {
                httpHeaders.setContentLength(body.getBytes().length);
            }
            httpHeaders.writeTo(socket.getOutputStream());
            if (body != null) {
                socket.getOutputStream().write(body.getBytes());
            }

            return new HttpResponse(socket);
        }
    }

    public void writeRequestLine(Socket socket) throws IOException {
        HttpIO.writeLine(socket.getOutputStream(), method + " " + requestTarget + " HTTP/1.1");
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setFormBody(HttpQuery query) {
        this.body = query.toString();
        httpHeaders.put("Content-type", "application/x-www-form-urlencoded");
    }
}
