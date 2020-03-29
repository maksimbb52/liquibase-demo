package com.example.liquibasetest.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    @LiquibaseDataSource
    public DataSource dataSource(@Value("${hikari.property.path}")
                                 String propertyPath,
                                 LiquibaseProperties properties) {
        properties.setChangeLog("classpath:db/changelog/master/db.changelog-master.xml");
        return new HikariDataSource(
                new HikariConfig(propertyPath)
        );
    }
}
