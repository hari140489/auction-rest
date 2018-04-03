package com.prokarma.opa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Both name and type cannot be empty")
public class InvalidAuctionSearchException extends RuntimeException {
	
	public InvalidAuctionSearchException() {
		super();
	}
	
	

}
