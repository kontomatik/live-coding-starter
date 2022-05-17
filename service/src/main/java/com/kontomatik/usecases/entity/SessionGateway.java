package com.kontomatik.usecases.entity;

import java.util.UUID;

public interface SessionGateway {

  void save(OwnerSession session);

  OwnerSession getById(UUID sessionId);

}
