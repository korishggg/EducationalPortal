package com.educational.portal.security;

import com.educational.portal.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> grantedAuthorities;

	public static CustomUserDetails fromUserToCustomUserDetails(User user) {
		CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.username = user.getEmail();
		customUserDetails.password = user.getPassword();
		customUserDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
		return customUserDetails;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
