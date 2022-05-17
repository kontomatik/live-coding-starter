package com.kontomatik.models;

public record Credentials(
  String login,
  String password,
  String oneTimeCode
) {
}
