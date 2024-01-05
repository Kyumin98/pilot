package com.example.pilot.domain.login.account.dao;

import com.example.pilot.domain.login.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
}
