package no.kristiania.pgr200.database.http;

import no.kristiania.pgr200.database.database.ConferenceDatabaseProgram;

import java.sql.SQLException;

public class Controller {

    ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();

    public void trigger(String httpMethod, String[] body) throws SQLException {
        switch (httpMethod) {
            case "POST":
                cdp.main(new String[]{"insert", body[0], body[1] });
                break;

            case "GET":
                cdp.main(new String[]{"list"});
                break;

            case "DELETE":
                cdp.main(new String[]{"delete"});
                break;

            default: break;
        }
    }
}
