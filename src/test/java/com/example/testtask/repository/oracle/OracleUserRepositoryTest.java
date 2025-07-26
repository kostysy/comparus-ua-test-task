package com.example.testtask.repository.oracle;

import com.example.testtask.entity.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OracleUserRepositoryTest {
    private static final String ENTITY_SCAN_PACKAGE = "com.example.testtask.entity";
    public static final String LIQUIBASE_ORACLE_CHANGELOG_FILE = "liquibase.oracle.test.changelog.file";

    @Container
    static OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe")
            .withUsername("test")
            .withPassword("test");

    static {
        oracle.start();
    }

    @Autowired
    private OracleUserRepository oracleUserRepository;

    @Test
    void testOracleUserSave() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setName("John");
        user.setSurname("Doe");
        oracleUserRepository.save(user);

        //when
        List<User> result = oracleUserRepository.findAll();

        //then
        assertEquals(1, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
    }

    @TestConfiguration
    static class OracleTestConfig {
        @Autowired
        private Environment environment;

        @Bean(value = "dataSource")
        public DataSource oracleDataSource() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(oracle.getJdbcUrl());
            config.setUsername(oracle.getUsername());
            config.setPassword(oracle.getPassword());
            return new HikariDataSource(config);
        }

        @Bean(value = "entityManagerFactory")
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource,
                                                                           EntityManagerFactoryBuilder builder) {
            return builder.dataSource(dataSource)
                    .properties(jpaProperties())
                    .packages(environment.getProperty(ENTITY_SCAN_PACKAGE))
                    .persistenceUnit("oracle")
                    .build();
        }

        @Bean
        public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }

        @Bean
        public SpringLiquibase oracleLiquibase(@Qualifier("dataSource") DataSource ds) {
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(ds);
            liquibase.setChangeLog(environment.getProperty(LIQUIBASE_ORACLE_CHANGELOG_FILE));
            return liquibase;
        }

        private Map<String, Object> jpaProperties() {
            Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
            properties.put("hibernate.hbm2ddl.auto", "none");
            return properties;
        }
    }
}