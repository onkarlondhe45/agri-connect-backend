package com.agriconnect.service;

import java.util.List;
import java.util.UUID;

import com.agriconnect.model.Farmer;

public interface FarmerService {

	Farmer registerFarmer(Farmer farmer);

	List<Farmer> findAllFarmer();

	Farmer findById(UUID id);

	Farmer updateFarmer(Farmer farmer, UUID id);

	void deleteFarmer(UUID id);

}
