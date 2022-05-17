package com.kontomatik;

import com.kontomatik.exceptions.KontoXBug;
import com.kontomatik.exceptions.errors.InvalidCredentials;
import com.kontomatik.exceptions.errors.NotSignedIn;
import com.kontomatik.models.Account;
import com.kontomatik.models.Credentials;
import com.kontomatik.models.Owner;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class KontoXTest {

  private final KontoX kontoX = new KontoX();

  @Test
  void listsAvailableTargetsAndCommands() {
    assertThat(kontoX.catalog()).containsAllEntriesOf(
      Map.of(
        Target.KontoBank, List.of("EnterCredentialsCommand", "DefaultImportCommand"),
        Target.KontoBank_es, List.of("EnterCredentialsCommand", "DefaultImportCommand"),
        Target.EmptyTarget, List.of()
      )
    );
  }

  @Test
  void executingCommandMissingFromCatalogFails() {
    assertThatThrownBy(() -> kontoX.createSession(Target.KontoBank).executeEnterCredentialsCommand(null))
      .isExactlyInstanceOf(KontoXBug.class);
  }

  @Test
  void kontoBankThrowsInvalidCredentials() {
    var invalid = new Credentials("test1", "invalid", "invalid");
    assertThatThrownBy(() -> kontoX.createSession(Target.KontoBank).executeEnterCredentialsCommand(invalid))
      .isExactlyInstanceOf(InvalidCredentials.class);
  }

  @Test
  void kontoBankThrowsNotSignedInWhenEnteringCredentialsSkipped() {
    assertThatThrownBy(() -> kontoX.createSession(Target.KontoBank).executeDefaultImportCommand())
      .isExactlyInstanceOf(NotSignedIn.class);
  }

  @Test
  void signsIntoKontoBankAndReturnsSampleData() {
    var valid = new Credentials("test1", "Test123", "Test123");
    Owner actual = kontoX.createSession(Target.KontoBank)
      .executeEnterCredentialsCommand(valid)
      .executeDefaultImportCommand();
    assertThat(actual).isEqualTo(
      new Owner(
        "Maria Sochacka",
        "Stolarska 15/2 33-543 Jaworzno",
        List.of(
          new Account(
            "Konto 0",
            "PL69137013140000049678278112",
            "259402.51",
            "PLN"
          )
        )
      )
    );
  }

  @Test
  void kontoBankEsThrowsInvalidCredentials() {
    var invalid = new Credentials("invalid", "invalid", "invalid");
    assertThatThrownBy(() -> kontoX.createSession(Target.KontoBank_es).executeEnterCredentialsCommand(invalid))
      .isExactlyInstanceOf(InvalidCredentials.class);
  }

  @Test
  void kontoBankEsThrowsNotSignedInWhenEnteringCredentialsSkipped() {
    assertThatThrownBy(() -> kontoX.createSession(Target.KontoBank_es).executeDefaultImportCommand())
      .isExactlyInstanceOf(NotSignedIn.class);
  }

  @Test
  void signsIntoKontoBankEsAndReturnsSampleData() {
    var valid = new Credentials("test2", "Test123", null);
    Owner actual = kontoX.createSession(Target.KontoBank_es)
      .executeEnterCredentialsCommand(valid)
      .executeDefaultImportCommand();
    assertThat(actual).isEqualTo(
      new Owner(
        "Juan Manuel Hurtado",
        "",
        List.of(
          new Account(
            "Mi cuenta",
            "ES1901300000028318503650",
            "471397.82",
            "EUR"
          )
        )
      )
    );
  }

}


