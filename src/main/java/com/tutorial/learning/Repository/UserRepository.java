package com.tutorial.learning.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.learning.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
} 
