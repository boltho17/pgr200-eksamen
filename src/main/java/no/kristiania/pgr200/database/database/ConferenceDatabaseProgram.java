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


    public ConferenceDatabaseProgram() {
        this.dataSource = createDataSource();
        this.dao = new ConferenceTalkDao(dataSource);
    }

    public DataSource createDataSource() {

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

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        //flyway.clean();

        return dataSource;
    }


    public static void main(String[] args) throws SQLException {
        new ConferenceDatabaseProgram().run(args);
    }

    private void run(String[] args) throws SQLException {
        String command = "";
        String title, path, description, topic;

        if (args.length > 0) {
            command = args[0];
        }

        if (command.toLowerCase().equals("insert")) {
            if (args.length >= 3) {
                if (args[1] != null && args[2] != null) {
                    title = args[1];
                    description = args[2];
                    if(args.length == 4) {topic = args[3];} else{topic = "no-topic";}
                    title = title.substring(0, 1).toUpperCase() + title.substring(1);
                    talk = new ConferenceTalk(title, description, topic);
                    dao.insertTalk(talk);
                } else {
                    title = "no-title";
                    description = "no-description";
                    System.out.println("Title and description required!\n");
                }

            } else {
                System.out.println("Title and description required!");
            }
        } else if (command.toLowerCase().equals("list")) {
            // GET /api/talks/1
            if(args.length > 1) { path = args[1]; } else {path = "";dao.listAll();}
            if (path.matches(".*/.*/.*")) ;
            {
                if (path.contains("/api/talks/")) {
                    int slashPos = path.indexOf("/", path.indexOf("/") + 2);
                    int pathNum = Integer.parseInt(path.substring(slashPos).substring(slashPos + 3));
                    dao.show(pathNum);
                }
                if (path.equals("/api/talks")) {
                    dao.listAll();
                    //System.out.println("All talks listed!!");
                }
            }
        } else if (command.toLowerCase().equals("delete")) {
            if (args.length >= 2) {
                title = args[1];
                dao.deleteTalk(title);
            } else {
                System.out.println("Please specify which Talk you want to delete. (Delete + Title)");
            }
        } else

        {
            System.out.println("Please type command: \"Insert\", \"Delete\" or \"List\"");
            System.exit(1);
        }
    }
}
