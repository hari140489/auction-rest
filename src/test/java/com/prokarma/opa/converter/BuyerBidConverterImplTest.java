package com.prokarma.opa.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.BuyerBidDto;
import com.prokarma.opa.web.domain.BuyerBidVo;

@RunWith(MockitoJUnitRunner.class)
public class BuyerBidConverterImplTest {
	
	BuyerBidConverter buyerBidConverter;
	
	@Before
   	public void setUp() throws Exception {
		buyerBidConverter = new BuyerBidConverterImpl();
   	}
	
	  @Test
		public void testBuyerBidGetDto() {
	    	BuyerBidVo  buyerBidVo=OnlineProductAuctionFixture.buyerBidVo();
	    	BuyerBidDto buyerBidDto=buyerBidConverter.toDto(buyerBidVo);
	    	
	    	assertEquals(buyerBidVo.getActualAmount(), buyerBidDto.getActualAmount());
	    	assertEquals(buyerBidVo.getBidCreatedDate(), buyerBidDto.getBidCreatedDate());
	    	assertEquals(buyerBidVo.getBidderName(), buyerBidDto.getBidderName());
	    	assertEquals(buyerBidVo.getBidId(), buyerBidDto.getBidId());
	    	assertEquals(buyerBidVo.getBuyerBidAmount(), buyerBidDto.getBuyerBidAmount());
	    	assertEquals(buyerBidVo.getMaximumBidAmount(), buyerBidDto.getMaximumBidAmount());
	    	assertEquals(buyerBidVo.getProductId(), buyerBidDto.getProductId());
	    }
	  
	  @Test
		public void testBuyerBidGetVo() {
		  
		  BuyerBidDto  buyerBidDto=OnlineProductAuctionFixture.buyerBidDto();
	    	BuyerBidVo buyerBidVo=buyerBidConverter.toVo(buyerBidDto);
	    	
	    	assertEquals(buyerBidDto.getActualAmount(), buyerBidVo.getActualAmount());
	    	assertEquals(buyerBidDto.getBidCreatedDate(), buyerBidVo.getBidCreatedDate());
	    	assertEquals(buyerBidDto.getBidderName(), buyerBidVo.getBidderName());
	    	assertEquals(buyerBidDto.getBidId(), buyerBidVo.getBidId());
	    	assertEquals(buyerBidDto.getBuyerBidAmount(), buyerBidVo.getBuyerBidAmount());
	    	assertEquals(buyerBidDto.getMaximumBidAmount(), buyerBidVo.getMaximumBidAmount());
	    	assertEquals(buyerBidDto.getProductId(), buyerBidVo.getProductId());
	    }
	  
	  @Test
		public void testListBuyerBidGetDto() {
		  
		  List<BuyerBidVo>  buyerBidVo=OnlineProductAuctionFixture.buyerBidVoList();
	    	List<BuyerBidDto> buyerBidDto=buyerBidConverter.toDtoList(buyerBidVo);
	    	
	    	assertEquals(buyerBidVo.get(0).getActualAmount(), buyerBidDto.get(0).getActualAmount());
	    	assertEquals(buyerBidVo.get(0).getBidCreatedDate(), buyerBidDto.get(0).getBidCreatedDate());
	    	assertEquals(buyerBidVo.get(0).getBidderName(), buyerBidDto.get(0).getBidderName());
	    	assertEquals(buyerBidVo.get(0).getBidId(), buyerBidDto.get(0).getBidId());
	    	assertEquals(buyerBidVo.get(0).getBuyerBidAmount(), buyerBidDto.get(0).getBuyerBidAmount());
	    	assertEquals(buyerBidVo.get(0).getMaximumBidAmount(), buyerBidDto.get(0).getMaximumBidAmount());
	    	assertEquals(buyerBidVo.get(0).getProductId(), buyerBidDto.get(0).getProductId());
	    	assertEquals(buyerBidVo.get(0).getImage(), buyerBidDto.get(0).getImage());
	    	assertEquals(buyerBidVo.get(1).getActualAmount(), buyerBidDto.get(1).getActualAmount());
	    	assertEquals(buyerBidVo.get(1).getBidCreatedDate(), buyerBidDto.get(1).getBidCreatedDate());
	    	assertEquals(buyerBidVo.get(1).getBidderName(), buyerBidDto.get(1).getBidderName());
	    	assertEquals(buyerBidVo.get(1).getBidId(), buyerBidDto.get(1).getBidId());
	    	assertEquals(buyerBidVo.get(1).getBuyerBidAmount(), buyerBidDto.get(1).getBuyerBidAmount());
	    	assertEquals(buyerBidVo.get(1).getMaximumBidAmount(), buyerBidDto.get(1).getMaximumBidAmount());
	    	assertEquals(buyerBidVo.get(1).getProductId(), buyerBidDto.get(1).getProductId());
	    	assertEquals(buyerBidVo.get(1).getImage(), buyerBidDto.get(1).getImage());
	  
	  }
	  
	  @Test
		public void testListBuyerBidGetVo() {
		  
		  List<BuyerBidDto> buyerBidDto=OnlineProductAuctionFixture.buyerBidDtoList();
		  
	    	List<BuyerBidVo> buyerBidVo=buyerBidConverter.toVoList(buyerBidDto);
	    	
	    	assertEquals(buyerBidDto.get(0).getActualAmount(), buyerBidVo.get(0).getActualAmount());
	    	assertEquals(buyerBidDto.get(0).getBidCreatedDate(), buyerBidVo.get(0).getBidCreatedDate());
	    	assertEquals(buyerBidDto.get(0).getBidderName(), buyerBidVo.get(0).getBidderName());
	    	assertEquals(buyerBidDto.get(0).getBidId(), buyerBidVo.get(0).getBidId());
	    	assertEquals(buyerBidDto.get(0).getBuyerBidAmount(), buyerBidVo.get(0).getBuyerBidAmount());
	    	assertEquals(buyerBidDto.get(0).getMaximumBidAmount(), buyerBidVo.get(0).getMaximumBidAmount());
	    	assertEquals(buyerBidDto.get(0).getProductId(), buyerBidVo.get(0).getProductId());
	    	assertEquals(buyerBidDto.get(0).getImage(), buyerBidVo.get(0).getImage());
	    	assertEquals(buyerBidDto.get(1).getActualAmount(), buyerBidVo.get(1).getActualAmount());
	    	assertEquals(buyerBidDto.get(1).getBidCreatedDate(), buyerBidVo.get(1).getBidCreatedDate());
	    	assertEquals(buyerBidDto.get(1).getBidderName(), buyerBidVo.get(1).getBidderName());
	    	assertEquals(buyerBidDto.get(1).getBidId(), buyerBidVo.get(1).getBidId());
	    	assertEquals(buyerBidDto.get(1).getBuyerBidAmount(), buyerBidVo.get(1).getBuyerBidAmount());
	    	assertEquals(buyerBidDto.get(1).getMaximumBidAmount(), buyerBidVo.get(1).getMaximumBidAmount());
	    	assertEquals(buyerBidDto.get(1).getProductId(), buyerBidVo.get(1).getProductId());
	    	assertEquals(buyerBidDto.get(1).getImage(), buyerBidVo.get(1).getImage());

	  
	  }
	   


}
