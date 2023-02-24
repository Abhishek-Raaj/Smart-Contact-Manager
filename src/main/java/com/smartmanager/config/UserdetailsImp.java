package com.smartmanager.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartmanager.entities.User;

public class UserdetailsImp  implements UserDetails{

	
	private User user;
	public UserdetailsImp(User user) {
		super();
		this.user = user;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		// TODO Auto-generated method stub
		
		SimpleGrantedAuthority simplegrantedauthority=new SimpleGrantedAuthority(user.getRole());
		return List.of(simplegrantedauthority);
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
