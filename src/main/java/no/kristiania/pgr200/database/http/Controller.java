package no.kristiania.pgr200.database.http;

import no.kristiania.pgr200.database.database.ConferenceDatabaseProgram;

import java.sql.SQLException;

public class Controller {

    ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();

    public void trigger(String httpMethod) throws SQLException {
        switch (httpMethod) {
            case "POST":
                cdp.main(new String[]{"insert"});

            case "GET":
                cdp.main(new String[]{"list"});

            case "DELETE":
                cdp.main(new String[]{"delete"});
        }
    }
}
