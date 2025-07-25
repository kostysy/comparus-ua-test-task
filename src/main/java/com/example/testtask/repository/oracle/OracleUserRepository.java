package com.example.testtask.repository.oracle;

import com.example.testtask.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "oracleUserRepository")
public interface OracleUserRepository extends UserRepository {
}
