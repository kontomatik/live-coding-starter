package com.kontomatik.helpers;

import com.kontomatik.usecases.GetDataUseCase;

import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class KxServiceApi {

  private final HttpClient httpClient;
  private String sessionId;

  public KxServiceApi(String baseUrl) {
    this.httpClient = new HttpClient(baseUrl);
  }

  public KxServiceApi createSession(String target, String ownerExternalId) {
    HttpRequest request = httpClient.createPost(
      "/session",
      String.join("&", "targetName=" + target, "ownerExternalId=" + ownerExternalId)
    );
    sessionId = httpClient.fetch(request);
    return this;
  }

  public KxServiceApi enterCredentials(String... credentials) {
    String body = Arrays.stream(credentials)
      .map(c -> "credentials=" + c)
      .collect(Collectors.joining("&"));
    HttpRequest request = httpClient.createPost("/session/" + sessionId + "/enterCredentials", body);
    httpClient.fetch(request);
    return this;
  }

  public KxServiceApi defaultImport() {
    HttpRequest request = httpClient.createPost("/session/" + sessionId + "/defaultImport", "");
    String commandId = httpClient.fetch(request);
    waitForCommandToFinish(commandId);
    return this;
  }

  private void waitForCommandToFinish(String commandId) {
    LocalDateTime start = LocalDateTime.now();
    while (Duration.between(start, LocalDateTime.now()).minus(Duration.ofMinutes(2)).isNegative()) {
      HttpRequest request = httpClient.createGet("/commands/" + commandId);
      String commandStatus = httpClient.fetch(request);
      if (commandStatus.equals("SUCCESSFUL"))
        return;
    }
    throw new RuntimeException("Timeout exceeded");
  }

  public GetDataUseCase.Output.Owner getData(String ownerExternalId) {
    HttpRequest request = httpClient.createGet("/owners/" + ownerExternalId);
    return httpClient.fetch(request, GetDataUseCase.Output.Owner.class);
  }

}
