package com.prokarma.opa.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.prokarma.opa.repository.model.Role;
import com.prokarma.opa.security.AuthenticationTokenProcessingFilter;
import com.prokarma.opa.security.CustomUserDetailsServiceImpl;
import com.prokarma.opa.security.UnauthorizedEntryPoint;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private UnauthorizedEntryPoint unauthorizedEntryPoint;

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(unauthorizedEntryPoint)
		    .and()
			.addFilterBefore(new AuthenticationTokenProcessingFilter(userDetailsService), BasicAuthenticationFilter.class)
			.authorizeRequests()			
			.antMatchers("/opa-ui-rest-online-product-auction/user/authenticate").permitAll()
			.antMatchers("/opa-ui-rest-online-product-auction/user/register").permitAll()
		   	.antMatchers(HttpMethod.GET,"/opa-ui-rest-online-product-auction/auction/active-auctions").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.POST,"/opa-ui-rest-online-product-auction/auction/create-auction").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.GET, "/opa-ui-rest-online-product-auction/auction/view-auction").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.GET, "/opa-ui-rest-online-product-auction/auction/completed-auction").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.GET,"/opa-ui-rest-online-product-auction/auction/get-auction-max-bid/*").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.POST,"/opa-ui-rest-online-product-auction/bid/create-bid").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.GET,"/opa-ui-rest-online-product-auction/bid/view-all-bids").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.GET,"/opa-ui-rest-online-product-auction/bid/my-active-bids").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.GET,"/opa-ui-rest-online-product-auction/bid/view-my-completed-bids").hasRole(Role.USER.toString())
		   	.antMatchers(HttpMethod.GET,"/opa-ui-rest-online-product-auction/bid/get-bid-by-product-for-user/*").hasRole(Role.USER.toString());
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
			auth
				.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}