package com.kontomatik.gateways;

import com.kontomatik.helpers.PostgresInitializer;
import com.kontomatik.usecases.entity.Owner;
import com.kontomatik.usecases.entity.OwnerGateway;
import com.kontomatik.usecases.entity.OwnerTarget;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(initializers = PostgresInitializer.class)
public class OwnerGatewayTest {

  @Autowired
  private OwnerGateway ownerGateway;

  @Test
  void savesAndLoads() {
    Owner expected = new Owner("id", List.of(
      new OwnerTarget("name", List.of(),List.of())
    ));
    ownerGateway.save(expected);
    Owner actual = ownerGateway.get("id");
    assertThat(actual.id).isEqualTo(expected.id);
    assertThat(actual.targets).containsExactlyElementsOf(expected.targets);
  }

}
