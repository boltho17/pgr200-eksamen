package no.kristiania.pgr200.database.database;

import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ConferenceDataBaseProgramTest {

    @Test
    public void shouldCreateDataSource() {
        Properties prop = new Properties();
        try {
            try (FileReader reader = new FileReader("config.properties")) {
                prop.load(reader);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PGPoolingDataSource dataSource = new PGPoolingDataSource();

        dataSource.setUrl(prop.getProperty("database"));
        dataSource.setUser(prop.getProperty("dbuser"));
        dataSource.setPassword(prop.getProperty("dbpassword"));
    }
}
