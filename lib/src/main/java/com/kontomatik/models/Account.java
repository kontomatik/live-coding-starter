package com.kontomatik.models;

public record Account(
  String name,
  String iban,
  String balance,
  String currency
) {
}
