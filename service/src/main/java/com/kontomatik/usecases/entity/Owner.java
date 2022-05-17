package com.kontomatik.usecases.entity;

import java.util.List;

public class Owner {

  public final String id;
  public final List<OwnerTarget> targets;

  public static Owner fromSingleImport(String target, String ownerExternalId, com.kontomatik.models.Owner single) {
    return new Owner(
      ownerExternalId,
      List.of(
        new OwnerTarget(
          target,
          List.of(
            new OwnerDetails(single.name(), single.address())
          ),
          single.accounts()
            .stream()
            .map(a -> new Account(a.iban(), a.name(), a.balance(), a.currency()))
            .toList()
        )
      )
    );
  }

  public Owner(String id, List<OwnerTarget> targets) {
    this.id = id;
    this.targets = targets;
  }

}
