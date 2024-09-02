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
import com.agriconnect.model.Expert;
import com.agriconnect.service.ExpertService;
import com.agriconnect.utils.JwtUtil;

@RestController
@RequestMapping("/api/experts")
public class ExpertController {

	@Autowired
	ExpertService expertService;
	
	@Autowired
	JwtUtil jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	@PostMapping("/create")
	public ResponseEntity<String> registerExpert(@RequestBody Expert expert){
		Expert registerExpert = expertService.registerExpert(expert);
		if(registerExpert!=null) {
			return new ResponseEntity<String>("Expert account has been registered successfully.",HttpStatus.CREATED);
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
 	public ResponseEntity<List<Expert>> getAllExpert(){
		List<Expert> findAllExpert = expertService.findAllExpert();
		if(findAllExpert!=null) {
			return new ResponseEntity<List<Expert>>(findAllExpert, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Expert>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Expert> getUserById(@PathVariable UUID id){
		Expert findById = expertService.findById(id);
		if(findById!=null) {
			return new ResponseEntity<Expert>(findById, HttpStatus.OK);
		}else {
			return new ResponseEntity<Expert>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/update/{id}")
   	public ResponseEntity<Expert> updateExpert(@RequestBody Expert expert, @PathVariable UUID id){
		Expert updateExpert = expertService.updateExpert(expert, id);
		if(updateExpert!=null) {
			return new ResponseEntity<Expert>(updateExpert, HttpStatus.OK);
		}else {
			return new ResponseEntity<Expert>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable UUID id){
		expertService.deleteExpert(id);
		return new ResponseEntity<String>("user deleted successfully...!", HttpStatus.OK);
	}
}
