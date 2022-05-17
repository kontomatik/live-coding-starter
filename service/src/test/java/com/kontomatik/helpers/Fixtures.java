package com.kontomatik.helpers;

import com.kontomatik.Target;
import com.kontomatik.usecases.GetDataUseCase;

import java.util.List;

public class Fixtures {

  public static class KontoBank_es {

    public static final String TARGET = Target.KontoBank_es.name();
    public static final String[] CREDENTIALS = {"test2", "Test123"};
    public static final GetDataUseCase.Output.OwnerTarget OWNER = new GetDataUseCase.Output.OwnerTarget(
      TARGET,
      List.of(
        new GetDataUseCase.Output.OwnerDetails("Juan Manuel Hurtado", "")
      ),
      List.of(
        new GetDataUseCase.Output.Account(
          "ES1901300000028318503650",
          "Mi cuenta",
          "471397.82",
          "EUR"
        )
      )
    );
  }

  public static class KontoBank {

    public static final String TARGET = Target.KontoBank.name();
    public static final String[] CREDENTIALS = {"test1", "Test123", "Test123"};
    public static final GetDataUseCase.Output.OwnerTarget OWNER = new GetDataUseCase.Output.OwnerTarget(
      TARGET,
      List.of(
        new GetDataUseCase.Output.OwnerDetails("Maria Sochacka", "Stolarska 15/2 33-543 Jaworzno")
      ),
      List.of(
        new GetDataUseCase.Output.Account(
          "PL69137013140000049678278112",
          "Konto 0",
          "259402.51",
          "PLN"
        )
      )
    );

  }

}
