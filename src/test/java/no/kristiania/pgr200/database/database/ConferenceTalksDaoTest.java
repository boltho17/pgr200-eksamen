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
    public void shouldPopulateConferenceTalks() throws SQLException {
        ConferenceDatabaseProgram cdp = new ConferenceDatabaseProgram();
        ConferenceTalkDao dao = new ConferenceTalkDao(cdp.createDataSource());
        dao.deleteAll();
        ConferenceTalk talk1 = new ConferenceTalk("ABC", "123");
        ConferenceTalk talk2 = new ConferenceTalk("ABCD", "1234");
        ConferenceTalk talk3 = new ConferenceTalk("ABCDE", "12345");
        ConferenceTalk talk4 = new ConferenceTalk("ABCDEF", "123456");
        ConferenceTalk talk5 = new ConferenceTalk("ABCDEFG", "1234567");
        ConferenceTalk talk6 = new ConferenceTalk("ABCDEFGH", "12345678");
        ConferenceTalk talk7 = new ConferenceTalk("ABCDEFGHI", "123456789");
        ConferenceTalk talk8 = new ConferenceTalk("ABCDEFGHIJ", "1234567890");
        dao.insertTalk(talk1);
        dao.insertTalk(talk2);
        dao.insertTalk(talk3);
        dao.insertTalk(talk4);
        dao.insertTalk(talk5);
        dao.insertTalk(talk6);
        dao.insertTalk(talk7);
        dao.insertTalk(talk8);
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






