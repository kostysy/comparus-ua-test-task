package com.example.testtask.integration;

import com.example.testtask.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UsersControllerIntegrationTest {
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
        // assuming liquibase adds 3 in each DB and we habe 2 databases
        assertThat(users.size()).isGreaterThanOrEqualTo(6);
        assertThat(users.get(0).getUsername()).isNotEmpty();
    }
}
