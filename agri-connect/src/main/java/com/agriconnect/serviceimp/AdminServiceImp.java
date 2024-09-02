package com.agriconnect.serviceimp;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agriconnect.exceptions.EmailAlreadyExistsException;
import com.agriconnect.exceptions.ResourceNotFoundException;
import com.agriconnect.model.Admin;
import com.agriconnect.repository.AdminRepository;
import com.agriconnect.service.AdminService;
import com.agriconnect.utils.EmailUtil;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Admin registerAdmin(Admin admin) {
		try {
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			return adminRepository.save(admin);
		} catch (Exception e) {
			throw new EmailAlreadyExistsException(admin.getEmail());
		}
	}

	public List<Admin> findAllAdmin() {
		return adminRepository.findAll();
	}

	public Admin findById(UUID id) {
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
		return admin;
	}

	public Admin updateAdmin(Admin admin, UUID id) {
		Admin updatedAdmin = adminRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));

		updatedAdmin.setName(admin.getName());
		updatedAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
		updatedAdmin.setEmail(admin.getEmail());
		updatedAdmin.setRole(admin.getRole());
		return adminRepository.save(updatedAdmin);
	}

	public void deleteAdmin(UUID id) {
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
		adminRepository.delete(admin);

	}

}
