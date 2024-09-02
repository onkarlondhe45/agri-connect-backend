package com.agriconnect.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.agriconnect.model.Admin;

public class AdminInfo implements UserDetails {

	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public AdminInfo(Admin admin) {
		email = admin.getEmail();
		password = admin.getPassword();
		authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + admin.getRole().name()));
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
