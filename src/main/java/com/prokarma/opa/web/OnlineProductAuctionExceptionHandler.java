package com.prokarma.opa.web;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prokarma.opa.exception.InvalidAuctionSearchException;
import com.prokarma.opa.exception.InvalidBidException;
import com.prokarma.opa.exception.InvalidCreateAuctionException;
import com.prokarma.opa.exception.ProductNotFoundException;
import com.prokarma.opa.exception.TokenNotFoundException;
import com.prokarma.opa.exception.UnauthenticatedException;
import com.prokarma.opa.web.domain.GenericResponse;

@RestControllerAdvice
public class OnlineProductAuctionExceptionHandler {
	private static Logger logger = (Logger) LoggerFactory.getLogger(OnlineProductAuctionExceptionHandler.class);

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<GenericResponse> handleRuntimeException(RuntimeException ex) {
		GenericResponse genericResponse = new GenericResponse();
		com.prokarma.opa.web.domain.Error error = new com.prokarma.opa.web.domain.Error();
		logger.error(ex.getMessage());
		logger.error(ex.getStackTrace().toString());
		error.setMessage("Something went wrong, please try again later");
		genericResponse.setErrors(Collections.singletonList(error));
		ex.printStackTrace();
		return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UnauthenticatedException.class)
	public ResponseEntity<GenericResponse> handleUnauthenticatedException(UnauthenticatedException ex) {
		GenericResponse genericResponse = new GenericResponse();
		com.prokarma.opa.web.domain.Error error = new com.prokarma.opa.web.domain.Error();
		error.setMessage("User is not logged in");
		genericResponse.setErrors(Collections.singletonList(error));
		return new ResponseEntity<>(genericResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<GenericResponse> handleBadCredentialsException(BadCredentialsException ex) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("Unable to login with entered credentials");
		return new ResponseEntity<>(genericResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<GenericResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("Unable to access the requested resource, reason: " + ex.getMessage());
		return new ResponseEntity<>(genericResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<GenericResponse> handleProductNotFoundException(ProductNotFoundException ex) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("Product not found");
		return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidBidException.class)
	public ResponseEntity<GenericResponse> handleInvalidBidException(InvalidBidException ex) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("Invalid bid. " + ex.getMessage());
		return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidAuctionSearchException.class)
	public ResponseEntity<GenericResponse> handleInvalidAuctionSearchException(InvalidAuctionSearchException ex) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("Both name and type cannot be empty");
		return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidCreateAuctionException.class)
	public ResponseEntity<GenericResponse> handleInvalidCreateAuctionException(InvalidCreateAuctionException ex) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("Error validating Create Auction");
		genericResponse.setErrors(ex.getErrors());
		return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
	}
	
	
	
}
