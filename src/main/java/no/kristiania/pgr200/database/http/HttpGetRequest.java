package no.kristiania.pgr200.database.http;

import no.kristiania.pgr200.database.commandline.AddTalkCommand;
import no.kristiania.pgr200.database.commandline.ConferenceClientCommand;

import java.io.IOException;

public class HttpGetRequest extends HttpRequest {

    private String method = "GET";

    public HttpGetRequest(String hostname, int port, String requestTarget) throws IOException {
        super(hostname, port, requestTarget);
    }


}
