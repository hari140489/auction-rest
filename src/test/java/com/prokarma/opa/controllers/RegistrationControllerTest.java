package com.prokarma.opa.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.prokarma.opa.exception.InvalidRegistrationFormException;
import com.prokarma.opa.service.RegistrationService;
import com.prokarma.opa.web.domain.Error;
import com.prokarma.opa.web.domain.RegistrationForm;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RegistrationController.class, secure = false)
public class RegistrationControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RegistrationService registrationService;

	@Test
	public void testRegister_whenEmailAlreadyExists_RespondWithStatusBadRequest() throws Exception {
		
		String registrationFormJson = "{"
				+ "\"name\": \"User Name\","
				+ "\"email\": \"user@email.com\","
				+ "\"addressLineOne\": \"address line 1\","
				+ "\"addressLineTwo\": \"address line 2\""
				+ "}";
				
		doThrow(DuplicateKeyException.class).when(registrationService).registerUser(any(RegistrationForm.class));
		
		mvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(registrationFormJson))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors.size()", is(1)))
			.andExpect(jsonPath("$.message", is("User creation error")))
			.andExpect(jsonPath("$.errors[0].field", is("email")))
			.andExpect(jsonPath("$.errors[0].message", is("Email already exists")));
				
	}
	
	@Test
	public void testRegister_whenUserCreatedSuccessfully_RespondWithStatusCreated() throws Exception {
		String registrationFormJson = "{"
				+ "\"name\": \"User Name\","
				+ "\"email\": \"user@email.com\","
				+ "\"addressLineOne\": \"address line 1\","
				+ "\"addressLineTwo\": \"address line 2\""
				+ "}";
		
		doNothing().when(registrationService).registerUser(any(RegistrationForm.class));
		
		mvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(registrationFormJson))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message", is("User created successfully")))
			.andExpect(jsonPath("$.errors.size()", nullValue()));
		
	}
	
	@Test
	public void testRegister_whenInvalidRegistrationForm_ResponsdWithBadRequest() throws Exception {
		String registrationFormJson = "{"
				+ "\"name\": \"User Name\","
				+ "\"email\": \"user@email.com\","
				+ "\"addressLineOne\": \"address line 1\","
				+ "\"addressLineTwo\": \"address line 2\""
				+ "}";
		
		List<Error> errors = new ArrayList<>();
		errors.add(new Error("email", "Invalid email"));
		errors.add(new Error("confirmPassword", "Passwords do not match"));
		errors.add(new Error("PinCode", "Invalid pin code"));
		
		doThrow(new InvalidRegistrationFormException(errors)).when(registrationService).registerUser(any(RegistrationForm.class));
		
		mvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(registrationFormJson))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", is("Error validating registration form")))
			.andExpect(jsonPath("$.errors.size()", is(errors.size())))
			.andExpect(jsonPath("$.errors[0].field", is(errors.get(0).getField())))
			.andExpect(jsonPath("$.errors[0].message", is(errors.get(0).getMessage())))
			.andExpect(jsonPath("$.errors[1].field", is(errors.get(1).getField())))
			.andExpect(jsonPath("$.errors[1].message", is(errors.get(1).getMessage())))
			.andExpect(jsonPath("$.errors[2].field", is(errors.get(2).getField())))
			.andExpect(jsonPath("$.errors[2].message", is(errors.get(2).getMessage())));
		
	}

}
