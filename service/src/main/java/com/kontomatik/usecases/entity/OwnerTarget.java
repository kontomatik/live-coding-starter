package com.kontomatik.usecases.entity;

import java.util.List;

public record OwnerTarget(String name, List<OwnerDetails> ownerDetails, List<Account> accounts) {
}
