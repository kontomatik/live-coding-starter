package com.kontomatik.persistence;

import com.kontomatik.usecases.entity.OwnerSession;
import com.kontomatik.usecases.entity.SessionGateway;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemorySessionGateway implements SessionGateway {

  private final Map<UUID, OwnerSession> storage = new HashMap<>();

  @Override
  public void save(OwnerSession session) {
    storage.put(session.id, session);
  }

  @Override
  public OwnerSession getById(UUID sessionId) {
    return storage.get(sessionId);
  }

}
