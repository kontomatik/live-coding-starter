package com.kontomatik.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontomatik.usecases.entity.OwnerTarget;
import com.kontomatik.util.Exceptions;

import javax.persistence.*;

@Entity(name = "ownerTarget")
@Table(name = "targets")
public class OwnerTargetModel {

  @Id
  @GeneratedValue
  Long id;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  OwnerModel owner;

  @Column
  String target;

  public static OwnerTargetModel from(OwnerTarget ownerTarget, OwnerModel owner) {
    var created = new OwnerTargetModel();
    created.target = Exceptions.uncheck(() -> new ObjectMapper().writeValueAsString(ownerTarget));
    created.owner = owner;
    return created;
  }

  public OwnerTarget toDomain() {
    return Exceptions.uncheck(() ->
      new ObjectMapper().readValue(target, OwnerTarget.class)
    );
  }

}
