package com.prokarma.opa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="The given product was not found")
public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException() {
		super();
	}
	
	
	
}
