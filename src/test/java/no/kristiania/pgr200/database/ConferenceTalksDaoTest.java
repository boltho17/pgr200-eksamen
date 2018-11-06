package no.kristiania.pgr200.database;

import no.kristiania.pgr200.database.database.ConferenceDatabaseProgram;
import no.kristiania.pgr200.database.database.ConferenceTalk;
import no.kristiania.pgr200.database.database.ConferenceTalkDao;
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
        List<ConferenceTalk> talks = dao.listAll();
        assertThat(talks)
                .noneMatch(p -> p.getTitle().isEmpty())
                .extracting(p -> p.toString())
                .contains("Hello World");
    }



}






