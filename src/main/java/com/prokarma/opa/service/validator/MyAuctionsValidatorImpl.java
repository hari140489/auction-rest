package com.prokarma.opa.service.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MyAuctionsValidatorImpl implements MyAuctionsValidator {

	@Override
	public void validate(String email) {
		if (StringUtils.isBlank(email)) {
			//throw exception
		}
		// throw exception if email not valid
	}

}
