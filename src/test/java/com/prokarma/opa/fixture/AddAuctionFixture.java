package com.prokarma.opa.fixture;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.web.domain.AuctionVo;

public class AddAuctionFixture {
	
	private AddAuctionFixture() {
		throw new AssertionError("Cannot create instance");
		
		
	}
	  public static AuctionVo auctionVo() {
		  AuctionVo auctionVo=new AuctionVo();
		  auctionVo.setName("samsung");
		  auctionVo.setType("mobile");
		  auctionVo.setEmail("nishal@prokarma.com");
		 auctionVo.setImage(new byte[] {0x14, 0x20, 0x33});
		 auctionVo.setPrice(5000);
		return auctionVo;
		  
	  }
	  
	  public static AuctionDto auctionDto() {
		  AuctionDto auctionDto=new AuctionDto();
		  auctionDto.setName("samsung");
		  auctionDto.setType("mobile");
		  auctionDto.setOwner_email("nishal@prokarma.com");
		  auctionDto.setImage(new byte[] {0x14, 0x20, 0x33});
		  auctionDto.setMin_bid_amount(5000);
		return auctionDto;
		  
	  }
	  

}
