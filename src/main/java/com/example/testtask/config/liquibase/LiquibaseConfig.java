package com.example.testtask.config.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {
    public static final String LIQUIBASE_ORACLE_CHANGELOG_FILE = "liquibase.oracle.changelog.file";
    public static final String LIQUIBASE_POSTGRES_CHANGELOG_FILE = "liquibase.postgres.changelog.file";

    private final DataSource oracleDataSource;
    private final DataSource postgresDataSource;
    private final Environment environment;

    public LiquibaseConfig(@Qualifier("oracleDataSource") DataSource oracleDataSource,
                           @Qualifier("postgresDataSource") DataSource postgresDataSource, Environment environment) {
        this.oracleDataSource = oracleDataSource;
        this.postgresDataSource = postgresDataSource;
        this.environment = environment;
    }

    @Bean(name = "oracleSpringLiquibase")
    public SpringLiquibase oracleLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(oracleDataSource);
        liquibase.setChangeLog(environment.getProperty(LIQUIBASE_ORACLE_CHANGELOG_FILE));
        liquibase.setShouldRun(Boolean.FALSE);
        return liquibase;
    }

    @Bean(name = "postgresSpringLiquibase")
    public SpringLiquibase postgresLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(postgresDataSource);
        liquibase.setChangeLog(environment.getProperty(LIQUIBASE_POSTGRES_CHANGELOG_FILE));
        liquibase.setShouldRun(Boolean.FALSE);
        return liquibase;
    }
}
