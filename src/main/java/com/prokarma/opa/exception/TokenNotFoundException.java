package com.prokarma.opa.exception;

public class TokenNotFoundException extends RuntimeException {

	public TokenNotFoundException() {
		super();
	}

	public TokenNotFoundException(String message, Throwable e) {
		super(message, e);
	}

	public TokenNotFoundException(String message) {
		super(message);
	}

}
