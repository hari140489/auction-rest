package com.prokarma.opa.repository;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.opa.OnlineProductAuctionApplication;
import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.Role;
import com.prokarma.opa.repository.model.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineProductAuctionApplication.class)
@Transactional
public class UserRepositoryImplTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Test
	public void testSaveUser() throws Exception {
		RegisterUserDto registerUserDto = new RegisterUserDto();
		registerUserDto.setEmail("user@mail.com");
		registerUserDto.setName("user");
		registerUserDto.setPassword("P@assw0rd");
		registerUserDto.setAddressLineOne("Address line one");
		registerUserDto.setAddressLineTwo("Address line two");
		registerUserDto.setCity("City");
		registerUserDto.setState("State");
		registerUserDto.setPincode("575663");
		registerUserDto.setMobileNo("8965852145");

		userRepository.saveUser(registerUserDto);

		String retrieveSQL = "SELECT email, name, password, active, address_line_one, address_line_two, city, state, pincode, created_date, mobile_no FROM opa.user_opa WHERE email = :email";

		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("email", registerUserDto.getEmail());
		
		RegisterUserDto registeredUser = jdbcTemplate.queryForObject(retrieveSQL, sqlParameters, new RowMapper<RegisterUserDto>() {

			@Override
			public RegisterUserDto mapRow(ResultSet rs, int i) throws SQLException {
				RegisterUserDto registerUserDto = new RegisterUserDto();

				registerUserDto.setEmail(rs.getString("email"));
				registerUserDto.setName(rs.getString("name"));
				registerUserDto.setPassword(rs.getString("password"));
				registerUserDto.setAddressLineOne(rs.getString("address_line_one"));
				registerUserDto.setAddressLineTwo(rs.getString("address_line_two"));
				registerUserDto.setCity(rs.getString("city"));
				registerUserDto.setState(rs.getString("state"));
				registerUserDto.setPincode(rs.getString("pincode"));
				registerUserDto.setMobileNo(rs.getString("mobile_no"));

				return registerUserDto;
			}

		});
		
		assertEquals(registerUserDto.getEmail(), registeredUser.getEmail());
		assertEquals(registerUserDto.getName(), registeredUser.getName());
		assertEquals(registerUserDto.getPassword(), registeredUser.getPassword());
		assertEquals(registerUserDto.getAddressLineOne(), registeredUser.getAddressLineOne());
		assertEquals(registerUserDto.getAddressLineTwo(), registeredUser.getAddressLineTwo());
		assertEquals(registerUserDto.getCity(), registeredUser.getCity());
		assertEquals(registerUserDto.getState(), registeredUser.getState());
		assertEquals(registerUserDto.getPincode(), registeredUser.getPincode());
		assertEquals(registerUserDto.getMobileNo(), registeredUser.getMobileNo());

	}
	
	@Test(expected=DuplicateKeyException.class)
	public void testSaveUser_whenEmailExists_throwDuplicateKeyException() {
		RegisterUserDto registerUserDto = new RegisterUserDto();
		registerUserDto.setEmail("user@mail.com");
		registerUserDto.setName("user");
		registerUserDto.setPassword("P@assw0rd");
		registerUserDto.setAddressLineOne("Address line one");
		registerUserDto.setAddressLineTwo("Address line two");
		registerUserDto.setCity("City");
		registerUserDto.setState("State");
		registerUserDto.setPincode("575663");

		userRepository.saveUser(registerUserDto);
		userRepository.saveUser(registerUserDto);
	}

	@Test
	public void testFindByEmail_whenUserFound_thenReturnUser() {
		UserDto userDto=new UserDto();
		userDto.setEmail("abc@xyzs.com");
		userDto.setName("user123");
		userDto.setPassword("user");
		
		String sql="insert into opa.user_opa(email,password,name) values(:email,:password,:name)";
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("email", userDto.getEmail());
		parameters.addValue("password", userDto.getPassword());
		parameters.addValue("name", userDto.getName());
		parameters.addValue("role", Role.USER.toString());
		jdbcTemplate.update(sql, parameters);
		jdbcTemplate.update("INSERT INTO opa.user_role (role, email) VALUES (:role, :email)", parameters);
		
		UserDto user = userRepository.findByEmail(userDto.getEmail());

		assertEquals(userDto.getEmail(), user.getEmail());
		assertEquals(userDto.getName(), user.getName());
		assertEquals(userDto.getPassword(), user.getPassword());
	}
	
	@Test
	public void testFindByEmail_whenUserNotFound_thenReturnNull() {
		UserDto user = userRepository.findByEmail("abc@xyzs.com");
		
		assertEquals(null, user);
	}
}
