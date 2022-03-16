package com.company.schedule.management.system.dao.impl;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Transactional
public class BaseIntegrationTest {

    public static final DriverManagerDataSource DATA_SOURCE = new DriverManagerDataSource();

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");

    @BeforeEach
    void setUp() {
        DATA_SOURCE.setUrl(container.getJdbcUrl());
        DATA_SOURCE.setUsername(container.getUsername());
        DATA_SOURCE.setPassword(container.getPassword());

        container.start();
    }
}