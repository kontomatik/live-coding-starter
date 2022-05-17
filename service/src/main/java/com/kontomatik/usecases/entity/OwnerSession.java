package com.kontomatik.usecases.entity;

import com.kontomatik.KontoX;
import com.kontomatik.Session;
import com.kontomatik.Target;

import java.util.UUID;

public class OwnerSession {

  public final UUID id;
  public final Session session;
  public final String ownerExternalId;
  public final String targetName;

  public OwnerSession(String targetName, String ownerExternalId, KontoX kontoX) {
    session = kontoX.createSession(Target.valueOf(targetName));
    this.id = UUID.randomUUID();
    this.ownerExternalId = ownerExternalId;
    this.targetName = targetName;
  }

}
