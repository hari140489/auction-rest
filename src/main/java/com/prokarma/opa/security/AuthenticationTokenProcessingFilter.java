package com.prokarma.opa.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.prokarma.opa.repository.model.UserDto;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private final CustomUserDetailsServiceImpl customUserDetailsService;

	public AuthenticationTokenProcessingFilter(CustomUserDetailsServiceImpl customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = getAsHttpRequest(request);
		String accessToken = extractAccessTokenFromRequest(httpRequest);
		if (StringUtils.isNotBlank(accessToken)) {
			UserDto user = customUserDetailsService.findUserByAccessToken(accessToken);
			if (null != user) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}
		return (HttpServletRequest) request;
	}

	private String extractAccessTokenFromRequest(HttpServletRequest httpRequest) {

		String authToken = httpRequest.getHeader("X-Access-Token");

		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}
		return authToken;
	}
}
