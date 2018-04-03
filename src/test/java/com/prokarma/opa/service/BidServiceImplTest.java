package com.prokarma.opa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.prokarma.opa.converter.AuctionConverter;
import com.prokarma.opa.converter.BidConverter;
import com.prokarma.opa.converter.BuyerBidConverter;
import com.prokarma.opa.exception.InvalidBidException;
import com.prokarma.opa.exception.ProductNotFoundException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.AuctionRepository;
import com.prokarma.opa.repository.BidRepository;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.BuyerBidDto;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.service.validator.BidValidatorImpl;
import com.prokarma.opa.web.domain.BidVo;
import com.prokarma.opa.web.domain.BuyerBidVo;

@RunWith(MockitoJUnitRunner.class)
public class BidServiceImplTest {
	
	private BidService bidService;

	@Mock
	private BidRepository bidRepository;

	@Mock
	private BidConverter bidConverter;

	@Mock
	private BidValidatorImpl bidValidator;

	@Mock
	private AuctionRepository auctionRepository;

	@Mock
	private AuctionConverter auctionConverter;
	
	@Mock
	private BuyerBidConverter buyerBidConverter;
	
	
	
	private BidVo bidVo;
	private BidDto bidDto;
	private UserDto loggedInUser;
	private BuyerBidVo buyerBidVo;
	private BuyerBidDto buyerBidDto;
	
	@Before
	public void setUp() throws Exception {
		bidService = new BidServiceImpl(bidRepository, bidConverter, bidValidator, auctionRepository, auctionConverter, buyerBidConverter);
		bidVo = OnlineProductAuctionFixture.bidVo();
		loggedInUser = OnlineProductAuctionFixture.userDto();
		bidDto = OnlineProductAuctionFixture.bidDto();
	}

	@Test
	public void testCreateBid_whenValidBid_callSaveBidRepositoryMethod() throws Exception {
		doNothing().when(bidValidator).validate(any(BidVo.class), any(UserDto.class));
		when(bidRepository.getBidForUserByProduct(anyString(), any(long.class))).thenReturn(null);
		when(bidConverter.toDto(any(BidVo.class))).thenReturn(bidDto);
		doNothing().when(bidRepository).saveBid(any(BidDto.class));
		
		bidService.createBid(bidVo, loggedInUser);
		
		ArgumentCaptor<BidDto> argumentCaptor = ArgumentCaptor.forClass(BidDto.class);
		
		verify(bidValidator).validate(any(BidVo.class), any(UserDto.class));
		verify(bidRepository).saveBid(argumentCaptor.capture());
		
		BidDto bidDto = argumentCaptor.getValue();
		
		assertEquals(loggedInUser.getEmail(), bidDto.getBidderEmail());
		
	}
	
	@Test(expected = InvalidBidException.class)
	public void testCreateBid_whenInvalidBid_thenThrowInvalidBidException() throws Exception {
		doThrow(InvalidBidException.class).when(bidValidator).validate(any(BidVo.class), any(UserDto.class));
		bidService.createBid(bidVo, loggedInUser);
	}
	
	@Test
	public void testCreateBid_whenBidExists_thenUpdateBid() {
		BidDto existingBid = new BidDto();
		existingBid.setId(52l);
		existingBid.setAmount(22363);
		
		doNothing().when(bidValidator).validate(any(BidVo.class), any(UserDto.class));
		when(bidRepository.getBidForUserByProduct(anyString(), any(long.class))).thenReturn(existingBid);
		when(bidConverter.toDto(any(BidVo.class))).thenReturn(OnlineProductAuctionFixture.bidDto());
		doNothing().when(bidRepository).updateBid(any(BidDto.class));
		
		bidService.createBid(bidVo, loggedInUser);
		
		ArgumentCaptor<BidDto> argumentCaptor = ArgumentCaptor.forClass(BidDto.class);
		
		verify(bidValidator).validate(any(BidVo.class), any(UserDto.class));
		verify(bidRepository).updateBid(argumentCaptor.capture());
		
		BidDto bidDto = argumentCaptor.getValue();
		
		assertEquals(loggedInUser.getEmail(), bidDto.getBidderEmail());
	}
	
}
