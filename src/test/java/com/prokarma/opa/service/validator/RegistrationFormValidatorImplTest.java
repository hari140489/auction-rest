package com.prokarma.opa.service.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.prokarma.opa.exception.InvalidRegistrationFormException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.web.domain.RegistrationForm;

public class RegistrationFormValidatorImplTest {

	private RegistrationFormValidator registrationFormValidator;

	private RegistrationForm registrationForm;

	@BeforeEach
	public void setup() {
		registrationFormValidator = new RegistrationFormValidatorImpl();

		registrationForm = OnlineProductAuctionFixture.registrationForm();

	}

	@Test
	public void whenFormFieldsValid_thenNoValidationErrors() {
		registrationFormValidator.validate(registrationForm);
	}

	@Test
	public void whenAllFieldsAreEmpty_ReturnErrorsListOfSize8() {

		registrationForm.setEmail("");
		registrationForm.setName("");
		registrationForm.setPassword("");
		registrationForm.setConfirmPassword("");
		registrationForm.setAddressLineOne("");
		registrationForm.setAddressLineTwo("");
		registrationForm.setCity("");
		registrationForm.setState("");
		registrationForm.setPincode("");
		registrationForm.setNumber("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class, 
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(8, exception.getErrors().size());
	}

	@Test
	public void whenEmailFieldIsEmpty_ReturnEmptyEmailError() {
		registrationForm.setEmail("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("email", exception.getErrors().get(0).getField());
		assertEquals("Email cannot be empty", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenInvalidEmailEntered_ReturnInvalidEmailError() {
		registrationForm.setEmail("abc.xyz");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("email", exception.getErrors().get(0).getField());
		assertEquals("Invalid email address entered", exception.getErrors().get(0).getMessage());

	}

	@Test
	public void whenNameFieldIsEmpty_ReturnEmptyNameError() {
		registrationForm.setName("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("name", exception.getErrors().get(0).getField());
		assertEquals("Name cannot be empty", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenPasswordFieldIsEmpty_ReturnEmptyPasswordError() {
		registrationForm.setPassword("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size(), 1);
		assertEquals("password", exception.getErrors().get(0).getField());
		assertEquals("Password cannot be empty", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenPasswordFieldIsLessThan6CharactersLong_ReturnPasswordTooShortError() {
		registrationForm.setPassword("pw^#d");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("password", exception.getErrors().get(0).getField());
		assertEquals("Password must be a minimum of 6 characters", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenPasswordFieldDoesNotSatisfyConditions_ReturnInvalidPasswordError() {
		registrationForm.setPassword("$PASS*WRD");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("password", exception.getErrors().get(0).getField());
		assertEquals("Invalid password", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenPasswordFieldsDoNotMatch_ReturnPasswordsNotEqualError() {
		registrationForm.setPassword("P@ss.word1");
		registrationForm.setConfirmPassword("P@ss&word2!");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("confirmPassword", exception.getErrors().get(0).getField());
		assertEquals("Confirm password field must be equal to password field", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenAddressLineOneFieldIsEmpty_ReturnEmptyAddressLineOneError() {
		registrationForm.setAddressLineOne("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(exception.getErrors().size(), 1);
		assertEquals(exception.getErrors().get(0).getField(), "addressLineOne");
		assertEquals(exception.getErrors().get(0).getMessage(), "Address Line 1 cannot be empty");
	}

	@Test
	public void whenAddressLineOneFieldLengthExceeds30_ReturnAddressLineOneengthTooLongError() {
		registrationForm.setAddressLineOne("This address line one field is greater than 50 characters long");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("addressLineOne", exception.getErrors().get(0).getField());
		assertEquals("Address line 1 exceeds maximum length of 50 characters", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenAddressLineTwoFieldIsEmpty_DonotReturnErrors() {
		registrationForm.setAddressLineTwo("");
	}

	@Test
	public void whenAddressLineTwoFieldLengthExceeds30_ReturnAddressLineTwoLengthTooLongError() {
		registrationForm.setAddressLineTwo("This address line two field is greater than 50 characters long");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(exception.getErrors().size(), 1);
		assertEquals(exception.getErrors().get(0).getField(), "addressLineTwo");
		assertEquals(exception.getErrors().get(0).getMessage(),
				"Address line 2 exceeds maximum length of 50 characters");
	}

	@Test
	public void whenCityFieldIsEmpty_ReturnEmptyCityError() {
		registrationForm.setCity("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(exception.getErrors().size(), 1);
		assertEquals(exception.getErrors().get(0).getField(), "city");
		assertEquals(exception.getErrors().get(0).getMessage(), "City cannot be empty");
	}

	@Test
	public void whenCityFieldLengthExceeds30_ReturnCityFieldLengthTooLargeError() {
		registrationForm.setCity("This city length is greater than 30 characters long");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(exception.getErrors().size(), 1);
		assertEquals(exception.getErrors().get(0).getField(), "city");
		assertEquals(exception.getErrors().get(0).getMessage(), "City exceeds maximum length of 30 characters");
	}

	@Test
	public void whenStateFieldIsEmpty_ReturnEmptyStateError() {
		registrationForm.setState("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(exception.getErrors().size(), 1);
		assertEquals(exception.getErrors().get(0).getField(), "state");
		assertEquals(exception.getErrors().get(0).getMessage(), "State cannot be empty");
	}

	@Test
	public void whenStateFieldLengthExceeds30_ReturnStateFieldLengthTooLargeError() {
		registrationForm.setState("This state length is greater than 30 characters long");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(exception.getErrors().size(), 1);
		assertEquals(exception.getErrors().get(0).getField(), "state");
		assertEquals(exception.getErrors().get(0).getMessage(), "State exceeds maximum length of 30 characters");
	}

	@Test
	public void whenPincodeFieldIsEmpty_ReturnEmptyPincodeError() {
		registrationForm.setPincode("");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("pincode", exception.getErrors().get(0).getField());
		assertEquals("Pin code cannot be empty", exception.getErrors().get(0).getMessage());
	}

	@Test
	public void whenPincodeFieldIsInvalid_ReturnInvalidPincodeError() {
		registrationForm.setPincode("58966");

		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));

		assertEquals(1, exception.getErrors().size());
		assertEquals("pincode", exception.getErrors().get(0).getField());
		assertEquals("Invalid pin code", exception.getErrors().get(0).getMessage());
	}
	
	@Test
	public void whenMobileNoFieldIsEmpty_ReturnEmptyMobileNumberError() {
		registrationForm.setNumber(null);
		
		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));
		
		assertEquals(1, exception.getErrors().size());
		assertEquals("number", exception.getErrors().get(0).getField());
		assertEquals("Mobile number cannot be empty", exception.getErrors().get(0).getMessage());
	}
	
	@Test
	public void whenMobileNoLengthIsLessThan10_ReturnInvalidMobileNumberError() {
		registrationForm.setNumber("48596");
		
		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));
		
		assertEquals(1, exception.getErrors().size());
		assertEquals("number", exception.getErrors().get(0).getField());
		assertEquals("Invalid mobile number", exception.getErrors().get(0).getMessage());	
	}

	@Test
	public void whenMobileNoLengthIsGreaterThan10_ReturnInvalidMobileNumberError() {
		registrationForm.setNumber("4859658963245");
		
		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));
		
		assertEquals(1, exception.getErrors().size());
		assertEquals("number", exception.getErrors().get(0).getField());
		assertEquals("Invalid mobile number", exception.getErrors().get(0).getMessage());	
	}
	
	@Test
	public void whenMobileNoLengthContainNonNumeric_ReturnInvalidMobileNumberError() {
		registrationForm.setNumber("5asefwe3");
		
		InvalidRegistrationFormException exception = assertThrows(InvalidRegistrationFormException.class,
				() -> registrationFormValidator.validate(registrationForm));
		
		assertEquals(1, exception.getErrors().size());
		assertEquals("number", exception.getErrors().get(0).getField());
		assertEquals("Invalid mobile number", exception.getErrors().get(0).getMessage());	
	}
	
}
