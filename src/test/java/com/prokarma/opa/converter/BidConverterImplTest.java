package com.prokarma.opa.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.web.domain.BidVo;

@RunWith(MockitoJUnitRunner.class)
public class BidConverterImplTest {

	BidConverter bidConverter;

	@Before
	public void init() {
		bidConverter = new BidConverterImpl();

	}

	@Test
	public void testToDto() {
		BidVo bidVo = OnlineProductAuctionFixture.bidVo();
		BidDto bidDto = bidConverter.toDto(bidVo);

		assertEquals(bidVo.getAmount(), bidDto.getAmount());
		assertEquals(bidVo.getId(), bidDto.getId());
		assertEquals(bidVo.getProductId(), bidDto.getProductId());
	}

	@Test
	public void testToVo() {
		BidDto bidDto = OnlineProductAuctionFixture.bidDto();
		BidVo bidVo = bidConverter.toVo(bidDto);

		assertEquals(bidDto.getAmount(), bidVo.getAmount());
		assertEquals(bidDto.getBidderEmail(), bidVo.getBidderEmail());
		assertEquals(bidDto.getId(), bidVo.getId());
		assertEquals(bidDto.getProductId(), bidVo.getProductId());
	}

	@Test
	public void testToVoList() {
		List<BidDto> bidDtos = OnlineProductAuctionFixture.bidDtos();
		List<BidVo> bidVos = bidConverter.toVoList(bidDtos);
		
		assertEquals(bidDtos.get(0).getAmount(), bidVos.get(0).getAmount());
		assertEquals(bidDtos.get(0).getBidderEmail(), bidVos.get(0).getBidderEmail());
		assertEquals(bidDtos.get(0).getId(), bidVos.get(0).getId());
		assertEquals(bidDtos.get(0).getProductId(), bidVos.get(0).getProductId());
		assertEquals(bidDtos.get(1).getAmount(), bidVos.get(1).getAmount());
		assertEquals(bidDtos.get(1).getBidderEmail(), bidVos.get(1).getBidderEmail());
		assertEquals(bidDtos.get(1).getId(), bidVos.get(1).getId());
		assertEquals(bidDtos.get(1).getProductId(), bidVos.get(1).getProductId());
	}

	@Test
	public void testToDtoList() {
		List<BidVo> bidVos = OnlineProductAuctionFixture.bidVos();
		List<BidDto> bidDtos = bidConverter.toDtoList(bidVos);
		
		assertEquals(bidVos.get(0).getAmount(), bidDtos.get(0).getAmount());
		assertEquals(bidVos.get(0).getId(), bidDtos.get(0).getId());
		assertEquals(bidVos.get(0).getProductId(), bidDtos.get(0).getProductId());
		assertEquals(bidVos.get(1).getAmount(), bidDtos.get(1).getAmount());
		assertEquals(bidVos.get(1).getId(), bidDtos.get(1).getId());
		assertEquals(bidVos.get(1).getProductId(), bidDtos.get(1).getProductId());
	}

}
