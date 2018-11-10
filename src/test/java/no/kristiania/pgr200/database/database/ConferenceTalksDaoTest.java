package no.kristiania.pgr200.database.database;

import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConferenceTalksDaoTest {

    @Test
    public void shouldInsertConferenceTalks() throws SQLException {
        ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();
        ConferenceTalkDao dao = new ConferenceTalkDao(cdp.createDataSource());
        ConferenceTalk talk = new ConferenceTalk("Hello", "World");
        dao.insertTalk(talk);
        }

    @Test
    public void shouldListAllTalks() throws SQLException {
        ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();
        ConferenceTalkDao dao = new ConferenceTalkDao(cdp.createDataSource());
        ConferenceTalk talk = new ConferenceTalk("Greetings", "Mr. Bond");
        dao.insertTalk(talk);
        List<ConferenceTalk> talks = dao.listAll();
        assertThat(talks)
                .noneMatch(p -> p.getTitle().isEmpty())
                .extracting(p -> p.toString())
                .contains("Greetings Mr. Bond");
    }

    //Denne testen kjører alltid grønn. MÅ FIKSES!
    @Test
    public void shouldDeleteTalkWithTitle() throws SQLException {
        ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();
        ConferenceTalkDao dao = new ConferenceTalkDao(cdp.createDataSource());
        ConferenceTalk talk = new ConferenceTalk("Delete This", "Thank you!");
        dao.insertTalk(talk);
        dao.deleteTalk("Delete This");
        ConferenceTalk talk2 = new ConferenceTalk("After the deleted", "Wohoo!");
        dao.insertTalk(talk2);
        List<ConferenceTalk> talks = dao.listAll();
        assertThat(talks)
                .noneMatch(p -> p.getTitle().isEmpty())
                .extracting(p -> p.toString())
                .doesNotContain("Delete This");
    }

    @Test
    public void shouldDeleteAllTalks() throws SQLException {
        ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();
        ConferenceTalkDao dao = new ConferenceTalkDao(cdp.createDataSource());
        ConferenceTalk talk = new ConferenceTalk("Delete This", "Thank you!");
        dao.insertTalk(talk);
        dao.deleteAll();
        List<ConferenceTalk> talks = dao.listAll();
        assertThat(talks)
                .isEmpty();

    }



}






