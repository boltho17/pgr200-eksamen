package no.kristiania.pgr200.database.http;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpRequest {
  protected String host;
  protected int port;
  protected String uri;
  protected OutputStream output;


  public HttpRequest() {}

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
/*
    output = socket.getOutputStream();
    output.write(("GETT " + getUri() + " HTTP/1.1\r\n").getBytes());
    output.write(("Host: " + getHost() + "\r\n").getBytes());
    output.write("Connection: close\r\n".getBytes());
    output.write("User-Agent: Hackerman\r\n".getBytes());
    output.write("\r\n".getBytes());

    //output.flush();*/
  }

  public String getHost() {
    return host;
  }

  public String getUri() {
    return uri;
  }
}
