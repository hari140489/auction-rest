package com.prokarma.opa.exception;

import java.util.List;

public class InvalidBidException extends RuntimeException {
	
	private List<Error> errors;

	public InvalidBidException(String message) {
		super(message);
	}

	public InvalidBidException(String message, List<Error> errors) {
		super(message);
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

	
	
}
