package com.prokarma.opa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.Role;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

	private static final String ASSIGN_ROLE_SQL= "INSERT INTO opa.user_role (role, email) VALUES (:role, :email)";
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public RoleRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void assignRoleToUser(RegisterUserDto userDto, Role role) {
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlParams.addValue("role", role.toString());
		sqlParams.addValue("email", userDto.getEmail());
		jdbcTemplate.update(ASSIGN_ROLE_SQL, sqlParams);
	}

}
