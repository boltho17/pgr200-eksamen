package no.kristiania.pgr200.database.http;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerTest {

    private static HttpEchoServer server;

    @BeforeClass
    public static void createServer() throws IOException {
        server = new HttpEchoServer(0);
    }

    @Test
    public void shouldHandleRequest() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=200");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldEchoStatusCode() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=404");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getStatusText()).isEqualTo("Not Found");
    }

    @Test
    public void shouldEchoResponseHeaders() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
    }

    @Test
    public void shouldEchoResponseBody() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo?body=Hello+Kristiania!");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
    }

    @Test
    public void shouldPostData() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo");
        request.setMethod("POST");
        request.setFormBody(new HttpQuery().put("body", "hello world").put("status", "202"));
        HttpResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("hello world");
    }

    @Test
    public void shouldReturn500ForPostWithoutData() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo");
        request.setMethod("POST");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(500);
    }

    @Test
    public void shouldDeleteData() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/api/talks/1?status=200");
        request.setMethod("DELETE");
        HttpResponse response = request.execute();
        HttpPath path = new HttpPath("/api/talks/1?status=200");
        assertThat(path.getPath()).isEqualTo("/api/talks/1");
        //assertThat(path.getPathParts()).containsExactly("api", "talks");
        assertThat(path.getParameter("status")).hasValue("200");
    }

    @Test
    public void test() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(), "/api/talks/1?status=200");
        HttpResponse response = request.execute();
        HttpPath path = new HttpPath("/api/talks/2?status=200&body=title=test%26description=hello%26topic=java");
        assertThat(path.getPath()).isEqualTo("/api/talks/2");
        //assertThat(path.getPathParts()).containsExactly("api", "talks");
        assertThat(path.getParameter("status")).hasValue("200");
        assertThat(path.getParameter("body")).hasValue("title=test&description=hello&topic=java");

    }
}

