package com.prokarma.opa.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.web.domain.AuctionVo;

@RunWith(MockitoJUnitRunner.class)

public class AuctionConverterImplTest {
	
	AuctionConverter auctionConverter;
	
	@Before
   	public void setUp() throws Exception {
		auctionConverter = new AuctionConverterImpl();
   	}
	
	 @Test
		public void testAuctionVoGetDto() {
	    	AuctionVo  auctionVo=OnlineProductAuctionFixture.auctionVO();
	    	AuctionDto auctionDto=auctionConverter.toDto(auctionVo);
	    	
	    	assertEquals(auctionVo.getProduct_id(), auctionDto.getProduct_id());
	    	assertEquals(auctionVo.getActive(), auctionDto.getActive());
	    	assertEquals(auctionVo.getName(), auctionDto.getName());
	    	assertEquals(auctionVo.getType(), auctionDto.getType());
	    	assertEquals(auctionVo.getMaxBidAmount(), auctionDto.getMaxBidAmount());
	    	assertArrayEquals(auctionVo.getImage(), auctionDto.getImage());
	    	assertEquals(auctionVo.getEmail(), auctionDto.getOwner_email());
	    }
	 

	 @Test
	 public void testAuctionVoGeVo() {
	    	AuctionDto  auctionDto=OnlineProductAuctionFixture.auctionDto();
	    	AuctionVo auctionVo=auctionConverter.toVo(auctionDto);
	    	
	    	assertEquals(auctionDto.getProduct_id(), auctionVo.getProduct_id());
	    	assertEquals(auctionDto.getActive(), auctionVo.getActive());
	    	assertEquals(auctionDto.getName(), auctionVo.getName());
	    	assertEquals(auctionDto.getType(), auctionVo.getType());
	    	assertEquals(auctionDto.getMin_bid_amount(), auctionVo.getPrice());
	    	assertArrayEquals(auctionDto.getImage(), auctionVo.getImage());
	    	assertEquals(auctionDto.getOwner_email(),auctionVo.getEmail());
	    }
	

	 @Test
		public void testListAuctionDto() {
		  
		  List<AuctionVo>  auctionVo=OnlineProductAuctionFixture.auctionVos();
	    	List<AuctionDto> auctionDto=auctionConverter.toDtoList(auctionVo);
	    	
	    	assertEquals(auctionVo.get(0).getName(), auctionDto.get(0).getName());
	    	assertEquals(auctionVo.get(0).getType(), auctionDto.get(0).getType());
	    	assertEquals(auctionVo.get(0).getPrice(), auctionDto.get(0).getMin_bid_amount());
	    	assertEquals(auctionVo.get(0).getActive(), auctionDto.get(0).getActive());
	    	assertArrayEquals(auctionVo.get(0).getImage(), auctionDto.get(0).getImage());
	    	assertEquals(auctionVo.get(0).getEmail(), auctionDto.get(0).getOwner_email());
	  }
	  

	 @Test
		public void testListAuctionVo() {
		  
		  List<AuctionDto>  auctionDto=OnlineProductAuctionFixture.auctionDtos();
	    	List<AuctionVo> auctionVo=auctionConverter.toVoList(auctionDto);
	    	
	    	assertEquals( auctionDto.get(0).getName(),auctionVo.get(0).getName());
	    	assertEquals(auctionDto.get(0).getType(),auctionVo.get(0).getType());
	    	assertEquals(auctionDto.get(0).getMin_bid_amount(),auctionVo.get(0).getPrice());
	    	assertEquals(auctionDto.get(0).getActive(), auctionVo.get(0).getActive());
	    	assertArrayEquals(auctionDto.get(0).getImage(),auctionVo.get(0).getImage());
	    	assertEquals(auctionDto.get(0).getOwner_email(), auctionVo.get(0).getEmail());
	  }
	 
	 


	
	}


