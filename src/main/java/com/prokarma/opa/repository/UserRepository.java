package com.prokarma.opa.repository;

import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.UserDto;

public interface UserRepository {

	void saveUser(RegisterUserDto registerUserDto);

	UserDto findByEmail(String email);
}
