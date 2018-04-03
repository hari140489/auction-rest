package com.prokarma.opa.converter;

import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.web.domain.RegistrationForm;

public interface RegistrationFormConverter {
	
	public RegisterUserDto getDto(RegistrationForm registrationForm);
	
}