package com.kontomatik;

import com.kontomatik.usecases.GetDataUseCase;
import com.kontomatik.helpers.Fixtures;
import com.kontomatik.helpers.KxServiceApi;
import com.kontomatik.helpers.PostgresInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgresInitializer.class)
class KxServiceTest {

  @LocalServerPort
  private int port;

  private KxServiceApi api;

  @BeforeEach
  void setUp() {
    api = new KxServiceApi("http://localhost:" + port);
  }

  @Test
  void importsKontoBankDataAndAccessesItViaGetData() {
    api.createSession("KontoBank", "owner-external-id")
      .enterCredentials(Fixtures.KontoBank.CREDENTIALS)
      .defaultImport();
    GetDataUseCase.Output.Owner actual = api.getData("owner-external-id");
    assertThat(actual).isEqualTo(
      new GetDataUseCase.Output.Owner("owner-external-id", List.of(Fixtures.KontoBank.OWNER))
    );
  }

  @Test
  void importsForSameOwnerFromMultipleTargetsAndReturnsMergedData() {
    api.createSession(Fixtures.KontoBank.TARGET, "same-owner")
      .enterCredentials(Fixtures.KontoBank.CREDENTIALS)
      .defaultImport();
    api.createSession(Fixtures.KontoBank_es.TARGET, "same-owner")
      .enterCredentials(Fixtures.KontoBank_es.CREDENTIALS)
      .defaultImport();
    GetDataUseCase.Output.Owner actual = api.getData("same-owner");
    assertThat(actual).isEqualTo(
      new GetDataUseCase.Output.Owner("same-owner", List.of(Fixtures.KontoBank.OWNER, Fixtures.KontoBank_es.OWNER))
    );
  }

  @Test
  void importsForDifferentOwnersAreSeparated() {
    api.createSession(Fixtures.KontoBank.TARGET, "first")
      .enterCredentials(Fixtures.KontoBank.CREDENTIALS)
      .defaultImport();
    api.createSession(Fixtures.KontoBank_es.TARGET, "second")
      .enterCredentials(Fixtures.KontoBank_es.CREDENTIALS)
      .defaultImport();
    GetDataUseCase.Output.Owner first = api.getData("first");
    GetDataUseCase.Output.Owner second = api.getData("second");
    assertThat(first).isNotEqualTo(second);
    assertThat(first.ownerTargets()).isNotEqualTo(second.ownerTargets());
  }

}