package no.kristiania.pgr200.database.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConferenceTalkDao {

    private DataSource dataSource;

    public ConferenceTalkDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Legger til ny talk hvis ikke en talk med samme tittel allerede eksisterer.
    public void insertTalk(ConferenceTalk talk) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select count(*) from CONFERENCE_TALK where title = ?")) {
                ps.setString(1, talk.getTitle());

                ResultSet rs = ps.executeQuery();
                int n;
                if (rs.next()) {
                    n = rs.getInt(1);

                    if (n < 1) {
                        String sql = "insert into CONFERENCE_TALK (TITLE, DESCRIPTION) values (?, ?)";
                        try (PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                            statement.setString(1, talk.getTitle());
                            statement.setString(2, talk.getDescription());

                            statement.executeUpdate();
                            System.out.println("Success! The talk " + talk.getTitle() + " has been added.");
                        }
                    } else {
                        System.out.println("Failure! The talk " + talk.getTitle() + " already exists.");
                    }
                }
            }
        }
    }

    //Lister opp alle talks. Gir beskjed hvis det ikke finnes noen talks å liste.
    public List<ConferenceTalk> listAll() throws SQLException {
        List<ConferenceTalk> talks = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select count(*) from CONFERENCE_TALK")) {

                ResultSet rs = ps.executeQuery();
                int n;
                if (rs.next()) {
                    n = rs.getInt(1);

                    if (n > 0) {
                        String sql = "select * from conference_talk";
                        try (PreparedStatement statement = conn.prepareStatement(sql)) {
                            try (ResultSet rs2 = statement.executeQuery()) {
                                while (rs2.next()) {
                                    ConferenceTalk talk = new ConferenceTalk();
                                    talk.setId(rs2.getInt("id"));
                                    talk.setTitle(rs2.getString("title"));
                                    talk.setDescription(rs2.getString("description"));
                                    talks.add(talk);
                                    System.out.println(talk.getId() + "." +
                                            "\n" + "Title: " + talk.getTitle() +
                                            "\n" + "Descrption: " + talk.getDescription());
                                    System.out.println();
                                }
                                System.out.println("All talks listed");
                            }
                        }
                    } else {
                        System.out.println("No talks currently registered in the database.");
                    }
                }
            }
        }
        return talks;
    }

    //Sletter en talk basert på tittel. Gir beskjed hvis en talk med den gitte tittelen ikke eksisterer.
    public void deleteTalk(String title) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select count(*) from CONFERENCE_TALK where title = ?")) {
                ps.setString(1, title);

                ResultSet rs = ps.executeQuery();
                int n;
                if (rs.next()) {
                    n = rs.getInt(1);
                    System.out.println(n);

                    if (n > 0) {
                        String sql = "delete from CONFERENCE_TALK where title = ?";
                        try (PreparedStatement statement = conn.prepareStatement(sql)) {
                            statement.setString(1, title);

                            statement.executeUpdate();
                            System.out.println("Job done! The talk " + title + " has been deleted.");
                        }
                    } else System.out.println("No talk with the title " + title + " exists.");
                }
            }
        }
    }

    //Sletter alle data i tabellen conference talks og resetter det unike løpenummeret.
    public void deleteAll() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "truncate CONFERENCE_TALK restart identity";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.executeUpdate();
                System.out.println("All talks deleted, identifier reset.");
            }
        }
    }
}
