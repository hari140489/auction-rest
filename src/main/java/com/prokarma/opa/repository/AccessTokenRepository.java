package com.prokarma.opa.repository;

import com.prokarma.opa.repository.model.AccessTokenDto;

public interface AccessTokenRepository {

	String createUserToken(String email);

	AccessTokenDto findByToken(String token);
}
