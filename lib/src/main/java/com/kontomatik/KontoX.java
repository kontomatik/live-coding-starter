package com.kontomatik;

import java.util.List;
import java.util.Map;

public class KontoX {

  public Session createSession(Target target) {
    return new Session(target);
  }

  public Map<Target, List<String>> catalog() {
    return Map.of(
      Target.KontoBank, List.of("EnterCredentialsCommand", "DefaultImportCommand"),
      Target.KontoBank_es, List.of("EnterCredentialsCommand", "DefaultImportCommand"),
      Target.EmptyTarget, List.of()
    );
  }

}
