package no.kristiania.pgr200.database.http;

import no.kristiania.pgr200.database.database.ConferenceDatabaseProgram;
import no.kristiania.pgr200.database.database.ConferenceTalk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpServer {

  private int port;
  private HashMap<String, String> headers = new HashMap<>();
  ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();


  public HttpServer(int port){
    this.port = port;
  }

  public void startServer() throws IOException {
    ServerSocket server = new ServerSocket(0);
    setPort(server.getLocalPort());
    new Thread(() -> {
        try {
            serverThread(server);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }).start();
  }

  private void serverThread(ServerSocket server) throws SQLException {
    while(true){
      try {
        Socket clientSocket = server.accept();

        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();

        String line = readNextLine(input);

        headers.put("RequestLine", line);
        while(!line.isEmpty()){
          System.out.println(line);
          if(line.contains(":")) {
            int colonPos = line.indexOf(":");
            String headerName = line.substring(0, colonPos);
            String headerValue = line.substring(colonPos + 1).trim();
            headers.put(headerName, headerValue);
          }
          line = readNextLine(input);
        }

        System.out.println(" ");


        String[] path = getHeaderParameter("RequestLine").split(" ");
        String httpMethod = path[0];
        HttpPath httpPath = new HttpPath(path[1]);
        httpPath.getQuery();

        switch(httpMethod) {
            case "POST":
                cdp.main(new String[]{"insert"});

            case "GET":
                cdp.main(new String[]{"list"});

            case "DELETE":
                cdp.main(new String[]{"delete"});
        }


        //Response from server
        output.write(("HTTP/1.1 " + httpPath.getParameter("status") + " " + httpPath.getStatusMessage(httpPath.getParameter("status")) + "\r\n").getBytes());
        output.write("X-Special-Server: Vegard og Thomas little layer\r\n".getBytes());
        output.write("Connection: close\r\n".getBytes());
        if(httpPath.getParameter("Location") != null){
          output.write(("Location: " + httpPath.getParameter("Location") +  "\r\n").getBytes());

        }
        if(httpPath.getParameter("body") != null){
          output.write(("Content-Length: " + httpPath.getParameter("body").length() + "\r\n").getBytes());
        }
        output.write("Content-Type: text/html\r\n".getBytes());
        output.write("\r\n".getBytes());
        if(httpPath.getParameter("body") != null){
          output.write(httpPath.getParameter("body").getBytes());
        }
        output.write("\r\n".getBytes());

        output.flush();

        clientSocket.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String getHeaderParameter(String key){
    return headers.get(key);
  }

  //Reads input from the client.
  private String readNextLine(InputStream input) throws IOException {
    StringBuilder nextLine = new StringBuilder();
    int c;
    while((c = input.read()) != -1){
      if(c == '\r'){
        input.read();
        break;
      }
      nextLine.append((char) c);
    }
    return nextLine.toString();
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
