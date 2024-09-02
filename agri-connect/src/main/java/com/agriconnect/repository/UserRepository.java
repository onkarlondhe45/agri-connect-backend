package com.agriconnect.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agriconnect.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	Optional<User> findByEmail(String email);
}
