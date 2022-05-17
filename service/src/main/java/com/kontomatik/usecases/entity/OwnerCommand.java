package com.kontomatik.usecases.entity;

import java.util.UUID;

public record OwnerCommand(UUID id, String state) {

  public static OwnerCommand createNew() {
    return new OwnerCommand(UUID.randomUUID(), "IN_PROGRESS");
  }

  public OwnerCommand successful() {
    return new OwnerCommand(id, "SUCCESSFUL");
  }

}
