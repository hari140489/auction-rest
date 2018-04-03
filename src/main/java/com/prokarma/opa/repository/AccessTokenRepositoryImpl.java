package com.prokarma.opa.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prokarma.opa.repository.model.AccessTokenDto;

@Repository
public class AccessTokenRepositoryImpl implements AccessTokenRepository {

	private static final String CREATE_TOKEN = "insert into OPA.ACCESS_TOKEN(EMAIL,TOKEN) values (:email,:token)";
	private static final String USER_TOKEN = "select email, token from OPA.ACCESS_TOKEN where token=:token";
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public AccessTokenRepositoryImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public String createUserToken(String email) {
		String token = UUID.randomUUID().toString();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);
		parameters.addValue("token", token);
		namedParameterJdbcTemplate.update(CREATE_TOKEN, parameters);
		return token;
	}

	@Override
	public AccessTokenDto findByToken(String token) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("token", token);
		return namedParameterJdbcTemplate.query(USER_TOKEN, paramMap, (ResultSetExtractor<AccessTokenDto>) resultSet -> {
			if(resultSet.next()) {
				AccessTokenDto accessTokenDto = new AccessTokenDto();
				accessTokenDto.setEmail(resultSet.getString("email"));
				accessTokenDto.setToken(resultSet.getString("token"));
				return accessTokenDto;
			}
			return null;
		});
	}

}
