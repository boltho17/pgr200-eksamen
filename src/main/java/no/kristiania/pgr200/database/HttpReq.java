package no.kristiania.pgr200.database;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpReq {
  private String host;
  private int port;
  private String uri;
  private OutputStream output;

  public HttpReq(String host, int port, String uri) {
    this.host = host;
    this.port = port;
    this.uri = uri;
  }

  public HttpRes execute() throws IOException {
    try(Socket socket = new Socket(host, port)){
      writeOutput(socket);
      return new HttpRes(socket);
    }
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void writeOutput(Socket socket) throws IOException {

    output = socket.getOutputStream();
    output.write(("GET " + getUri() + " HTTP/1.1\r\n").getBytes());
    output.write(("Host: " + getHost() + "\r\n").getBytes());
    output.write("Connection: close\r\n".getBytes());
    output.write("User-Agent: Hackerman101\r\n".getBytes());
    output.write("\r\n".getBytes());

    //output.flush();
  }

  private String getHost() {
    return host;
  }

  public String getUri() {
    return uri;
  }
}
