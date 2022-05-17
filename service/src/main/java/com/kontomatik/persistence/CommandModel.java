package com.kontomatik.persistence;

import com.kontomatik.usecases.entity.OwnerCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "commands")
public class CommandModel {

  @Id
  String id;

  @Column
  String state;

  public static CommandModel from(OwnerCommand command) {
    var created = new CommandModel();
    created.id = command.id().toString();
    created.state = command.state();
    return created;
  }

  public OwnerCommand toDomain() {
    return new OwnerCommand(UUID.fromString(id), state);
  }

}
