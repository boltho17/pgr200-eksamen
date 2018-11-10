package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

public class HttpResponse {

    StringBuilder currentLine;
    HashMap<String, String> headers;
    String[] parts;
    String body;

    public HttpResponse(Socket socket){
        InputStream input;
        headers = new HashMap<>();

        try {
            input = socket.getInputStream();
            readNextLine(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        readResponse();
    }

    private void readResponse() {
        parts = currentLine.toString().split("\n");
        String key, value;

        for(int i = 0; i < parts.length; i++){
            int colonPos = parts[i].indexOf(":");
            if (parts[i].contains(":")) {
                key = parts[i].substring(0, colonPos);
                value = parts[i].substring(colonPos + 1).trim();
                headers.put(key, value);
            }

            if(parts[i].equals("\r") && parts.length > i+1){
                body = parts[i+1].trim();
            }
            System.out.println(parts[i]);
        }
    }

    private String readNextLine(InputStream input) throws IOException {
        currentLine = new StringBuilder();
        int c;
        while ((c = input.read()) != -1) {
            currentLine.append((char)c);
        }
        return currentLine.toString();
    }

    public int getStatusCode() {

        String[] statusCode = parts[0].split(" ");
        return Integer.parseInt(statusCode[1]);
    }

    public String getHeader(String key){
        return headers.get(key);
    }

    public String getBody() {
        return body;
    }
}