package com.example.testtask.config.liquibase;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DelayedLiquibaseRunner implements ApplicationListener<ApplicationReadyEvent> {
    private List<SpringLiquibase> springLiquibase;

    public DelayedLiquibaseRunner(List<SpringLiquibase> springLiquibase) {
        this.springLiquibase = springLiquibase;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (SpringLiquibase springLiquibase : springLiquibase) {
            springLiquibase.setShouldRun(Boolean.TRUE);
        }
        try {
            for (SpringLiquibase springLiquibase : springLiquibase) {
                springLiquibase.afterPropertiesSet();
            }
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}
