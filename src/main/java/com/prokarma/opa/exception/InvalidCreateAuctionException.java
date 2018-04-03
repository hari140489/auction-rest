package com.prokarma.opa.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.prokarma.opa.web.domain.Error;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error validating create auction")
public class InvalidCreateAuctionException extends RuntimeException {

	private List<Error> errors;

	public InvalidCreateAuctionException(List<Error> errors) {
		
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

}

