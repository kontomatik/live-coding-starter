package com.kontomatik.usecases;

import com.kontomatik.usecases.entity.CommandGateway;
import com.kontomatik.usecases.entity.OwnerCommand;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCommandUseCase {

  private final CommandGateway commandGateway;

  public GetCommandUseCase(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  public String run(String commandId) {
    OwnerCommand command = commandGateway.get(UUID.fromString(commandId));
    return command.state();
  }

}
