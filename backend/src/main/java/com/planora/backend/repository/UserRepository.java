package com.planora.backend.repository;

import java.util.Optional;
import com.planora.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email); //Reason for using optional:There may or may not be a User result

}