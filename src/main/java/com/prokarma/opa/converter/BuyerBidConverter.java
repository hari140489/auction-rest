package com.prokarma.opa.converter;

import java.util.List;

import com.prokarma.opa.repository.model.BuyerBidDto;
import com.prokarma.opa.web.domain.BuyerBidVo;

public interface BuyerBidConverter {
	
	BuyerBidDto toDto(BuyerBidVo buyerBidVo);

	BuyerBidVo toVo(BuyerBidDto buyerBidDto);

	List<BuyerBidVo> toVoList(List<BuyerBidDto> buyerBidDto);

	List<BuyerBidDto> toDtoList(List<BuyerBidVo> buyerBidVo);

}
