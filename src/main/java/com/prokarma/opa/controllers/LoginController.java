package com.prokarma.opa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prokarma.opa.service.LoginService;
import com.prokarma.opa.web.domain.LoginReplyVo;
import com.prokarma.opa.web.domain.LoginRequestVo;

@RestController
public class LoginController {

	@Autowired
	public LoginService loginService;

	@RequestMapping(value = "/user/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginReplyVo> authenticate(@RequestBody LoginRequestVo loginRequestVo) {
		final LoginReplyVo loginReplyVo = loginService.authenticateByEmailPassword(loginRequestVo);
		return new ResponseEntity<LoginReplyVo>(loginReplyVo, HttpStatus.OK);
	}

}
