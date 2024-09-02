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
import com.agriconnect.model.Farmer;
import com.agriconnect.service.FarmerService;
import com.agriconnect.utils.JwtUtil;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

	@Autowired
	FarmerService farmerService;
	
	@Autowired
	JwtUtil jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	@PostMapping("/create")
	public ResponseEntity<String> registerFarmer(@RequestBody Farmer farmer){
		Farmer registerFarmer = farmerService.registerFarmer(farmer);
		if(registerFarmer!=null) {
			return new ResponseEntity<String>("Farmer account has been registered successfully.",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
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
 	public ResponseEntity<List<Farmer>> getAllFarmer(){
		List<Farmer> findAllFarmer = farmerService.findAllFarmer();
		if(findAllFarmer!=null) {
			return new ResponseEntity<List<Farmer>>(findAllFarmer, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Farmer>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Farmer> getFarmerById(@PathVariable UUID id){
		Farmer findById = farmerService.findById(id);
		if(findById!=null) {
			return new ResponseEntity<Farmer>(findById, HttpStatus.OK);
		}else {
			return new ResponseEntity<Farmer>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/update/{id}")
   	public ResponseEntity<Farmer> updateUser(@RequestBody Farmer farmer, @PathVariable UUID id){
		Farmer updateFarmer = farmerService.updateFarmer(farmer, id);
		if(updateFarmer!=null) {
			return new ResponseEntity<Farmer>(updateFarmer, HttpStatus.OK);
		}else {
			return new ResponseEntity<Farmer>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable UUID id){
		farmerService.deleteFarmer(id);
		return new ResponseEntity<String>("farmer deleted successfully...!", HttpStatus.OK);
	}
}
