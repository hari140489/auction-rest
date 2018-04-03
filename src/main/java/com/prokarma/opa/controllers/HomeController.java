package com.prokarma.opa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

	@GetMapping
	public ResponseEntity<String> home() {
		return new ResponseEntity<>("welcome to online product auction", HttpStatus.OK);
	}

	@GetMapping("/exception")
	public ResponseEntity<String> exception() {
		throw new NullPointerException();
	}
	
}
