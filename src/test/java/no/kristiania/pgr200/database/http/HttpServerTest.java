package no.kristiania.pgr200.database.http;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerTest {

  private static HttpServer server;

  @BeforeClass
  public static void startServer() throws IOException {
    server = new HttpServer(0);
    server.startServer();
  }

  @Test
  public void shouldHandleRequest() throws IOException {
    HttpRequest request = new HttpGetRequest("localhost", server.getPort(), "/echo?status=200");
    HttpResponse response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(200);
  }

  @Test
  public void shouldEchoStatusCode() throws IOException {
    HttpRequest request = new HttpGetRequest("localhost", server.getPort(), "/echo?status=404");
    HttpResponse response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(404);
  }

  @Test
  public void shouldEchoResponseHeaders() throws IOException {
    HttpRequest request = new HttpGetRequest("localhost", server.getPort(),
            "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no");
    HttpResponse response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(307);
    assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
  }

  @Test
  public void shouldEchoResponseBody() throws IOException {
    HttpRequest request = new HttpGetRequest("localhost", server.getPort(),
            "/echo?body=Hello+Kristiania!");
    HttpResponse response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
  }

    @Test
    public void test() throws IOException {
        HttpRequest request = new HttpGetRequest("localhost", server.getPort(), "/api/talks?status=200");
        HttpResponse response = request.execute();
        HttpPath path = new HttpPath("/api/talks?status=200&body=vi%20plukker%20bl%C3%A5b%C3%A6r");
        assertThat(path.getPath()).isEqualTo("/api/talks");
        assertThat(path.getPathParts()).containsExactly("api", "talks");
        assertThat(path.getQuery().getParameter("status")).isEqualTo("200");
        assertThat(path.getQuery().getParameter("body")).isEqualTo("vi plukker blåbær");
        assertThat(path.getQuery().toString()).isEqualTo("status=200body=vi plukker blåbær");
    }

    @Test
    public void test2() throws IOException {
        HttpRequest request = new HttpGetRequest("localhost", server.getPort(), "/api/talks?status=200");
        HttpResponse response = request.execute();
        HttpPath path = new HttpPath("/api/talks?status=200&body=title=test%26description=hello");
        assertThat(path.getPath()).isEqualTo("/api/talks");
        assertThat(path.getPathParts()).containsExactly("api", "talks");
        assertThat(path.getQuery().getParameter("status")).isEqualTo("200");
        assertThat(path.getQuery().getParameter("body")).isEqualTo("title=test&description=hello");
        assertThat(path.getQuery().toString()).isEqualTo("status=200body=title=test&description=hello");
    }
}

