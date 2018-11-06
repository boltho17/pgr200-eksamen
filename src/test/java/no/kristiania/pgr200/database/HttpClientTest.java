package no.kristiania.pgr200.database;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpClientTest {

  @Test
  public void shouldExecuteRequest() throws Exception {
    HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo");
    HttpResponse response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(200);
  }


  @Test
  public void shouldReadResponseCode() throws Exception {
    HttpRequest request = new HttpRequest("urlecho.appspot.com", 80,
            "/echo?status=404");
    HttpResponse response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(404);
  }

  @Test
  public void shouldReadResponseHeaders() throws IOException {
    HttpRequest request = new HttpRequest("urlecho.appspot.com", 80,
            "/echo?status=307&Location=http://www.google.com");

    HttpResponse response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(307);
    assertThat(response.getHeader("Location")).isEqualTo("http://www.google.com");
  }

  @Test
  public void shouldReadBody() throws IOException {
    HttpRequest request = new HttpRequest("urlecho.appspot.com", 80,
            "/echo?body=Hello+World");
    HttpResponse response = request.execute();

    assertThat(response.getBody())
            .isEqualTo("Hello World");
  }

}
