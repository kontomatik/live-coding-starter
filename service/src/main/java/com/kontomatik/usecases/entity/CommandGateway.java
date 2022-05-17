package com.kontomatik.usecases.entity;

import java.util.UUID;

public interface CommandGateway {

  void save(OwnerCommand command);

  OwnerCommand get(UUID id);

}
