package com.kontomatik.helpers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    PostgreSQLContainer<?> container = new PostgreSQLContainer("postgres:11.1");
    container.start();
    TestPropertyValues.of(Map.of(
      "spring.datasource.url", container.getJdbcUrl(),
      "spring.datasource.username", container.getUsername(),
      "spring.datasource.password", container.getPassword()
    )).applyTo(applicationContext);
  }

}
