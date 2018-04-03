package com.prokarma.opa.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.web.domain.BidVo;


@Component
public class BidConverterImpl implements BidConverter {

	@Override
	public BidDto toDto(BidVo bidVo) {
		BidDto bidDto = new BidDto();
		bidDto.setId(bidVo.getId());
		bidDto.setProductId(bidVo.getProductId());
		bidDto.setAmount(bidVo.getAmount());
		return bidDto;
	}

	@Override
	public BidVo toVo(BidDto bidDto) {
		BidVo bidVo = new BidVo();

		bidVo.setId(bidDto.getId());
		bidVo.setAmount(bidDto.getAmount());
		bidVo.setProductId(bidDto.getProductId());
		bidVo.setBidderEmail(bidDto.getBidderEmail());

		return bidVo;
	}

	@Override
	public List<BidVo> toVoList(List<BidDto> bidDtos) {
		List<BidVo> bidVos = new ArrayList<>();

		for (BidDto bidDto : bidDtos)
			bidVos.add(toVo(bidDto));

		return bidVos;

	}

	@Override
	public List<BidDto> toDtoList(List<BidVo> bidVos) {
		List<BidDto> bidDtos = new ArrayList<>();

		for (BidVo bidVo : bidVos)
			bidDtos.add(toDto(bidVo));

		return bidDtos;
	}

}
