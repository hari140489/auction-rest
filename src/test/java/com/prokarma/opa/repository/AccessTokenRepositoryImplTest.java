package com.prokarma.opa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.opa.web.domain.LoginReplyVo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccessTokenRepositoryImplTest {
	

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private AccessTokenRepository accessTokenRepository;
	
  
    
	@Test
	public void testCreateUserToken() {
		LoginReplyVo userVo=new LoginReplyVo();		
		userVo.setEmail("user@email.com");	
		userVo.setToken("abcde123");
		
		String sql1="insert into opa.user_opa(email,password,name) values(:email,:password,:name)";
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("email","user@email.com");
		params.addValue("password", "user");
		params.addValue("name", "user123");
		jdbcTemplate.update(sql1, params);
		
		accessTokenRepository.createUserToken(userVo.getEmail());
		
		String sql="select email,token from opa.access_token where email=:email";
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("email",userVo.getEmail());
		
		LoginReplyVo user=jdbcTemplate.queryForObject(sql, parameters,  new RowMapper<LoginReplyVo>() {

			@Override
			public LoginReplyVo mapRow(ResultSet rs, int i) throws SQLException {
				LoginReplyVo userVo = new LoginReplyVo();

				userVo.setEmail(rs.getString("email"));
				userVo.setToken(rs.getString("token"));
				return userVo;
			

	}

});
		assertEquals(userVo.getEmail(),user.getEmail());
		assertEquals(userVo.getEmail(),user.getEmail());
		
	}
}
	
