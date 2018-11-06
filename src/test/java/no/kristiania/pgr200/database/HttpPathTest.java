package no.kristiania.pgr200.database;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpPathTest {

    @Test
    public void shouldSeparatePathAndQuery200() {
        HttpPath path = new HttpPath("/urlecho?status=200");
        assertThat(path.getPath()).isEqualTo("/urlecho");
        assertThat(path.getQuery().getParameter("body")).isEqualTo("OK");
        assertThat(path.getQuery().toString()).isEqualTo("status=200body=OK");
    }

    @Test
    public void shouldSeparatePathAndQuery404() {
        HttpPath path = new HttpPath("/urlecho?status=404");
        assertThat(path.getPath()).isEqualTo("/urlecho");
        assertThat(path.getQuery().getParameter("body")).isEqualTo("Not Found");
        assertThat(path.getQuery().toString()).isEqualTo("status=404body=Not Found");
    }

    @Test
    public void shouldReturnQueryNullWhenNoQuery() {
        HttpPath path = new HttpPath("/myfile");
        assertThat(path.getPath()).isEqualTo("/myfile");
        assertThat(path.getQuery()).isNull();
    }

    @Test
    public void shouldParseUrl() {
        HttpPath path = new HttpPath("/myapp/echo?status=402&body=vi%20plukker%20bl%C3%A5b%C3%A6r");
        assertThat(path.getPath()).isEqualTo("/myapp/echo");
        assertThat(path.getPathParts()).containsExactly("myapp", "echo");
        assertThat(path.getQuery().getParameter("status")).isEqualTo("402");
        assertThat(path.getQuery().getParameter("body")).isEqualTo("vi plukker blåbær");
        assertThat(path.getQuery().toString()).isEqualTo("status=402body=vi plukker blåbær");
    }

}
