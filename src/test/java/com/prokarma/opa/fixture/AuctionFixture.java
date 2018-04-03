package com.prokarma.opa.fixture;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.web.domain.AuctionVo;

public class AuctionFixture {
	
	public static AuctionDto auctionDto() {
		AuctionDto auctionDto= new AuctionDto();
		
		auctionDto.setProduct_id(1);
		auctionDto.setActive("y");
		auctionDto .setName("samsung");
		auctionDto.setType("moble");
		auctionDto.setMaxBidAmount(50000);
		auctionDto.setImage(new byte[] {0x14, 0x20, 0x33});
		auctionDto.setOwner_email("saihussain666@gmail.com");
	
		return auctionDto;
		
	}
	
	public static AuctionVo auctionVo() {
		AuctionVo auctionVo= new AuctionVo();
		
		auctionVo.setProduct_id(1);
		auctionVo.setActive("y");
		auctionVo .setName("samsung");
		auctionVo.setType("moble");
		auctionVo.setMaxBidAmount(50000);
		auctionVo.setImage(new byte[] {0x14, 0x20, 0x33});
		auctionVo.setEmail("saihussain666@gmail.com");
	
		return auctionVo;
		
	}
	
	

}
