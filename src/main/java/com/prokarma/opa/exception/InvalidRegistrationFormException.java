package com.prokarma.opa.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.prokarma.opa.web.domain.Error;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error validating registration form")
public class InvalidRegistrationFormException extends RuntimeException {

	private List<Error> errors;

	public InvalidRegistrationFormException(List<Error> errors) {
		
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

}
