package com.agriconnect.service;

import java.util.List;
import java.util.UUID;

import com.agriconnect.model.User;

public interface UserService {

	User registerUser(User user);

	List<User> findAllUser();

	User findById(UUID id);

	User updateUser(User user, UUID id);

	void deleteUser(UUID id);

}
