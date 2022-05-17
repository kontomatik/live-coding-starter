package com.kontomatik.helpers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.springframework.http.MediaType;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {

  private final String baseUrl;
  private final java.net.http.HttpClient theClient = java.net.http.HttpClient.newHttpClient();

  public HttpClient(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  HttpRequest createGet(String path) {
    return HttpRequest.newBuilder()
      .uri(URI.create(baseUrl + path))
      .build();
  }

  HttpRequest createPost(String path, String body) {
    return HttpRequest.newBuilder()
      .POST(HttpRequest.BodyPublishers.ofString(body))
      .uri(URI.create(baseUrl + path))
      .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
      .build();
  }

  public <T> T fetch(HttpRequest request, Class<T> responseType) {
    String body = fetch(request);
    return HttpClient.unchecked(() -> new XmlMapper().readValue(body, responseType));
  }

  String fetch(HttpRequest request) {
    HttpResponse<String> response = unchecked(() ->
      theClient.send(request, HttpResponse.BodyHandlers.ofString())
    );
    if (response.statusCode() >= 400)
      throw new RuntimeException(String.format(
        "Response status: %s with body:\n%s",
        response.statusCode(),
        response.body()
      ));
    return response.body();
  }

  static <T> T unchecked(ThrowingSupplier<T> action) {
    try {
      return action.get();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

}
