package com.agriconnect.service;

import java.util.List;
import java.util.UUID;

import com.agriconnect.model.Admin;

public interface AdminService {

	Admin registerAdmin(Admin admin);

//	Admin validateAdmin(String email, String password);

	List<Admin> findAllAdmin();

	Admin findById(UUID id);

	Admin updateAdmin(Admin admin, UUID id);

	void deleteAdmin(UUID id);

}
