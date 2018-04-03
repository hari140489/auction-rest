package com.prokarma.opa.service;

import com.prokarma.opa.web.domain.GenericResponse;

public interface LogoutService {

	GenericResponse deleteUserToken(String token);

}
