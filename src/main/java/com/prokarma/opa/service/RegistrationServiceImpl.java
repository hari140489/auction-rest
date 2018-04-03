package com.prokarma.opa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.opa.converter.RegistrationFormConverter;
import com.prokarma.opa.repository.RoleRepository;
import com.prokarma.opa.repository.UserRepository;
import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.Role;
import com.prokarma.opa.service.validator.RegistrationFormValidator;
import com.prokarma.opa.web.domain.RegistrationForm;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private RegistrationFormConverter registrationFormConverter;
	private RegistrationFormValidator registrationFormValidator;
	
	@Autowired
	public RegistrationServiceImpl(UserRepository userRepository, RegistrationFormConverter registrationFormConverter,
			RegistrationFormValidator registrationFormValidator, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.registrationFormConverter = registrationFormConverter;
		this.registrationFormValidator = registrationFormValidator;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public void registerUser(RegistrationForm registrationForm) {
		registrationFormValidator.validate(registrationForm);
		RegisterUserDto userDto = registrationFormConverter.getDto(registrationForm);
		userRepository.saveUser(userDto);
		roleRepository.assignRoleToUser(userDto, Role.USER);
	}

}
