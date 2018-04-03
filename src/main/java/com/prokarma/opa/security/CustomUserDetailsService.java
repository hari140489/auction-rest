package com.prokarma.opa.security;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.prokarma.opa.repository.model.UserDto;

public interface CustomUserDetailsService extends UserDetailsService {

	UserDto findUserByAccessToken(String accessToken);

}
