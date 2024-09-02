package com.agriconnect.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agriconnect.model.Expert;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, UUID>{
	
	 Optional<Expert> findByEmail(String email);

}
