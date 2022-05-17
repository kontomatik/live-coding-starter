package com.kontomatik.usecases;

import com.kontomatik.usecases.entity.Owner;
import com.kontomatik.usecases.entity.OwnerGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDataUseCase {

  private final OwnerGateway ownerGateway;

  public GetDataUseCase(OwnerGateway ownerGateway) {this.ownerGateway = ownerGateway;}

  public Output.Owner run(String ownerExternalId) {
    Owner owner = ownerGateway.get(ownerExternalId);
    return mapToOutput(owner);
  }

  private static Output.Owner mapToOutput(Owner owner) {
    return new Output.Owner(
      owner.id,
      owner.targets.stream().map(t -> new Output.OwnerTarget(t.name(),
        t.ownerDetails().stream().map(od -> new Output.OwnerDetails(od.name(), od.address())).toList(),
        t.accounts().stream().map(a -> new Output.Account(a.id(), a.name(), a.amount(), a.currency())).toList()
      )).toList()
    );
  }

  public static final class Output {

    private Output() {}

    public record Owner(String id, List<OwnerTarget> ownerTargets) {}

    public record OwnerTarget(String id, List<OwnerDetails> ownerDetails, List<Account> accounts) {}

    public record Account(String id, String name, String amount, String currency) {}

    public record OwnerDetails(String name, String address) {}

  }

}
