package com.prokarma.opa.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prokarma.opa.exception.InvalidRegistrationFormException;
import com.prokarma.opa.service.RegistrationService;
import com.prokarma.opa.web.domain.Error;
import com.prokarma.opa.web.domain.GenericResponse;
import com.prokarma.opa.web.domain.RegistrationForm;

@RestController
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@PostMapping("/register")
	public ResponseEntity<GenericResponse> register(@RequestBody RegistrationForm registrationForm) {
		registrationService.registerUser(registrationForm);
		GenericResponse response = new GenericResponse();
		response.setMessage("User created successfully");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@ExceptionHandler(InvalidRegistrationFormException.class)
	public ResponseEntity<GenericResponse> validationError(HttpServletRequest request,
			InvalidRegistrationFormException exception) {
		GenericResponse response = new GenericResponse();
		response.setMessage("Error validating registration form");
		response.setErrors(exception.getErrors());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<GenericResponse> emailFound(HttpServletRequest request, DuplicateKeyException exception) {
		GenericResponse response = new GenericResponse();
		response.setMessage("User creation error");
		List<Error> errors = new ArrayList<>();
		errors.add(new Error("email", "Email already exists"));
		response.setErrors(errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
