package no.kristiania.pgr200.database.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpIO {

    static String readLine(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != -1) {
            if (c == '\r') {
                c = inputStream.read();
                break;
            }
            result.append((char)c);
        }
        System.out.println(result);
        return result.toString();
    }

    public static String readBody(InputStream inputStream, int contentLength) throws IOException {
        if (contentLength < 0) {
            return null;
        }
        StringBuilder result = new StringBuilder();

        int c;
        int remains = contentLength;
        while ((remains-- > 0) && (c = inputStream.read()) != -1) {
            result.append((char)c);
        }
        return result.toString();
    }

    public static void writeLine(OutputStream outputStream, String line) throws IOException {
        outputStream.write((line + "\r\n").getBytes());
    }

}
