package no.kristiania.pgr200.database.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpPathTest {

    @Test
    public void shouldSeparatePathAndQuery() {
        HttpPath path = new HttpPath("/echo?status=200");
        assertThat(path.getPath()).isEqualTo("/echo");
        assertThat(path.getQuery()).isEqualTo("status=200");
        assertThat(path.toString()).isEqualTo("/echo?status=200");
        assertThat(path.getParameter("status")).hasValue("200");
    }

    @Test
    public void shouldHandlePathWithoutQuery() {
        HttpPath path = new HttpPath("/myfile");
        assertThat(path.getPath()).isEqualTo("/myfile");
        assertThat(path.getQuery()).isNull();
        assertThat(path.toString()).isEqualTo("/myfile");
    }

    @Test
    public void shouldHandleMultipleParameters() {
        HttpPath path = new HttpPath("/echo?status=307&body=hello+bl%C3%A5b%C3%A6r");
        assertThat(path.getQuery()).isEqualTo("status=307&body=hello+bl%C3%A5b%C3%A6r");
        assertThat(path.getParameter("status")).hasValue("307");
        assertThat(path.getParameter("body")).hasValue("hello blåbær");
    }

    @Test
    public void shouldSetParameters() {
        HttpPath path = new HttpPath("/echo");
        path.setParameter("status", "200");
        assertThat(path.getQuery()).isEqualTo("status=200");
        assertThat(path.toString()).isEqualTo("/echo?status=200");
    }


}
