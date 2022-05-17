package com.kontomatik.usecases;

import com.kontomatik.usecases.entity.*;
import com.kontomatik.usecases.ports.AsyncRunner;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultImportUseCase {

  private final SessionGateway sessionGateway;
  private final OwnerGateway ownerGateway;
  private final CommandGateway commandGateway;
  private final AsyncRunner asyncRunner;

  public DefaultImportUseCase(
    SessionGateway sessionGateway,
    OwnerGateway ownerGateway,
    CommandGateway commandGateway,
    AsyncRunner asyncRunner
  ) {
    this.sessionGateway = sessionGateway;
    this.ownerGateway = ownerGateway;
    this.commandGateway = commandGateway;
    this.asyncRunner = asyncRunner;
  }

  public String run(String sessionId) {
    OwnerCommand command = OwnerCommand.createNew();
    commandGateway.save(command);
    asyncRunner.run(() -> {
      OwnerSession ownerSession = sessionGateway.getById(UUID.fromString(sessionId));
      com.kontomatik.models.Owner result = ownerSession.session.executeDefaultImportCommand();
      ownerGateway.save(Owner.fromSingleImport(ownerSession.targetName, ownerSession.ownerExternalId, result));
      commandGateway.save(command.successful());
    });
    return command.id().toString();
  }

}
