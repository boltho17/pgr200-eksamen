package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpEchoServer {

    private ServerSocket serverSocket;
    Controller controller = new Controller();

    public HttpEchoServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        new Thread(this::runServerThread).start();
    }

    public void runServerThread() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            } catch (RuntimeException e) {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException ioEx) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void handleRequest(Socket clientSocket) throws IOException {
        String statusCode;
        String body;

        HttpQuery query;
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            String requestLine = HttpIO.readLine(clientSocket.getInputStream());
            String requestTarget = requestLine.split(" ")[1];
            HttpPath path = new HttpPath(requestTarget);

            HttpHeaders headers = new HttpHeaders();
            headers.readHeaders(clientSocket.getInputStream());

            if (requestLine.split(" ")[0].equals("POST")) {
                query = new HttpQuery(HttpIO.readBody(clientSocket.getInputStream(), headers.getContentLength()));
            } else {
                query = path.query();
            }

            statusCode = query.get("status").orElse("200");
            body = query.get("body").orElse("None");
        } catch (RuntimeException e) {
            e.printStackTrace();
            writeResponseLine(clientSocket, "500");
            responseHeaders.writeTo(clientSocket.getOutputStream());
            return;
        }

        writeResponseLine(clientSocket, statusCode);
        responseHeaders
                .put("Content-Type", "text/plain; charset=utf-8")
                .setContentLength(body.length());
        query.get("Location").ifPresent(value -> {
            responseHeaders.put("Location", value);
        });

        responseHeaders.writeTo(clientSocket.getOutputStream());

        clientSocket.getOutputStream().write(body.getBytes());
    }

    public void writeResponseLine(Socket clientSocket, String statusCode) throws IOException {
        String statusMessage = getStatusMessage(statusCode);
        HttpIO.writeLine(clientSocket.getOutputStream(), "HTTP/1.1 " + statusCode + " " + statusMessage);
    }


    private static Map<String, String> statusMessages = new HashMap<>();
    static {
        statusMessages.put("100", "Continue");
        statusMessages.put("101", "Switching Protocols");
        statusMessages.put("200", "OK");
        statusMessages.put("201", "Created");
        statusMessages.put("202", "Accepted");
        statusMessages.put("203", "Non - Authoritative Information");
        statusMessages.put("204", "No Content");
        statusMessages.put("205", "Reset Content");
        statusMessages.put("206", "Partial Content");
        statusMessages.put("300", "Multiple Choices");
        statusMessages.put("301", "Moved Permanently");
        statusMessages.put("302", "Found");
        statusMessages.put("303", "See Other");
        statusMessages.put("304", "Not Modified");
        statusMessages.put("305", "Use Proxy");
        statusMessages.put("307", "Temporary Redirect");
        statusMessages.put("400", "Bad Request");
        statusMessages.put("401", "Unauthorized");
        statusMessages.put("402", "Payment Required");
        statusMessages.put("403", "Forbidden");
        statusMessages.put("404", "Not Found");
        statusMessages.put("405", "Method Not Allowed");
        statusMessages.put("406", "Not Acceptable");
        statusMessages.put("407", "Proxy Authentication Required");
        statusMessages.put("408", "Request Timeout");
        statusMessages.put("409", "Conflict");
        statusMessages.put("410", "Gone");
        statusMessages.put("411", "Length Required");
        statusMessages.put("412", "Precondition Failed");
        statusMessages.put("413", "Payload Too Large");
        statusMessages.put("414", "URI Too Long");
        statusMessages.put("415", "Unsupported Media Type");
        statusMessages.put("416", "Range Not Satisfiable");
        statusMessages.put("417", "Expectation Failed");
        statusMessages.put("426", "Upgrade Required");
        statusMessages.put("500", "Internal Server Error");
        statusMessages.put("501", "Not Implemented");
        statusMessages.put("502", "Bad Gateway");
        statusMessages.put("503", "Service Unavailable");
        statusMessages.put("504", "Gateway Timeout");
        statusMessages.put("505", "HTTP Version Not Supported");
    }


    private String getStatusMessage(String statusCode) {
        return statusMessages.get(statusCode);
    }

    public static void main(String[] args) throws IOException {
        new HttpEchoServer(10080);
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

}
