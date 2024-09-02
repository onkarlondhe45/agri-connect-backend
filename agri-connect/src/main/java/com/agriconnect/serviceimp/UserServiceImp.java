package com.agriconnect.serviceimp;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agriconnect.exceptions.EmailAlreadyExistsException;
import com.agriconnect.exceptions.ResourceNotFoundException;
import com.agriconnect.model.User;
import com.agriconnect.repository.UserRepository;
import com.agriconnect.service.UserService;
import com.agriconnect.utils.EmailUtil;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser = userRepository.save(user);
			
			String subject = "Welcome to AgriConnect!";
			String body = "<html>" + 
		              "<body>" + 
		              "<h1>Hello " + savedUser.getName() + ",</h1>" + 
		              "<p>Welcome to AgriConnect! Your account has been successfully set up.</p>" + 
		              "<p>We are excited to have you with us. Feel free to explore and engage with our platform.</p>" + 
		              "<p>Warm regards,<br>The AgriConnect Team</p>" + 
		              "</body>" + 
		              "</html>";
			
			emailUtil.sendMail(subject, body, savedUser.getEmail(), true);

			return savedUser;
		} catch (Exception e) {
			throw new EmailAlreadyExistsException(user.getEmail());
		}
	}

	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	public User findById(UUID id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return user;
	}

	public User updateUser(User user, UUID id) {
		User updatedUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		updatedUser.setName(user.getName());
		updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
		updatedUser.setEmail(user.getEmail());
		updatedUser.setRole(user.getRole());
		return userRepository.save(updatedUser);
	}

	public void deleteUser(UUID id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		userRepository.delete(user);

	}

}
