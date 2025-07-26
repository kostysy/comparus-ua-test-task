package com.example.testtask.repository.postgres;

import com.example.testtask.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "postgresUserRepository")
public interface PostgresUserRepository extends UserRepository {
}
