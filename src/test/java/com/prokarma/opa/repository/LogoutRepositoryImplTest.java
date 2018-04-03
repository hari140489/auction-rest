package com.prokarma.opa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.prokarma.opa.repository.model.UserDto;
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogoutRepositoryImplTest {
	
	@Autowired
	private LogoutRepository logoutRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Test
	public void testDeleteUserToken_whenUserClickingLogout_thenDeleteTokenFromAccessTokenTable() {
		UserDto user=new UserDto();
		user.setToken("abcde");
	    logoutRepository.deleteUserToken(user.getToken());
	
	}

}
