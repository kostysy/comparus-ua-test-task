package com.example.testtask.repository.oracle;

import com.example.testtask.entity.User;
import com.example.testtask.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "oracleUserRepository")
public interface OracleUserRepository extends UserRepository {
}
