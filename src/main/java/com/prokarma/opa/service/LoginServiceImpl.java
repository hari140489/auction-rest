package com.prokarma.opa.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.prokarma.opa.exception.UnauthenticatedException;
import com.prokarma.opa.repository.AccessTokenRepository;
import com.prokarma.opa.repository.UserRepository;
import com.prokarma.opa.repository.model.Role;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.web.domain.LoginReplyVo;
import com.prokarma.opa.web.domain.LoginRequestVo;

@Service
public class LoginServiceImpl implements LoginService {

	private final AuthenticationManager authenticationManager;

	private final AccessTokenRepository accessTokenRepository;

	private final UserRepository userRepository;

	@Autowired
	public LoginServiceImpl(final AuthenticationManager authenticationManager,
			final AccessTokenRepository accessTokenRepository, final UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.accessTokenRepository = accessTokenRepository;

		this.userRepository = userRepository;
	}

	@Override
	public LoginReplyVo authenticateByEmailPassword(LoginRequestVo userVo) {
		String email = userVo.getEmail();
		String password = userVo.getPassword();

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetails)) {
			throw new UnauthenticatedException("Wrong credentials");
		}

		String userToken = accessTokenRepository.createUserToken(userVo.getEmail());
		UserDto userDto = userRepository.findByEmail(email);
		LoginReplyVo loginReplyVo = new LoginReplyVo();
		loginReplyVo.setEmail(email);
		loginReplyVo.setToken(userToken);
		loginReplyVo.setName(userDto.getName());
		Set<Role> roles = userDto.getRoles();
		if (CollectionUtils.isNotEmpty(roles)) {
			loginReplyVo.setRoles(roles.stream().map(role -> role.name()).collect(Collectors.toSet()));
		}
		return loginReplyVo;
	}
}
