package no.kristiania.pgr200.database.http;

import no.kristiania.pgr200.database.database.ConferenceDatabaseProgram;

import java.sql.SQLException;

public class Controller {

    String method, path, query, body;
    ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();

    public Controller(String method, String path, String query) {
        this.method = method;
        this.path = path;
        this.query = query;
    }

    public void trigger() throws SQLException {
        switch (method) {
            case "POST":
                cdp.main(new String[]{"insert", getBodyParts()[0], getBodyParts()[1]});
                break;

            case "GET":
                cdp.main(new String[]{"list", path});
                break;

            case "DELETE":
                cdp.main(new String[]{"delete", path});
                break;

            default: break;
        }
    }

    public String[] getBodyParts() {
        String[] parts = new String[10];
        if (query != null && query.contains("&")) {
            int ampPos = query.indexOf("&");
            body = query.substring(0, ampPos);

            int equalPos = body.indexOf("=");
            String bodyContent = body.substring(equalPos + 1);

            int addPos = bodyContent.indexOf("+");
            parts[0] = bodyContent.substring(0, addPos);
            parts[1] = bodyContent.substring(addPos + 1);
        }
        else {body = "";}
        return parts;
    }
}
