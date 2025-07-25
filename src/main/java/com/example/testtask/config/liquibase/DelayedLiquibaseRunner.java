package com.example.testtask.config.liquibase;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DelayedLiquibaseRunner implements ApplicationListener<ApplicationReadyEvent> {
    private final DataSource oracleDataSource;
    private final DataSource postgresDataSource;

    private ApplicationContext applicationContext;

    public DelayedLiquibaseRunner(@Qualifier("oracleDataSource") DataSource oracleDataSource,
                                  @Qualifier("postgresDataSource") DataSource postgresDataSource) {
        this.oracleDataSource = oracleDataSource;
        this.postgresDataSource = postgresDataSource;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        SpringLiquibase oracleSpringLiquibase = (SpringLiquibase) applicationContext.getBean("oracleSpringLiquibase");
        SpringLiquibase postgresSpringLiquibase = (SpringLiquibase) applicationContext.getBean("postgresSpringLiquibase");
        oracleSpringLiquibase.setShouldRun(true);
        postgresSpringLiquibase.setShouldRun(true);
        try {
            oracleSpringLiquibase.afterPropertiesSet();
            postgresSpringLiquibase.afterPropertiesSet();
        } catch (LiquibaseException ignore) {
        }
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


}
