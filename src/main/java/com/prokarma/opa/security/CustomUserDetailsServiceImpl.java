package com.prokarma.opa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.opa.exception.TokenNotFoundException;
import com.prokarma.opa.repository.AccessTokenRepository;
import com.prokarma.opa.repository.UserRepository;
import com.prokarma.opa.repository.model.AccessTokenDto;
import com.prokarma.opa.repository.model.UserDto;

@Component
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	private final UserRepository userRepository;

	private final AccessTokenRepository accessTokenRepository;

	@Autowired
	public CustomUserDetailsServiceImpl(final UserRepository userRepository,
			final AccessTokenRepository accessTokenRepository) {
		this.userRepository = userRepository;
		this.accessTokenRepository = accessTokenRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDto userDto = userRepository.findByEmail(email);
		if(userDto == null)
			throw new UsernameNotFoundException("Username was not found");
		return userDto;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto findUserByAccessToken(String accessToken) {
		AccessTokenDto accessTokenDto = accessTokenRepository.findByToken(accessToken);
		if (accessTokenDto == null) {
			throw new TokenNotFoundException("Unable to find token");
		}
		return loadUserByUsername(accessTokenDto.getEmail());
	}

}
