package com.kontomatik;

import com.kontomatik.usecases.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebApi {

  private final CreateSessionUseCase createSessionUseCase;
  private final EnterCredentialsUseCase enterCredentialsUseCase;
  private final DefaultImportUseCase defaultImportUseCase;
  private final GetDataUseCase getDataUseCase;
  private final GetCommandUseCase getCommandUseCase;

  public WebApi(
    CreateSessionUseCase createSessionUseCase,
    EnterCredentialsUseCase enterCredentialsUseCase,
    DefaultImportUseCase defaultImportUseCase,
    GetDataUseCase getDataUseCase,
    GetCommandUseCase getCommandUseCase
  ) {
    this.createSessionUseCase = createSessionUseCase;
    this.enterCredentialsUseCase = enterCredentialsUseCase;
    this.defaultImportUseCase = defaultImportUseCase;
    this.getDataUseCase = getDataUseCase;
    this.getCommandUseCase = getCommandUseCase;
  }

  @PostMapping(value = "/session")
  public String createSession(
    @RequestParam String targetName,
    @RequestParam String ownerExternalId
  ) {
    return createSessionUseCase.run(targetName, ownerExternalId);
  }

  @PostMapping(value = "/session/{sessionId}/enterCredentials")
  public void enterCredentials(
    @PathVariable String sessionId,
    @RequestParam String... credentials
  ) {
    enterCredentialsUseCase.run(sessionId, credentials);
  }

  @PostMapping(value = "/session/{sessionId}/defaultImport")
  public String defaultImport(
    @PathVariable String sessionId
  ) {
    return defaultImportUseCase.run(sessionId);
  }

  @GetMapping(value = "/commands/{commandId}")
  public String getCommandStatus(
    @PathVariable String commandId
  ) {
    return getCommandUseCase.run(commandId);
  }

  @GetMapping(value = "/owners/{ownerExternalId}", produces = MediaType.APPLICATION_XML_VALUE)
  public GetDataUseCase.Output.Owner getData(@PathVariable String ownerExternalId) {
    return getDataUseCase.run(ownerExternalId);
  }

}
