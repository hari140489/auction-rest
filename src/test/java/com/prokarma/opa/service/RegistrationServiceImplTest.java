package com.prokarma.opa.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prokarma.opa.converter.RegistrationFormConverter;
import com.prokarma.opa.exception.InvalidRegistrationFormException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.RoleRepository;
import com.prokarma.opa.repository.UserRepository;
import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.Role;
import com.prokarma.opa.service.validator.RegistrationFormValidator;
import com.prokarma.opa.web.domain.RegistrationForm;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {

	private RegistrationService registrationService;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;

	@Mock
	private RegistrationFormValidator registrationFormValidator;

	@Mock
	private RegistrationFormConverter registrationFormConverter;

	@Before
	public void setup() {
		registrationService = new RegistrationServiceImpl(userRepository, registrationFormConverter, registrationFormValidator, roleRepository);
	}

	@Test
	public void testRegisterUser_WithValidData_callSaveUserRepositoryMethod() {

		doNothing().when(registrationFormValidator).validate(any(RegistrationForm.class));
		doNothing().when(roleRepository).assignRoleToUser(any(RegisterUserDto.class), any(Role.class));
		when(registrationFormConverter.getDto(any(RegistrationForm.class))).thenReturn(OnlineProductAuctionFixture.registerUserDto());
				
		registrationService.registerUser(OnlineProductAuctionFixture.registrationForm());

		ArgumentCaptor<RegisterUserDto> argumentCaptor = ArgumentCaptor.forClass(RegisterUserDto.class);

		verify(registrationFormValidator).validate(any(RegistrationForm.class));
		verify(registrationFormConverter).getDto(any(RegistrationForm.class));
		verify(userRepository).saveUser(argumentCaptor.capture());
		
		RegisterUserDto registerUserDto = argumentCaptor.getValue();
		
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getPassword(), registerUserDto.getPassword());
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getEmail(), registerUserDto.getEmail());
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getName(), registerUserDto.getName());
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getAddressLineOne(), registerUserDto.getAddressLineOne());
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getAddressLineTwo(), registerUserDto.getAddressLineTwo());
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getCity(), registerUserDto.getCity());
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getState(), registerUserDto.getState());
		assertEquals(OnlineProductAuctionFixture.registerUserDto().getPincode(), registerUserDto.getPincode());

	}

	@Test(expected = InvalidRegistrationFormException.class)
	public void testRegisterUser_WithInvalidRegistrationForm_ThrowInvalidRegistrationFormException() {
		doThrow(InvalidRegistrationFormException.class).when(registrationFormValidator).validate(any(RegistrationForm.class));

		registrationService.registerUser(OnlineProductAuctionFixture.registrationForm());

	}

}
