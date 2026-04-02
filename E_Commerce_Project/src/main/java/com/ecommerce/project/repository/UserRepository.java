package com.ecommerce.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	   Optional<User> findByEmail(String email);
	   
	   
}
