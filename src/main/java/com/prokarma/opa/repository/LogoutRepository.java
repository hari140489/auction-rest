package com.prokarma.opa.repository;

public interface LogoutRepository {
	
	void deleteUserToken(String token);

}
