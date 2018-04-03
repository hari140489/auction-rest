package com.prokarma.opa.controllers;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HomeController.class, secure = false)
public class HomeControllerTest {

	@Autowired
	MockMvc mvc;
	
	@Test
	public void testHome() throws Exception {
		mvc.perform(get("/home"))
			.andExpect(status().isOk());
	}

	@Test
	public void testException() throws Exception {
		mvc.perform(get("/home/exception"))
		.andExpect(status().isInternalServerError());
	}

}
