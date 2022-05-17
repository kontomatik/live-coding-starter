package com.kontomatik;

import com.kontomatik.exceptions.KontoXBug;
import com.kontomatik.exceptions.errors.InvalidCredentials;
import com.kontomatik.exceptions.errors.NotSignedIn;
import com.kontomatik.models.Account;
import com.kontomatik.models.Credentials;
import com.kontomatik.models.Owner;

import java.util.List;
import java.util.function.Supplier;

public class Session {

  private final Target target;
  private boolean isSignedIn;

  public Session(Target target) {
    this.target = target;
  }

  public Session executeEnterCredentialsCommand(Credentials credentials) {
    handleExceptions(() -> {
        if (target.equals(Target.KontoBank))
          if (!credentials.equals(new Credentials("test1", "Test123", "Test123")))
            throw new InvalidCredentials();
        if (target.equals(Target.KontoBank_es))
          if (!credentials.equals(new Credentials("test2", "Test123", null)))
            throw new InvalidCredentials();
        return null;
      }
    );
    isSignedIn = true;
    return this;
  }

  public Owner executeDefaultImportCommand() {
    if (!isSignedIn)
      throw new NotSignedIn();
    return handleExceptions(
      () -> {
        if (target.equals(Target.KontoBank))
          return new Owner(
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
          );
        if (target.equals(Target.KontoBank_es))
          return new Owner(
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
          );
        return null;
      }
    );
  }

  private <R> R handleExceptions(Supplier<R> command) {
    try {
      return command.get();
    } catch (InvalidCredentials | NotSignedIn e) {
      throw e;
    } catch (Exception e) {
      throw new KontoXBug(e);
    }
  }

}
