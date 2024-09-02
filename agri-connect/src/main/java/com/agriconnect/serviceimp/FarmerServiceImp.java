package com.agriconnect.serviceimp;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agriconnect.exceptions.EmailAlreadyExistsException;
import com.agriconnect.exceptions.ResourceNotFoundException;
import com.agriconnect.model.Farmer;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.service.FarmerService;
import com.agriconnect.utils.EmailUtil;

@Service
public class FarmerServiceImp implements FarmerService {

	@Autowired
	FarmerRepository farmerRepository;

	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Farmer registerFarmer(Farmer farmer) {
		try {
			farmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
			Farmer savedFarmer = farmerRepository.save(farmer);

			String subject = "Welcome to AgriConnect!";
			String body = "<html>" + 
		              "<body>" + 
		              "<h1>Hello " + savedFarmer.getName() + ",</h1>" + 
		              "<p>Welcome to AgriConnect! Your account has been successfully set up.</p>" + 
		              "<p>We are excited to have you with us. Feel free to explore and engage with our platform.</p>" + 
		              "<p>Warm regards,<br>The AgriConnect Team</p>" + 
		              "</body>" + 
		              "</html>";

			emailUtil.sendMail(subject, body, savedFarmer.getEmail(), true);

			return savedFarmer;
		} catch (Exception e) {
			throw new EmailAlreadyExistsException(farmer.getEmail());
		}
	}

	public List<Farmer> findAllFarmer() {
		return farmerRepository.findAll();
	}

	public Farmer findById(UUID id) {
		Farmer farmer = farmerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Farmer", "id", id));
		return farmer;
	}

	public Farmer updateFarmer(Farmer farmer, UUID id) {
		Farmer updatedFarmer = farmerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Farmer", "id", id));

		updatedFarmer.setName(farmer.getName());
		updatedFarmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
		updatedFarmer.setEmail(farmer.getEmail());
		updatedFarmer.setRole(farmer.getRole());
		return farmerRepository.save(updatedFarmer);
	}

	public void deleteFarmer(UUID id) {
		Farmer farmer = farmerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Farmer", "id", id));
		farmerRepository.delete(farmer);

	}
}
