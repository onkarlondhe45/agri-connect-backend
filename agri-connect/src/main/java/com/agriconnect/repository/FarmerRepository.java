package com.agriconnect.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agriconnect.model.Farmer;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, UUID>{
	
	 Optional<Farmer> findByEmail(String email);

}
