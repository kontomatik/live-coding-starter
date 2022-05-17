package com.kontomatik.usecases.entity;

public interface OwnerGateway {

  void save(Owner owner);

  Owner get(String ownerExternalId);

}
