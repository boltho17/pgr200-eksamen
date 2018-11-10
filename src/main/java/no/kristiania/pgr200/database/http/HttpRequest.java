package no.kristiania.pgr200.database.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpRequest {
    private String host;
    private int port;
    private String uri;
    protected OutputStream output;

    public HttpRequest(String host, int port, String uri) {
        this.host = host;
        this.port = port;
        this.uri = uri;
    }

    public HttpResponse execute() throws IOException {
        try(Socket socket = new Socket(host, port)){
            writeOutput(socket);
            return new HttpResponse(socket);
        }
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void writeOutput(Socket socket) throws IOException {


        //output.flush();
    }

    protected String getHost() {
        return host;
    }

    public String getUri() {
        return uri;
    }
}