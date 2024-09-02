package com.agriconnect.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.agriconnect.model.Farmer;
import com.agriconnect.model.User;

public class FarmerInfo implements UserDetails {

	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public FarmerInfo(Farmer farmer) {
		email = farmer.getEmail();
		password = farmer.getPassword();
		authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + farmer.getRole().name()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

}
