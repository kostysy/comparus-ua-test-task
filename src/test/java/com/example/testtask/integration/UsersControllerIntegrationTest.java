package com.example.testtask.integration;

import com.example.testtask.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UsersControllerIntegrationTest {
    private static final String LIQUIBASE_NUMBER_OF_USERS = "liquibase.number.of.users";
    private static final String NUMBER_OF_DATABASE_CONNECTIONS = "bd.number.of.database.connections";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    @ServiceConnection
    static OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe:21-slim")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Test
    void shouldReturnUsersFromBothDatabases() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/api/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<User> users = response.getBody();
        Assertions.assertNotNull(users);
        assertThat(users.size()).isGreaterThanOrEqualTo(
                Integer.parseInt(Objects.requireNonNull(environment.getProperty(LIQUIBASE_NUMBER_OF_USERS)))
                * Integer.parseInt(Objects.requireNonNull(environment.getProperty(NUMBER_OF_DATABASE_CONNECTIONS))));
        assertThat(users.get(0).getUsername()).isNotEmpty();
    }
}
