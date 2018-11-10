package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpEchoServer {

    private static ServerSocket serverSocket;

    // Initiate Thread by Constructor

    public HttpEchoServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Thread thread = new Thread(() -> runServer());
        thread.start();
    }

    // Initiate Thread as an application

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(0);
        Thread thread = new Thread(() -> runServer());
        thread.start();
    }

    // Run server in thread

    public static void runServer() {
        while (true) {

            // Open socket and wait for connection

            try (Socket socket = serverSocket.accept()) {
                StringBuilder requestLine = new StringBuilder();

                // Read the first line into StringBuilder requestLine
                int c;
                while ((c = socket.getInputStream().read()) != -1) {
                    if (c == '\r') {
                        break;
                    }
                    requestLine.append((char) c);
                }

                // read first line and send to httpQuery

                HttpQuery query = new HttpPath(requestLine.toString().split(" ")[1]).getQuery();
                HttpPath path = new HttpPath(requestLine.toString().split(" ")[1]);


                // Get parameters from HttpQuery
                String statusCode, body, location, contentType;
                if (query != null) {
                    statusCode = query.getParameter("status");
                    body = query.getParameter("body");
                    location = query.getParameter("location");
                    contentType = query.getParameter("content-type");
                }

                //If there httpquery is null define hardcoded parameters
                else {
                    statusCode = "200";
                    body = "";
                    location = "http://google.com";
                    contentType = "text/html; charset=utf-8\r\n";
                }

                // Writes the response
                socket.getOutputStream().write(("HTTP/1.1 " + statusCode + " " + path.getStatusMessage(statusCode) + "\r\n").getBytes());
                socket.getOutputStream().write(("Content-Type: " + contentType + "\r\n").getBytes());
                socket.getOutputStream().write(("Location: " + location + "\r\n").getBytes());
                socket.getOutputStream().write("Server: Kristiania Java Server!!\r\n".getBytes());
                socket.getOutputStream().write(("Content-Length: " + body.length() + "\r\n").getBytes());
                socket.getOutputStream().write("\r\n".getBytes());
                socket.getOutputStream().write((body + "\r\n").getBytes());
                socket.getOutputStream().flush();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }


}
