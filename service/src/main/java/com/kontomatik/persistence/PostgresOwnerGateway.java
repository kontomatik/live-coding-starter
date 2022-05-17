package com.kontomatik.persistence;

import com.kontomatik.usecases.entity.Owner;
import com.kontomatik.usecases.entity.OwnerGateway;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class PostgresOwnerGateway implements OwnerGateway {

  private final OwnerJpaRepository ownerJpaRepository;

  public PostgresOwnerGateway(OwnerJpaRepository ownerJpaRepository) {
    this.ownerJpaRepository = ownerJpaRepository;
  }

  @Override
  public void save(Owner owner) {
    ownerJpaRepository.save(OwnerModel.from(owner));
  }

  @Override
  public Owner get(String ownerExternalId) {
    return ownerJpaRepository.getById(ownerExternalId).toDomain();
  }

}
