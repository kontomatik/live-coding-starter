package com.kontomatik.usecases.entity;

public record Account(
  String id,
  String name,
  String amount,
  String currency
) {}