package no.kristiania.pgr200.database;

import no.kristiania.pgr200.database.http.HttpEchoServer;

import java.io.IOException;

public class InnleveringMain {
    public static void main(String[] args) throws IOException {
        HttpEchoServer server = new HttpEchoServer(0);
        server.runServerThread();
        System.out.println(server.getPort());
    }
}