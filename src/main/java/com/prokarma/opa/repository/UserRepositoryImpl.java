package com.prokarma.opa.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.Role;
import com.prokarma.opa.repository.model.UserDto;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private static final String FIND_BY_EMAIL_SQL = "select u.email, u.name, u.password, listagg(r.role, ',') within group (order by r.role) as roles\r\n"
			+ "    from OPA.USER_OPA u inner join OPA.USER_ROLE r\r\n"
			+ "            on u.email= r.email and u.email=:email\r\n"
			+ "group by u.email, u.name, u.password";
	
	private static final String SAVE_USER_SQL = "INSERT INTO opa.user_opa (email, name, password, active, address_line_one, address_line_two, city, state, pincode, mobile_no, created_date) "
			+ "VALUES(:email, :name, :password, :active, :addressLineOne, :addressLineTwo, :city, :state, :pincode, :mobileNo, SYSDATE)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void saveUser(RegisterUserDto registerUserDto) {
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("email", registerUserDto.getEmail());
		sqlParameters.addValue("name", registerUserDto.getName());
		sqlParameters.addValue("password", registerUserDto.getPassword());
		sqlParameters.addValue("active", "Y");
		sqlParameters.addValue("addressLineOne", registerUserDto.getAddressLineOne());
		sqlParameters.addValue("addressLineTwo", registerUserDto.getAddressLineTwo());
		sqlParameters.addValue("city", registerUserDto.getCity());
		sqlParameters.addValue("state", registerUserDto.getState());
		sqlParameters.addValue("pincode", registerUserDto.getPincode());
		sqlParameters.addValue("mobileNo", registerUserDto.getMobileNo());
		jdbcTemplate.update(SAVE_USER_SQL, sqlParameters);
	}

	@Override
	public UserDto findByEmail(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);

		UserDto userDtoReply = jdbcTemplate.query(FIND_BY_EMAIL_SQL, parameters, resultSet -> {
			if (resultSet.next()) {
				final UserDto userDto = new UserDto(resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"));
				userDto.setName(resultSet.getString("name"));
				userDto.setEmail(resultSet.getString("email"));
				userDto.setPassword(resultSet.getString("password"));
				final String commaSeparatedRoles = resultSet.getString("roles");
				Set<Role> roles = new HashSet<>();
				if (StringUtils.isNotBlank(commaSeparatedRoles)) {
					String[] rolesArray = StringUtils.split(commaSeparatedRoles, ",");
					roles.addAll(Arrays.stream(rolesArray).map(role -> Role.valueOf(StringUtils.trim(role)))
							.collect(Collectors.toSet()));
				}
				userDto.setRoles(roles);
				return userDto;
			}
			return null;
		});
		return userDtoReply;

	}

}
