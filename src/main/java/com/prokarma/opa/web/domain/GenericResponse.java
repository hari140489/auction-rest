package com.prokarma.opa.web.domain;

import java.util.List;

public class GenericResponse {

	private String message;
	private List<Error> errors;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

}
