package com.prokarma.opa.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.web.domain.RegistrationForm;

@Component
public class RegistrationFormConverterImpl implements RegistrationFormConverter   {
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public RegistrationFormConverterImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public RegisterUserDto getDto(RegistrationForm registrationForm) {
		RegisterUserDto registerUserDto = new RegisterUserDto();
		registerUserDto.setEmail(registrationForm.getEmail());
		registerUserDto.setName(registrationForm.getName());
		registerUserDto.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
		registerUserDto.setAddressLineOne(registrationForm.getAddressLineOne());
		registerUserDto.setAddressLineTwo(registrationForm.getAddressLineTwo());
		registerUserDto.setCity(registrationForm.getCity());
		registerUserDto.setState(registrationForm.getState());
		registerUserDto.setPincode(registrationForm.getPincode());
		registerUserDto.setMobileNo(registrationForm.getNumber());
		return registerUserDto;
	}

}
