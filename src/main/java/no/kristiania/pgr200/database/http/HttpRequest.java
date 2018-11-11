package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Optional;

public class HttpRequest {

    private String hostname;
    private int port;
    private String requestTarget;
    private String method = "GET";
    private HttpHeaders httpHeaders;
    private String body;
    Controller controller = new Controller();

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

            try {
                controller.trigger(this.method, getBodyParts());
            } catch (SQLException e) {
                e.printStackTrace();
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

    public String[] getBodyParts() {
        String[] parts = new String[10];
        if (body != null) {

            int ampPos = body.indexOf("&");
            body = body.substring(0, ampPos);

            int equalPos = body.indexOf("=");
            String bodyContent = body.substring(equalPos + 1);

            int addPos = bodyContent.indexOf("+");
            parts[0] = bodyContent.substring(0, addPos);
            parts[1] = bodyContent.substring(addPos + 1);
        }
        else {body = "";}
        return parts;
    }
}
