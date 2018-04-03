package com.prokarma.opa.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.service.LogoutService;
import com.prokarma.opa.web.domain.GenericResponse;

@RestController
public class LogoutController {
	
	@Autowired
	private LogoutService logOutService;

	@RequestMapping(value = "/user-logout", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> deleteUserToken(@AuthenticationPrincipal final UserDto userDto, HttpServletRequest request) {
		String token = request.getHeader("X-Access-Token");
		final GenericResponse response = logOutService.deleteUserToken(token);
		return new ResponseEntity<>(response,HttpStatus.OK);			
	}
}
