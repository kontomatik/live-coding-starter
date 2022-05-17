package com.kontomatik.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandJpaRepository extends JpaRepository<CommandModel, String> {}
