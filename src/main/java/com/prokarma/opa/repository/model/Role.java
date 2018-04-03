package com.prokarma.opa.repository.model;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	USER("USER"),
	ADMIN("ADMIN");

	private String authority;

	Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}