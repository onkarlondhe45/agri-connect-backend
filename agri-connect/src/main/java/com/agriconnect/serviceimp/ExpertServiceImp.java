package com.agriconnect.serviceimp;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agriconnect.exceptions.EmailAlreadyExistsException;
import com.agriconnect.exceptions.ResourceNotFoundException;
import com.agriconnect.model.Expert;
import com.agriconnect.repository.ExpertRepository;
import com.agriconnect.service.ExpertService;
import com.agriconnect.utils.EmailUtil;

@Service
public class ExpertServiceImp implements ExpertService {

	@Autowired
	ExpertRepository expertRepository;

	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Expert registerExpert(Expert expert) {
		try {
			expert.setPassword(passwordEncoder.encode(expert.getPassword()));
			Expert savedExpert = expertRepository.save(expert);
			
			String subject = "Welcome to AgriConnect!";
			String body = "<html>" + 
		              "<body>" + 
		              "<h1>Hello " + savedExpert.getName() + ",</h1>" + 
		              "<p>Welcome to AgriConnect! Your account has been successfully set up.</p>" + 
		              "<p>We are excited to have you with us. Feel free to explore and engage with our platform.</p>" + 
		              "<p>Warm regards,<br>The AgriConnect Team</p>" + 
		              "</body>" + 
		              "</html>";

			emailUtil.sendMail(subject, body, savedExpert.getEmail(), true);
			return savedExpert;

		} catch (Exception e) {
			throw new EmailAlreadyExistsException(expert.getEmail());
		}
	}

	public List<Expert> findAllExpert() {
		return expertRepository.findAll();
	}

	public Expert findById(UUID id) {
		Expert expert = expertRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expert", "id", id));
		return expert;
	}

	public Expert updateExpert(Expert expert, UUID id) {
		Expert updatedExpert = expertRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expert", "id", id));

		updatedExpert.setName(expert.getName());
		updatedExpert.setPassword(passwordEncoder.encode(expert.getPassword()));
		updatedExpert.setEmail(expert.getEmail());
		updatedExpert.setRole(expert.getRole());
		updatedExpert.setExpertiseArea(expert.getExpertiseArea());
		updatedExpert.setQualification(expert.getQualification());
		return expertRepository.save(updatedExpert);
	}

	public void deleteExpert(UUID id) {
		Expert expert = expertRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expert", "id", id));
		expertRepository.delete(expert);

	}

}
