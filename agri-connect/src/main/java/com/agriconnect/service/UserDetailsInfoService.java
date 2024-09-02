package com.agriconnect.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.agriconnect.config.AdminInfo;
import com.agriconnect.config.ExpertInfo;
import com.agriconnect.config.FarmerInfo;
import com.agriconnect.config.UserInfo;
import com.agriconnect.model.Admin;
import com.agriconnect.model.Expert;
import com.agriconnect.model.Farmer;
import com.agriconnect.model.User;
import com.agriconnect.repository.AdminRepository;
import com.agriconnect.repository.ExpertRepository;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.repository.UserRepository;

@Component
public class UserDetailsInfoService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FarmerRepository farmerRepository;

	@Autowired
	ExpertRepository expertRepository;
	
	@Autowired
	AdminRepository adminRepository; 

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<User> userInfo = userRepository.findByEmail(email);
		if (userInfo.isPresent()) {
			return new UserInfo(userInfo.get());
		}

		Optional<Farmer> farmerInfo = farmerRepository.findByEmail(email);
		if (farmerInfo.isPresent()) {
			return new FarmerInfo(farmerInfo.get());
		}

		Optional<Expert> expertInfo = expertRepository.findByEmail(email);
		if (expertInfo.isPresent()) {
			return new ExpertInfo(expertInfo.get());
		}
		
		Optional<Admin> adminInfo = adminRepository.findByEmail(email);
		if (adminInfo.isPresent()) {
			return new AdminInfo(adminInfo.get());
		} 

		throw new UsernameNotFoundException("No account found with the username: " + email);
	}
}
