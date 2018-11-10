package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.net.Socket;

public class HttpPostRequest extends HttpRequest {

    public HttpPostRequest(String host, int port, String uri) {
        super(host, port, uri);
    }


    public void writeOutput(Socket socket) throws IOException {
        output = socket.getOutputStream();
        output.write(("POST " + getUri() + " HTTP/1.1\r\n").getBytes());
        output.write(("Host: " + getHost() + "\r\n").getBytes());
        output.write("Connection: close\r\n".getBytes());
        output.write("User-Agent: Hackerman\r\n".getBytes());
        output.write("\r\n".getBytes());

        //output.flush();
    }


}
