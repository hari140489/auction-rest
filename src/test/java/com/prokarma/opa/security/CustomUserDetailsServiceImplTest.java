package com.prokarma.opa.security;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prokarma.opa.exception.TokenNotFoundException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.AccessTokenRepository;
import com.prokarma.opa.repository.UserRepository;
import com.prokarma.opa.repository.model.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private AccessTokenRepository accessTokenRepository;

	private UserDetailsService userDetailService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userDetailService = new CustomUserDetailsServiceImpl(userRepository, accessTokenRepository);
	}

	@Test
	public void testloadUserByUsername_whenUserFound_thenReturnUser() {
		UserDto expected = OnlineProductAuctionFixture.userDto();
		when(userRepository.findByEmail(Matchers.anyString())).thenReturn(expected);
		UserDto user = (UserDto) userDetailService.loadUserByUsername("dummy@email.com");
		assertEquals(expected.getEmail(), user.getEmail());
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsername_whenUserNotFound_thenThrowUsernameNotFoundException() {
		UserDto expected = OnlineProductAuctionFixture.userDto();
		when(userRepository.findByEmail(Matchers.anyString())).thenReturn(null);
		UserDto user = (UserDto) userDetailService.loadUserByUsername("dummy@email.com");
	}
	
	@Test
	public void testFindUserByAccessToken_whenUserFound_returnUserDto() {
		when(accessTokenRepository.findByToken(Matchers.any())).thenReturn(OnlineProductAuctionFixture.accessTokenDto());
		UserDto expected = OnlineProductAuctionFixture.userDto();
		when(userRepository.findByEmail(Matchers.anyString())).thenReturn(expected);
		UserDto user = ((CustomUserDetailsServiceImpl) userDetailService).findUserByAccessToken("slkfdsalfhwp");
		assertEquals(expected.getEmail(), user.getEmail());
	}
	
	@Test(expected = TokenNotFoundException.class)
	public void testFindUserByAccessToken_whenUserNotFound_thenThrowTokenNotFoundException() {
		when(accessTokenRepository.findByToken(Matchers.any())).thenReturn(null);
		((CustomUserDetailsServiceImpl) userDetailService).findUserByAccessToken("slkfdsalfhwp");
		
	}

}
