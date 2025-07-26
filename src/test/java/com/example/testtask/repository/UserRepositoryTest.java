package com.example.testtask.repository;

import com.example.testtask.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(excludeAutoConfiguration = {LiquibaseAutoConfiguration.class})
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void shouldFindUserByUsername() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setName("John");
        user.setSurname("Doe");
        userRepository.save(user);

        // when
        List<User> result = userRepository.findAll();

        // then
        assertEquals(1, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
    }
}