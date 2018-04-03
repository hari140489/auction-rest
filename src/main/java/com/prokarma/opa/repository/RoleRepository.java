package com.prokarma.opa.repository;

import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.Role;

public interface RoleRepository {
	
	public void assignRoleToUser(RegisterUserDto userDto, Role role);
	
}
