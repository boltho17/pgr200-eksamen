package no.kristiania.pgr200.database.database;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ConferenceDatabaseProgram {

    private DataSource dataSource;
    private ConferenceTalkDao dao;
    private ConferenceTalk talk;


    public ConferenceDatabaseProgram() throws SQLException {
        this.dataSource = createDataSource();
        this.dao = new ConferenceTalkDao(dataSource);
    }

    public DataSource createDataSource() {

        //Fikk ikke til å bruke Properties og lese fra fil:
        Properties prop = new Properties();
        try {
            FileReader reader = new FileReader("config.properties");
            prop.load(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PGPoolingDataSource dataSource = new PGPoolingDataSource();

        dataSource.setUrl(prop.getProperty("database"));
        dataSource.setUser(prop.getProperty("dbuser"));
        dataSource.setPassword(prop.getProperty("dbpassword"));
        /*
        dataSource.setUrl("jdbc:postgresql://localhost/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("root");
        */


        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        //flyway.clean();
        //flyway.baseline();
        flyway.migrate();

        return dataSource;
    }


    public static void main(String[] args) throws SQLException {
        new ConferenceDatabaseProgram().run(args);
    }

    private void run(String[] args) throws SQLException {
        String command = "";
        if(args.length > 0) {
        command = args[0];
        }

        if(command.toLowerCase().equals("insert") && args.length >= 3) {
            String title = args[1] ;
            args[1] = title.substring(0, 1).toUpperCase() + title.substring(1);
            talk = new ConferenceTalk(args[1], args[2]);
            insertTalk();
            System.out.println("Success! The talk " + args[1] + " has been added.");
        }

        else if(command.toLowerCase().equals("insert") && args.length < 3) {
            System.err.println("Title and description required!");
        }

        else if (command.toLowerCase().equals("list") && args.length > 0) {
            dao.listAll();
            System.out.println("All talks listed!!");
        }

        else {
            System.out.println("Please type command: \"Insert\" or \"List\"");
            System.exit(1);
        }
    }

    private void insertTalk() throws SQLException {
        dao.insertTalk(talk);
        dao.listAll();
    }

}
