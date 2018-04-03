package com.prokarma.opa.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
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

import com.prokarma.opa.exception.InvalidBidException;
import com.prokarma.opa.exception.ProductNotFoundException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.security.WithMockCustomUser;
import com.prokarma.opa.service.BidService;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.BidVo;
import com.prokarma.opa.web.domain.BuyerBidVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidControllerTest {
	
	private MockMvc mvc;

	private BidService bidService;
	
	@Autowired
	private WebApplicationContext webAppContext;
	
	@Autowired
	private BidController bidController;
	
	@Before
	public void init() {
		bidService = Mockito.mock(BidService.class);
		mvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(
			springSecurity()).build();
		ReflectionTestUtils.setField(bidController, "bidService",
			bidService);
	}
	
	@Test
	@WithMockCustomUser
	public void testCreateBid_whenBidIsValid_RespondWithStatusCreated() throws Exception {
		String createBidJson = OnlineProductAuctionFixture.createBidJson();
		
		doNothing().when(bidService).createBid(any(BidVo.class), any(UserDto.class));
		
		mvc.perform(post("/bid/create-bid")
				.contentType(MediaType.APPLICATION_JSON).content(createBidJson))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message", is("Bid created successfully")))
			.andExpect(jsonPath("$.errors", nullValue()));
	}
	
	@Test
	@WithMockCustomUser
	public void testCreateBid_whenBidIsInvalid_thenRespondWithInvalidBidMessage() throws Exception {
		String createBidJson = OnlineProductAuctionFixture.createBidJson();
		
		doThrow(new InvalidBidException("Some reason")).when(bidService).createBid(any(BidVo.class), any(UserDto.class));
		
		mvc.perform(post("/bid/create-bid")
				.contentType(MediaType.APPLICATION_JSON).content(createBidJson))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", is("Invalid bid. Some reason")))
			.andExpect(jsonPath("$.errors", nullValue()));
	}
	
	@Test
	public void testCreateBid_whenUserNotLoggedIn_thenRespondWithUnauthorizedMessage() throws Exception {
		String createBidJson = OnlineProductAuctionFixture.createBidJson();
		
		mvc.perform(post("/bid/create-bid")
				.contentType(MediaType.APPLICATION_JSON).content(createBidJson))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.message", nullValue()))
			.andExpect(jsonPath("$.errors[0].message", is("User is not logged in")));
	}
	
	@Test
	@WithMockCustomUser
	public void testViewBids_whenBidsExist() throws Exception {
		int productId = 4;
		AuctionVo auctionVo = new AuctionVo();
		auctionVo.setPrice(1000);
		auctionVo.setEmail("abc@prokarma.com");
		when(bidService.getBidsByProduct(productId)).thenReturn(auctionVo);
		
		mvc.perform(get("/bid/view-all-bids?productId=4"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.price", is(1000)))
			.andExpect(jsonPath("$.email", is("abc@prokarma.com")));
	}
	
	@Test
	@WithMockCustomUser
	public void testViewBids_whenProductExist() throws Exception {

		int productId = 1;
		
		when(bidService.getBidsByProduct(productId)).thenReturn(OnlineProductAuctionFixture.auctionVo());

		mvc.perform(get("/bid/view-all-bids?productId=1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.active", is("Y")))
			.andExpect(jsonPath("$.bids[0].amount", is(100001)))
			.andExpect(jsonPath("$.bids[0].bidderEmail", is("xyz@gmail.com")))
			.andExpect(jsonPath("$.bids[0].id", is(3)))
			.andExpect(jsonPath("$.bids[0].productId", is(1)))
			.andExpect(jsonPath("$.email", is("abc@gmail.com")))
			.andExpect(jsonPath("$.name", is("iPhone X")))
			.andExpect(jsonPath("$.price", is(100000)))
			.andExpect(jsonPath("$.product_id", is(1)))
			.andExpect(jsonPath("$.type", is("Mobile")));
	}
	
	@Test
	@WithMockCustomUser
	public void testViewBids_whenProductDoesNotExist() throws Exception {
		
		int productId = 1;
		doThrow(new ProductNotFoundException()).when(bidService).getBidsByProduct(productId);
		
		mvc.perform(get("/bid/view-all-bids?productId=1"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message",is("Product not found")))
			.andExpect(jsonPath("$.errors",nullValue()));
	}
	
	@Test
	public void testViewBids_whenUserNotLoggedIn_thenRespondWithUnauthorizedMessage() throws Exception {
		
		mvc.perform(get("/bid/view-all-bids?productId=1"))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.message", nullValue()))
			.andExpect(jsonPath("$.errors[0].message", is("User is not logged in")));
	}
	
	@Test
	@WithMockCustomUser
	public void testMyActiveBids() throws Exception {
		List<BuyerBidVo> buyerBidVos = OnlineProductAuctionFixture.buyerBidVoList();
		
		when(bidService.getActiveBidsByBuyerEmail(any())).thenReturn(buyerBidVos);
		
		mvc.perform(get("/bid/my-active-bids")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(buyerBidVos.size())))
			.andExpect(jsonPath("$[0].productId", is((int) buyerBidVos.get(0).getProductId())))
			.andExpect(jsonPath("$[0].productName", is(buyerBidVos.get(0).getProductName())))
			.andExpect(jsonPath("$[0].bidId", is((int) buyerBidVos.get(0).getBidId())))
			.andExpect(jsonPath("$[0].bidderName", is(buyerBidVos.get(0).getBidderName())))
			.andExpect(jsonPath("$[0].image", is(Base64.getEncoder().encodeToString(buyerBidVos.get(0).getImage()))))
			.andExpect(jsonPath("$[0].actualAmount", is(buyerBidVos.get(0).getActualAmount())))
			.andExpect(jsonPath("$[0].buyerBidAmount", is(buyerBidVos.get(0).getBuyerBidAmount())))
			.andExpect(jsonPath("$[0].maximumBidAmount", is(buyerBidVos.get(0).getMaximumBidAmount())))
			.andExpect(jsonPath("$[1].productId", is((int) buyerBidVos.get(1).getProductId())))
			.andExpect(jsonPath("$[1].productName", is(buyerBidVos.get(1).getProductName())))
			.andExpect(jsonPath("$[1].bidId", is((int) buyerBidVos.get(1).getBidId())))
			.andExpect(jsonPath("$[1].bidderName", is(buyerBidVos.get(1).getBidderName())))
			.andExpect(jsonPath("$[1].image", is(Base64.getEncoder().encodeToString(buyerBidVos.get(1).getImage()))))
			.andExpect(jsonPath("$[1].actualAmount", is(buyerBidVos.get(1).getActualAmount())))
			.andExpect(jsonPath("$[1].buyerBidAmount", is(buyerBidVos.get(1).getBuyerBidAmount())))
			.andExpect(jsonPath("$[1].maximumBidAmount", is(buyerBidVos.get(1).getMaximumBidAmount())))
			.andExpect(jsonPath("$[2].productId", is((int) buyerBidVos.get(2).getProductId())))
			.andExpect(jsonPath("$[2].productName", is(buyerBidVos.get(2).getProductName())))
			.andExpect(jsonPath("$[2].bidId", is((int) buyerBidVos.get(2).getBidId())))
			.andExpect(jsonPath("$[2].bidderName", is(buyerBidVos.get(2).getBidderName())))
			.andExpect(jsonPath("$[2].image", is(Base64.getEncoder().encodeToString(buyerBidVos.get(2).getImage()))))
			.andExpect(jsonPath("$[2].actualAmount", is(buyerBidVos.get(2).getActualAmount())))
			.andExpect(jsonPath("$[2].buyerBidAmount", is(buyerBidVos.get(2).getBuyerBidAmount())))
			.andExpect(jsonPath("$[2].maximumBidAmount", is(buyerBidVos.get(2).getMaximumBidAmount())));
	}
	
	@Test
	public void testMyActiveBids_whenUserNotLoggedIn_thenRespondWithUnauthorizedMessage() throws Exception {
		
		mvc.perform(get("/bid/my-active-bids"))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.message", nullValue()))
			.andExpect(jsonPath("$.errors[0].message", is("User is not logged in")));
	}
	
	@Test
	@WithMockCustomUser
	public void testMyCompletedBids() throws Exception {
		List<BuyerBidVo> buyerBidVos = OnlineProductAuctionFixture.buyerBidVos();
		
		when(bidService.findMyCompletedBids(any())).thenReturn(buyerBidVos);
		
		mvc.perform(get("/bid/view-my-completed-bids"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].productName", is(buyerBidVos.get(0).getProductName())))
		.andExpect(jsonPath("$.[0].actualAmount", is(buyerBidVos.get(0).getActualAmount())))
		.andExpect(jsonPath("$.[0].bidderName", is(buyerBidVos.get(0).getBidderName())))
		.andExpect(jsonPath("$.[0].maximumBidAmount", is(buyerBidVos.get(0).getMaximumBidAmount())))
		.andExpect(jsonPath("$.[1].productName", is(buyerBidVos.get(1).getProductName())))
		.andExpect(jsonPath("$.[1].actualAmount", is(buyerBidVos.get(1).getActualAmount())))
		.andExpect(jsonPath("$.[1].bidderName", is(buyerBidVos.get(1).getBidderName())))
		.andExpect(jsonPath("$.[1].maximumBidAmount", is(buyerBidVos.get(1).getMaximumBidAmount())));
		
	}
	
	@Test
    public void testCompletedBids_whenUserNotLoggedIn_thenRespondWithUnauthorizedMessage() throws Exception {
        mvc.perform(get("/bid/view-my-completed-bids"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message", nullValue()))
            .andExpect(jsonPath("$.errors[0].message", is("User is not logged in")));
    }
	
	
}
