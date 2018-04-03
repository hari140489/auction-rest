package com.prokarma.opa;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.opa.repository.model.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineProductAuctionApplication.class)
@Transactional
public class OnlineProductAuctionApplicationTests {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Test
	public void DBConnectionTest() throws Exception {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("name", "user");
		parameters.addValue("email", "xyz@zaq.com");
		parameters.addValue("password", "password");

		jdbcTemplate.update("INSERT INTO opa.user_opa (name, email, password) VALUES (:name, :email, :password)",
				parameters);

		UserDto user = jdbcTemplate.queryForObject("SELECT name, email, password FROM opa.user_opa WHERE email = :email",
				parameters, new RowMapper<UserDto>() {

					@Override
					public UserDto mapRow(ResultSet rs, int i) throws SQLException {
						UserDto user = new UserDto();

						user.setName(rs.getString("name"));
						user.setEmail(rs.getString("email"));
						user.setPassword(rs.getString("password"));

						return user;
					}

				});

		assertEquals("user", user.getName());
		assertEquals("xyz@zaq.com", user.getEmail());
		assertEquals("password", user.getPassword());
	}

}
