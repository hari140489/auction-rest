package com.prokarma.opa.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.prokarma.opa.exception.InvalidAuctionSearchException;
import com.prokarma.opa.exception.InvalidCreateAuctionException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.security.WithMockCustomUser;
import com.prokarma.opa.service.AuctionService;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.BuyerBidVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;
import com.prokarma.opa.web.domain.Error;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.prokarma.opa.exception.InvalidCreateAuctionException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.security.WithMockCustomUser;
import com.prokarma.opa.service.AuctionService;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;
import com.prokarma.opa.web.domain.Error;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuctionControllerTest {

	private MockMvc mvc;

	private AuctionService auctionService;

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	private AuctionController auctionController;

	@Before
	public void init() {
		auctionService = Mockito.mock(AuctionService.class);
		mvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(springSecurity()).build();
		ReflectionTestUtils.setField(auctionController, "auctionService", auctionService);
	}

	@Test
	@WithMockCustomUser
	public void testCreateAuction_whenUserCreatedSuccessfully_shouldReturnHttpStatusCreated() throws Exception {
		String auctionVoJson = "{" + "\"name\": \" Name\"," + "\"type\": \" type\"," + "\"price\": 15,"
				+ "\"email\": \"email\"" + "}";
		doNothing().when(auctionService).addAuction(any(AuctionVo.class));
		mvc.perform(post("/auction/create-auction").contentType(MediaType.APPLICATION_JSON).content(auctionVoJson))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.message", is("User created successfully")))
				.andExpect(jsonPath("$.errors").doesNotExist());
	}

	@Test
	public void testCreateAuction_withNullUserDetailsObject_shouldReturnHttpStatusUnauthorized() throws Exception {
		String auctionVoJson = "{" + "\"name\": \" Name\"," + "\"type\": \" type\"," + "\"price\": 15,"
				+ "\"email\": \"email\"" + "}";
		mvc.perform(post("/auction/create-auction").contentType(MediaType.APPLICATION_JSON).content(auctionVoJson))
				.andExpect(status().isUnauthorized());
	}

	
	@Test
	@WithMockCustomUser
	public void testCreateAuction_withAuctionServiceThrowingExceptionWithErrors_shouldReturnHttpStatusBadRequest()
			throws Exception {
		String auctionVoJson = "{" + "\"name\": \" Name\"," + "\"type\": \" type\"," + "\"price\": 15,"
				+ "\"email\": \"email\"" + "}";
		List<Error> errors = new ArrayList<>();
		errors.add(new Error("expiredate", "Invalid expiration date"));
		errors.add(new Error("price", "Invalid price"));
		doThrow(new InvalidCreateAuctionException(errors)).when(auctionService).addAuction(any(AuctionVo.class));
		mvc.perform(post("/auction/create-auction").contentType(MediaType.APPLICATION_JSON).content(auctionVoJson))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Error validating Create Auction")))
				.andExpect(jsonPath("$.errors.size()", is(errors.size())))
				.andExpect(jsonPath("$.errors[0].field", is(errors.get(0).getField())))
				.andExpect(jsonPath("$.errors[0].message", is(errors.get(0).getMessage())))
				.andExpect(jsonPath("$.errors[1].field", is(errors.get(1).getField())))
				.andExpect(jsonPath("$.errors[1].message", is(errors.get(1).getMessage())));
	}

	@Test
	@WithMockCustomUser
	public void testViewAuctions_whenFetchedAuctionsByNameAndType_RespondWithStatusOk() throws Exception {

		List<AuctionVo> auctionVos = OnlineProductAuctionFixture.auctionVos();
		when(auctionService.viewProducts(any(), any(), any())).thenReturn(auctionVos);
		mvc.perform(get("/auction/view-auctions?type=Mobile").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)))
				.andExpect(jsonPath("$[0].name", is(auctionVos.get(0).getName())))
				.andExpect(jsonPath("$[0].type", is(auctionVos.get(0).getType())))
				.andExpect(jsonPath("$[0].price", is(auctionVos.get(0).getPrice())))
				.andExpect(jsonPath("$[0].active", is(auctionVos.get(0).getActive())));
	}
	
	@Test
	@WithMockCustomUser
	public void testViewAuctions_throwsInvalidAuctionSearchException_whenProductTypeAndNameAreNull() throws Exception {
		doThrow(new InvalidAuctionSearchException()).when(auctionService).viewProducts(any(),any(),any());
		mvc.perform(get("/auction/view-auctions?type=Mobile").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is("Both name and type cannot be empty")));
	}
	
	@Test
    public void testViewAuctions_whenUserNotLoggedIn_thenRespondWithUnauthorizedMessage() throws Exception {
        mvc.perform(get("/auction/view-auctions?type=Mobile")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message", nullValue()))
            .andExpect(jsonPath("$.errors[0].message", is("User is not logged in")));
    }


	@Test
	@WithMockCustomUser
	public void testCompletedAuction() throws Exception {

		List<CompletedAuctionVo> completedAuction = new ArrayList<>();

		CompletedAuctionVo completedAuctionVo = new CompletedAuctionVo();
		completedAuctionVo.setName("samsung");
		completedAuctionVo.setMinBidAmount(10000);
		completedAuctionVo.setBidderName("satish");
		completedAuctionVo.setProductId(5);
		completedAuctionVo.setMaxBidAmount(110000);

		CompletedAuctionVo completedAuctionVo1 = new CompletedAuctionVo();

		completedAuctionVo1.setName("samsung s9");
		completedAuctionVo1.setMinBidAmount(100000);
		completedAuctionVo1.setBidderName("sai");
		completedAuctionVo1.setProductId(10);
		completedAuctionVo1.setMaxBidAmount(1100000);

		completedAuction.add(completedAuctionVo);
		completedAuction.add(completedAuctionVo1);

		when(auctionService.retrieveCompletedAuctions(any())).thenReturn(completedAuction);

		mvc.perform(get("/auction/completed-auction")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(completedAuction.get(0).getName())))
				.andExpect(jsonPath("$[0].minBidAmount", is(completedAuction.get(0).getMinBidAmount())))
				.andExpect(jsonPath("$[0].bidderName", is(completedAuction.get(0).getBidderName())))
				.andExpect(jsonPath("$[0].maxBidAmount", is(completedAuction.get(0).getMaxBidAmount())))
				.andExpect(jsonPath("$[1].name", is(completedAuction.get(1).getName())))
				.andExpect(jsonPath("$[1].minBidAmount", is(completedAuction.get(1).getMinBidAmount())))
				.andExpect(jsonPath("$[1].bidderName", is(completedAuction.get(1).getBidderName())))
				.andExpect(jsonPath("$[1].maxBidAmount", is(completedAuction.get(1).getMaxBidAmount())));
	}
	
	@Test
	@WithMockCustomUser
	public void testViewActiveAuctions_whenLoggedInUserSelectingMyActiveAuctions_thenDisplayHisActiveAuctions() throws Exception {	
		List<AuctionVo> auctionVos=new ArrayList<>();
		AuctionVo auctionVo =new AuctionVo();
		auctionVo.setName("My Book");
		auctionVo.setType("Book");
		auctionVo.setPrice(20);
		auctionVo.setActive("Y");
		
		auctionVos.add(auctionVo);
		when(auctionService.findMyActiveAuctions(any())).thenReturn(auctionVos);		
		mvc.perform(get("/auction/active-auctions")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(1)))
		.andExpect(jsonPath("$[0].name", is(auctionVos.get(0).getName())))
		.andExpect(jsonPath("$[0].type", is(auctionVos.get(0).getType())))
		.andExpect(jsonPath("$[0].price", is(auctionVos.get(0).getPrice())))
		.andExpect(jsonPath("$[0].active", is(auctionVos.get(0).getActive())));
	}

}
