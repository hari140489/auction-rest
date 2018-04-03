package com.prokarma.opa.service;

import com.prokarma.opa.web.domain.LoginReplyVo;
import com.prokarma.opa.web.domain.LoginRequestVo;

public interface LoginService {

	LoginReplyVo authenticateByEmailPassword(LoginRequestVo userVo);

}
