package com.kontomatik.persistence;

import com.kontomatik.usecases.entity.CommandGateway;
import com.kontomatik.usecases.entity.OwnerCommand;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostgresCommandGateway implements CommandGateway {

  private final CommandJpaRepository commandJpaRepository;

  public PostgresCommandGateway(CommandJpaRepository commandJpaRepository) {
    this.commandJpaRepository = commandJpaRepository;
  }

  @Override
  public void save(OwnerCommand command) {
    commandJpaRepository.save(CommandModel.from(command));
  }

  @Override
  public OwnerCommand get(UUID id) {
    return commandJpaRepository.getById(id.toString())
      .toDomain();
  }

}
