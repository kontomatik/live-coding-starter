package com.kontomatik.usecases;

import com.kontomatik.KontoX;
import com.kontomatik.usecases.entity.OwnerSession;
import com.kontomatik.usecases.entity.SessionGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateSessionUseCase {

  private final KontoX kontoX;
  private final SessionGateway sessionGateway;

  public CreateSessionUseCase(
    KontoX kontoX,
    SessionGateway sessionGateway
  ) {
    this.kontoX = kontoX;
    this.sessionGateway = sessionGateway;
  }

  public String run(String targetName, String ownerExternalId) {
    OwnerSession ownerSession = new OwnerSession(targetName, ownerExternalId, kontoX);
    sessionGateway.save(ownerSession);
    return ownerSession.id.toString();
  }

}
