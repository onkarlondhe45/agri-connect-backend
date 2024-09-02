package com.agriconnect.service;

import java.util.List;
import java.util.UUID;

import com.agriconnect.model.Expert;

public interface ExpertService {

	Expert registerExpert(Expert expert);

	List<Expert> findAllExpert();

	Expert findById(UUID id);

	Expert updateExpert(Expert expert, UUID id);

	void deleteExpert(UUID id);

}
