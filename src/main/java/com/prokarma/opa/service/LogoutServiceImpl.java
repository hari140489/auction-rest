package com.prokarma.opa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prokarma.opa.repository.LogoutRepository;
import com.prokarma.opa.web.domain.GenericResponse;

@Service
public class LogoutServiceImpl implements LogoutService {

	private final LogoutRepository logoutRepository;

	@Autowired
	public LogoutServiceImpl(final LogoutRepository logoutRepository) {
		this.logoutRepository = logoutRepository;

	}

	@Override
	public GenericResponse deleteUserToken(String token) {
		logoutRepository.deleteUserToken(token);
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("Logged out successfully!");
		return genericResponse;
	}

}
