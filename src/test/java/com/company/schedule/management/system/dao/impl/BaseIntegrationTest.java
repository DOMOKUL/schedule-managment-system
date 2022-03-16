package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.config.HibernateConfiguration;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Properties;

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