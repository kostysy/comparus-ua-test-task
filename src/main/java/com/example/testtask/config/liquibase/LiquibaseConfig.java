package com.example.testtask.config.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {
    private final DataSource oracleDataSource;
    private final DataSource postgresDataSource;

    public LiquibaseConfig(@Qualifier("oracleDataSource") DataSource oracleDataSource,
                           @Qualifier("postgresDataSource") DataSource postgresDataSource) {
        this.oracleDataSource = oracleDataSource;
        this.postgresDataSource = postgresDataSource;
    }

    @Bean(name = "oracleSpringLiquibase")
    public SpringLiquibase oracleLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(oracleDataSource);
        liquibase.setChangeLog("classpath:db/oracle-init-changelog.xml");
        liquibase.setShouldRun(Boolean.FALSE);
        return liquibase;
    }

    @Bean(name = "postgresSpringLiquibase")
    public SpringLiquibase postgresLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(postgresDataSource);
        liquibase.setChangeLog("classpath:db/postgres-init-changelog.xml");
        liquibase.setShouldRun(Boolean.FALSE);
        return liquibase;
    }
}
