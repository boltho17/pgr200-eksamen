package no.kristiania.pgr200.database;

import no.kristiania.pgr200.database.http.HttpServer;

import java.io.IOException;

public class InnleveringMain {
    public static void main(String[] args) {
        HttpServer server = new HttpServer(0);
        try {
            server.startServer();
            System.out.println(server.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

    /*HttpReq req = new HttpReq("localhost", server.getPort(), "/echo?body=Hello+Kristiania!");
    try {
      req.execute();
      //req1.execute();
    } catch (IOException e) {
      e.printStackTrace();
    } */
    }
}