package com.prokarma.opa.service.validator;

import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.web.domain.BidVo;

public interface BidValidator {

	void validate(BidVo bidVo, UserDto bidder);

}