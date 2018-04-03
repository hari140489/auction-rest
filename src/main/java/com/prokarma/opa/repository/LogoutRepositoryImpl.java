package com.prokarma.opa.repository;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogoutRepositoryImpl implements LogoutRepository {
	private static Logger logger = Logger.getLogger(LogoutRepositoryImpl.class);

	public static final String DELETE_USER_TOKEN_ON_LOGOUT_SQL = "delete from opa.access_token where token=:token";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public LogoutRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public void deleteUserToken(String token) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("token", token);
		jdbcTemplate.update(DELETE_USER_TOKEN_ON_LOGOUT_SQL, parameters);
		logger.info("user token deleted");
	}

}
