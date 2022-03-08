package com.company.schedule.managment.system.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseIntegrationTest {

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");

    public static final DriverManagerDataSource DATA_SOURCE = new DriverManagerDataSource();

    @BeforeEach
    void setUp() {
        DATA_SOURCE.setUrl(container.getJdbcUrl());
        DATA_SOURCE.setUsername(container.getUsername());
        DATA_SOURCE.setPassword(container.getPassword());
        container.start();
    }
}
