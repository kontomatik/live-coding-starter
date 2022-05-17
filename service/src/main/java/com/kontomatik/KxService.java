package com.kontomatik;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KxService {

  public static void main(String[] args) {
    SpringApplication.run(KxService.class, args);
  }

  @Bean
  KontoX kontoX() {
    return new KontoX();
  }

}
