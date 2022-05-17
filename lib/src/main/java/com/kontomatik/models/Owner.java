package com.kontomatik.models;

import java.util.List;

public record Owner(
  String name,
  String address,
  List<Account> accounts
) {
}
