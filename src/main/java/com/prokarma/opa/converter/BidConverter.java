package com.prokarma.opa.converter;

import java.util.List;

import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.web.domain.BidVo;


public interface BidConverter {

	BidDto toDto(BidVo bidVo);

	BidVo toVo(BidDto bidDto);

	List<BidVo> toVoList(List<BidDto> bidDtos);

	List<BidDto> toDtoList(List<BidVo> bidVos);
	
	
	
}
