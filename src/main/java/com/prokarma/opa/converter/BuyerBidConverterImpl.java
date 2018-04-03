package com.prokarma.opa.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.prokarma.opa.repository.model.BuyerBidDto;
import com.prokarma.opa.web.domain.BuyerBidVo;


@Component
public class BuyerBidConverterImpl implements BuyerBidConverter {

	@Override
	public BuyerBidDto toDto(BuyerBidVo buyerBidVo) {
		
		BuyerBidDto buyerBidDto = new BuyerBidDto();
		buyerBidDto.setActualAmount(buyerBidVo.getActualAmount());
		buyerBidDto.setBidderName(buyerBidVo.getBidderName());
		buyerBidDto.setBidId(buyerBidVo.getBidId());
		buyerBidDto.setBuyerBidAmount(buyerBidVo.getBuyerBidAmount());
		buyerBidDto.setImage(buyerBidVo.getImage());
		buyerBidDto.setMaximumBidAmount(buyerBidVo.getMaximumBidAmount());
		buyerBidDto.setProductId(buyerBidVo.getProductId());
		buyerBidDto.setProductName(buyerBidVo.getProductName());
		buyerBidDto.setBidCreatedDate(buyerBidVo.getBidCreatedDate());
		return buyerBidDto;
	}

	@Override
	public BuyerBidVo toVo(BuyerBidDto buyerBidDto) {
		BuyerBidVo buyerBidVo=new BuyerBidVo();
		buyerBidVo.setActualAmount(buyerBidDto.getActualAmount());
		buyerBidVo.setBidderName(buyerBidDto.getBidderName());
		buyerBidVo.setBidId(buyerBidDto.getBidId());
		buyerBidVo.setBuyerBidAmount(buyerBidDto.getBuyerBidAmount());
		buyerBidVo.setImage(buyerBidDto.getImage());
		buyerBidVo.setMaximumBidAmount(buyerBidDto.getMaximumBidAmount());
		buyerBidVo.setProductId(buyerBidDto.getProductId());
		buyerBidVo.setProductName(buyerBidDto.getProductName());
		buyerBidVo.setBidCreatedDate(buyerBidDto.getBidCreatedDate());
		return buyerBidVo;
	}

	@Override
	public List<BuyerBidVo> toVoList(List<BuyerBidDto> buyerBidDto) {
		List<BuyerBidVo>  buyerBidVo = new ArrayList<>();

		for (BuyerBidDto buyerBidDtos : buyerBidDto)
			buyerBidVo.add(toVo(buyerBidDtos));

		return buyerBidVo;
	}

	@Override
	public List<BuyerBidDto> toDtoList(List<BuyerBidVo> buyerBidVo) {
		List<BuyerBidDto> buyerBidDto = new ArrayList<>();

		for (BuyerBidVo buyerBidVos : buyerBidVo)
			buyerBidDto.add(toDto(buyerBidVos));

		return buyerBidDto;
	}

}
