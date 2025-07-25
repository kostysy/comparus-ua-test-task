package com.example.testtask.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.testtask.repository.postgres",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDataSourceConfig {

    private final Environment environment;

    public PostgresDataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "postgresDataSource")
    public DataSource getDataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName(environment.getProperty("spring.datasource.postgres.driver-class-name"));
        builder.url(environment.getProperty("spring.datasource.postgres.url"));
        builder.username(environment.getProperty("spring.datasource.postgres.username"));
        builder.password(environment.getProperty("spring.datasource.postgres.password"));
        return builder.build();
    }

    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("postgresDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(jpaProperties())
                .packages("com.example.testtask.entity")
                .persistenceUnit("postgres")
                .build();
    }

    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        return properties;
    }
}
