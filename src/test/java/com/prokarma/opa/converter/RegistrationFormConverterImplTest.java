package com.prokarma.opa.converter;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.web.domain.RegistrationForm;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationFormConverterImplTest {

	RegistrationFormConverter registrationFormConverter;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Before
	public void setUp() throws Exception {
		registrationFormConverter = new RegistrationFormConverterImpl(passwordEncoder);
	}

	@Test
	public void testGetDto() {
		RegistrationForm registrationForm = OnlineProductAuctionFixture.registrationForm();
		when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encoded-password");
		
		RegisterUserDto registerUserDto = registrationFormConverter.getDto(registrationForm);
		
		verify(passwordEncoder).encode(registrationForm.getPassword());		
		assertEquals(registrationForm.getEmail(), registerUserDto.getEmail());
		assertEquals(registrationForm.getName(), registerUserDto.getName());
		assertEquals("encoded-password", registerUserDto.getPassword());
		assertEquals(registrationForm.getAddressLineOne(), registerUserDto.getAddressLineOne());
		assertEquals(registrationForm.getAddressLineTwo(), registerUserDto.getAddressLineTwo());
		assertEquals(registrationForm.getCity(), registerUserDto.getCity());
		assertEquals(registrationForm.getState(), registerUserDto.getState());
		assertEquals(registrationForm.getPincode(), registerUserDto.getPincode());
		assertEquals(registrationForm.getNumber(), registerUserDto.getMobileNo());
		
	}

}
