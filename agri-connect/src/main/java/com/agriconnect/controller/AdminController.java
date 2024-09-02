package com.agriconnect.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriconnect.dtos.AuthRequest;
import com.agriconnect.model.Admin;
import com.agriconnect.service.AdminService;
import com.agriconnect.utils.JwtUtil;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	JwtUtil jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/create")
	public ResponseEntity<String> registerUser(@RequestBody Admin admin){
		Admin registerAdmin = adminService.registerAdmin(admin);
		if(registerAdmin!=null) {
			return new ResponseEntity<String>("Admin account has been registered successfully.",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<String> loginAdmin(@RequestParam String email, @RequestParam String password){
//		System.out.println(email);
//
//		Admin validateAdmin = adminService.validateAdmin(email, password);
//		if(validateAdmin!=null) {
//			return new ResponseEntity<String>("Admin logged in successfully...!",HttpStatus.OK);
//		}else {
//			return new ResponseEntity<String>("wrong credentials...!",HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		System.out.println(authentication);
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getEmail());
		}else {
			throw new UsernameNotFoundException("invalid user request!");
		}
		
	}
	
	@GetMapping("/getAll")
 	public ResponseEntity<List<Admin>> getAllAdmin(){
		List<Admin> findAllAdmin = adminService.findAllAdmin();
		if(findAllAdmin!=null) {
			return new ResponseEntity<List<Admin>>(findAllAdmin, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Admin>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Admin> getUserById(@PathVariable UUID id){
		Admin findById = adminService.findById(id);
		if(findById!=null) {
			return new ResponseEntity<Admin>(findById, HttpStatus.OK);
		}else {
			return new ResponseEntity<Admin>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/update/{id}")
   	public ResponseEntity<Admin> updateUser(@RequestBody Admin admin, @PathVariable UUID id){
		Admin updateAdmin = adminService.updateAdmin(admin, id);
		if(updateAdmin!=null) {
			return new ResponseEntity<Admin>(updateAdmin, HttpStatus.OK);
		}else {
			return new ResponseEntity<Admin>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable UUID id){
		adminService.deleteAdmin(id);
		return new ResponseEntity<String>("Admin deleted successfully...!", HttpStatus.OK);
	}
}
