package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.net.Socket;

public class HttpGetRequest extends HttpRequest {

    public HttpGetRequest(String host, int port, String url) {
        super(host, port, url);
    }


    public void writeOutput(Socket socket) throws IOException {
        output = socket.getOutputStream();
        output.write(("GET " + getUri() + " HTTP/1.1\r\n").getBytes());
        output.write(("Host: " + getHost() + "\r\n").getBytes());
        output.write("Connection: close\r\n".getBytes());
        output.write("User-Agent: Hackerman\r\n".getBytes());
        output.write("\r\n".getBytes());

        //output.flush();
    }


}
