package com.prokarma.opa.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.prokarma.opa.security.WithMockCustomUser;
import com.prokarma.opa.service.LoginService;
import com.prokarma.opa.web.domain.LoginReplyVo;
import com.prokarma.opa.web.domain.LoginRequestVo;
@RunWith(SpringRunner.class)
@WebMvcTest(value=LoginController.class,secure=false)
public class LoginControllerTest {
	
	@InjectMocks
	private LoginController loginController;
	
	@MockBean
	private LoginRequestVo userVo;
	
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private LoginService loginService;
	
	  @Before
	    public void setup() {
	        MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	    }
	
	   
	@Test
	@WithMockCustomUser
	public void whenUserCredetialsAreValid_RespondWithStatusOk() throws Exception {
		String loginFormJson = "{"
				+ "\"email\": \"akulakarni@prokarma.com\","
				+ "\"password\": \"anvesh\""
				+ "}";
	
		LoginReplyVo user = new LoginReplyVo();      
		user.setEmail("akulakarni@prokarma.com");
     	user.setToken("UUID.randomUUID()");
     	
		
    when(loginService.authenticateByEmailPassword(any(LoginRequestVo.class))).thenReturn(user);
    
	    mockMvc.perform(post("/user/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON).content(loginFormJson).accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())	       
	            .andExpect(jsonPath("$.email", is("akulakarni@prokarma.com")))
	            .andExpect(jsonPath("$.token", is("UUID.randomUUID()")));  
	    
	    verify(loginService, times(1)).authenticateByEmailPassword(any());
	    verifyNoMoreInteractions(loginService);
	    
	 
	
		
	}

}
