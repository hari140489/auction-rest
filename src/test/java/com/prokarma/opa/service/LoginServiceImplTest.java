package com.prokarma.opa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.prokarma.opa.exception.UnauthenticatedException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.AccessTokenRepository;
import com.prokarma.opa.repository.UserRepository;
import com.prokarma.opa.web.domain.LoginReplyVo;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private AccessTokenRepository accessTokenRepository;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private Authentication authentication;

	private LoginService loginService;

	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		loginService = new LoginServiceImpl(authenticationManager, accessTokenRepository, userRepository);
		OnlineProductAuctionFixture.requestVo();
		
	}

	@Test(expected = UnauthenticatedException.class)
	public void testLoadUserByUserName_whenAuthenticationFails_ThrowUnauthenticatedException() {
		when(authentication.getPrincipal()).thenReturn(null);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication);
		loginService.authenticateByEmailPassword(OnlineProductAuctionFixture.requestVo());
	}

	@Test
	public void testLoadUserByUserName_whenAuthenticationSuccess_ReturnLoginSuccess() {
	
		
		when(authentication.getPrincipal()).thenReturn(OnlineProductAuctionFixture.user());
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		when(accessTokenRepository.createUserToken(OnlineProductAuctionFixture.requestVo().getEmail())).thenReturn("abcde123");
		when(userRepository.findByEmail(OnlineProductAuctionFixture.requestVo().getEmail())).thenReturn(OnlineProductAuctionFixture.user());

		LoginReplyVo loginReplyVo = loginService.authenticateByEmailPassword(OnlineProductAuctionFixture.requestVo());

		assertEquals(OnlineProductAuctionFixture.user().getEmail(), loginReplyVo.getEmail());

	}

}
