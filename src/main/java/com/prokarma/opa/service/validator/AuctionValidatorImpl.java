package com.prokarma.opa.service.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.prokarma.opa.exception.InvalidCreateAuctionException;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.Error;

@Component
public class AuctionValidatorImpl implements AuctionValidator {
	
	@Override
	public void validate(AuctionVo auctionvo) {
		List<Error> errors = new ArrayList<>();
		validateName(auctionvo, errors);
		validateType(auctionvo, errors);
		validatePrice(auctionvo, errors);
		validateexpireddate(auctionvo, errors);
		
		validrteImage(auctionvo, errors);
		if (errors.size() > 0)
			throw new InvalidCreateAuctionException(errors);

	}

	private static void validateName(AuctionVo auctionvo, List<Error> errors) {
		if (isEmpty(auctionvo.getName()))
			errors.add(new Error("name", "Name cannot be empty"));
	}

	private static boolean isEmpty(String value) {
		return "".equals(value);
	}

	private static void validateType(AuctionVo auctionvo, List<Error> errors) {
		if (isEmpty(auctionvo.getType()))
			errors.add(new Error("type", "type cannot be empty"));
	}

	private static void validatePrice(AuctionVo auctionvo, List<Error> errors) {
		if (isEmpty(auctionvo.getPrice()))
			errors.add(new Error("price", "price cannot be empty"));
		else if (auctionvo.getPrice() < 0)
			errors.add(new Error("price", "price must be positive number"));
	}

	private static boolean isEmpty(int price) {
	
		return price == 0;
	}

	private static void validateexpireddate(AuctionVo auctionvo, List<Error> errors) {
		if (isEmpty(auctionvo.getExpiredate()))
			errors.add(new Error("expiredate", "date cannot be empty"));
		else if (new Date().after(auctionvo.getExpiredate()))
			errors.add(new Error("expireddate", "date must be future"));
	}

	private static boolean isEmpty(Date expiredate) {
		return expiredate == null;
	}


	private static void validrteImage(AuctionVo auctionvo, List<Error> errors) {
		if (isEmpty(auctionvo.getImage()))
			errors.add(new Error("image", "image cannot be empty"));
	}

	private static boolean isEmpty(byte[] image) {
		return (image == null) || (image.length == 0);
	}

}
