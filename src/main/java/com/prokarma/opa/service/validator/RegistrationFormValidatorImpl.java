package com.prokarma.opa.service.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prokarma.opa.exception.InvalidRegistrationFormException;
import com.prokarma.opa.util.ValidationUtils;
import com.prokarma.opa.web.domain.Error;
import com.prokarma.opa.web.domain.RegistrationForm;

@Component
public class RegistrationFormValidatorImpl implements RegistrationFormValidator {

	@Override
	public void validate(RegistrationForm registrationForm) {
		List<Error> errors = new ArrayList<>();
		validateEmail(registrationForm.getEmail(), errors);
		validateName(registrationForm.getName(), errors);
		validatePassword(registrationForm.getPassword(), registrationForm.getConfirmPassword(), errors);
		validateAddressLineOne(registrationForm.getAddressLineOne(), errors);
		validateAddressLineTwo(registrationForm.getAddressLineTwo(), errors);
		validateCity(registrationForm.getCity(), errors);
		validateState(registrationForm.getState(), errors);
		validatePincode(registrationForm.getPincode(), errors);
		validateMobileNumber(registrationForm.getNumber(), errors);
		if(errors.size() > 0)
			throw new InvalidRegistrationFormException(errors);
	}

	private static void validateMobileNumber(String mobileNumber, List<Error> errors) {
		if(ValidationUtils.isEmpty(mobileNumber))
			errors.add(new Error("number", "Mobile number cannot be empty"));
		else if(!isValidMobileNumber(mobileNumber))
			errors.add(new Error("number", "Invalid mobile number"));
		
	}

	private static void validatePincode(String pincode, List<Error> errors) {
		if (ValidationUtils.isEmpty(pincode))
			errors.add(new Error("pincode", "Pin code cannot be empty"));
		else if (!isValidPincode(pincode))
			errors.add(new Error("pincode", "Invalid pin code"));
	}

	private static void validateState(String state, List<Error> errors) {
		if (ValidationUtils.isEmpty(state))
			errors.add(new Error("state", "State cannot be empty"));
		else if (state.length() > 30)
			errors.add(new Error("state", "State exceeds maximum length of 30 characters"));
	}

	private static void validateCity(String city, List<Error> errors) {
		if (ValidationUtils.isEmpty(city))
			errors.add(new Error("city", "City cannot be empty"));
		else if (city.length() > 30)
			errors.add(new Error("city", "City exceeds maximum length of 30 characters"));
	}

	private static void validateAddressLineTwo(String addressLineTwo, List<Error> errors) {
		if((addressLineTwo != null) && (addressLineTwo.length() > 50))
			errors.add(new Error("addressLineTwo", "Address line 2 exceeds maximum length of 50 characters"));
	}

	private static void validateAddressLineOne(String addressLineOne, List<Error> errors) {
		if (ValidationUtils.isEmpty(addressLineOne))
			errors.add(new Error("addressLineOne", "Address Line 1 cannot be empty"));
		else if (addressLineOne.length() > 50)
			errors.add(new Error("addressLineOne", "Address line 1 exceeds maximum length of 50 characters"));
	}

	private static void validatePassword(String password, String confirmPassword, List<Error> errors) {
		if (ValidationUtils.isEmpty(password))
			errors.add(new Error("password", "Password cannot be empty"));
		else if (password.length() < 6)
			errors.add(new Error("password", "Password must be a minimum of 6 characters"));
		else if (!isValidPassword(password))
			errors.add(new Error("password", "Invalid password"));
		else if (!areEqual(password, confirmPassword))
			errors.add(new Error("confirmPassword", "Confirm password field must be equal to password field"));
	}

	private static void validateName(String name, List<Error> errors) {
		if (ValidationUtils.isEmpty(name))
			errors.add(new Error("name", "Name cannot be empty"));
	}

	private static void validateEmail(String email, List<Error> errors) {
		if (ValidationUtils.isEmpty(email))
			errors.add(new Error("email", "Email cannot be empty"));
		else if (!isValidEmail(email))
			errors.add(new Error("email", "Invalid email address entered"));
	}

	private static boolean isValidPassword(String password) {
		boolean hasUpperCase = false, hasLowerCase = false, hasSpecialCharacter = false, hasDigit = false;
		for(char c : password.toCharArray()) {
			if(Character.isUpperCase(c)) 
				hasUpperCase = true;
			if(Character.isLowerCase(c))
				hasLowerCase = true;
			if(isSpecialCharacter(c))
				hasSpecialCharacter = true;
			if(isDigit(c))
				hasDigit = true;
			if(hasUpperCase && hasLowerCase && hasSpecialCharacter && hasDigit)
				return true;
		}
		return false;
	}

	private static boolean isDigit(char c) {
		return (c >= '0') && (c <= '9');
	}

	private static boolean isSpecialCharacter(char c) {
		return 	   c == '^' 
				|| c == '#'
				|| c == '$'
				|| c == '*'
				|| c == '.'
				|| c == '!'
				|| c == '&'
				|| c == '@';
	}

	private static boolean areEqual(String value1, String value2) {
		if (value1 != null)
			return value1.equals(value2);
		return false;
	}

	private static boolean isValidEmail(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern emailPattern = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher emailMatcher = emailPattern.matcher(email);
		return emailMatcher.matches();
	}

	private static boolean isValidPincode(String pincode) {
		String pPattern = "[0-9]{6}";
		java.util.regex.Pattern pincodePattern = java.util.regex.Pattern.compile(pPattern);
		java.util.regex.Matcher pincodeMatcher = pincodePattern.matcher(pincode);
		return pincodeMatcher.matches();
	}
	
	private static boolean isValidMobileNumber(String mobileNumber) {
		String mPattern = "[0-9]{10}";
		java.util.regex.Pattern mobileNumberPattern = java.util.regex.Pattern.compile(mPattern);
		java.util.regex.Matcher mobileNumberMatcher = mobileNumberPattern.matcher(mobileNumber);
		return mobileNumberMatcher.matches();
	}

}
