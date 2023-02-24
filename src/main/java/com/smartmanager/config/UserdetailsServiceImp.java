package com.smartmanager.config;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.smartmanager.dao.Repository;
import com.smartmanager.entities.User;

@Service
public class UserdetailsServiceImp implements UserDetailsService {

	@Autowired
	private Repository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		UserdetailsImp userdetails;
		User user=repository.findByEmail(username);
		  if (user == null) {
	            throw new UsernameNotFoundException("User not found");
	        }
		
		 return new UserdetailsImp(user);	
	}

}
