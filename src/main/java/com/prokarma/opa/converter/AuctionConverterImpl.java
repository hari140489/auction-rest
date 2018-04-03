
package com.prokarma.opa.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.CompletedAuctionDto;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;


@Component
public class AuctionConverterImpl implements AuctionConverter {
	
	@Autowired
	BidConverter bidConverter;
	
	@Override
	public AuctionDto toDto(AuctionVo auctionVo) {
		AuctionDto auctionDto = new AuctionDto();
		if(auctionVo.getBids()!= null) {
			auctionDto.setBids(bidConverter.toDtoList(auctionVo.getBids()));
		}
		auctionDto.setActive(auctionVo.getActive());
		auctionDto.setCreated_date(auctionVo.getCreatedDate());
		auctionDto.setExpiration_date(auctionVo.getExpiredate());
		auctionDto.setImage(auctionVo.getImage());
		auctionDto.setMin_bid_amount(auctionVo.getPrice());
		auctionDto.setName(auctionVo.getName());
		auctionDto.setOwner_email(auctionVo.getEmail());
		auctionDto.setProduct_id(auctionVo.getProduct_id());
		auctionDto.setType(auctionVo.getType());
		auctionDto.setMaxBidAmount(auctionVo.getMaxBidAmount());		
		return auctionDto ;
	}

	@Override
	public AuctionVo toVo(AuctionDto auctionDto) {
		AuctionVo auctionVo = new AuctionVo();
		auctionVo.setActive(auctionDto.getActive());
		if(auctionDto.getBids() != null) {
			auctionVo.setBids(bidConverter.toVoList(auctionDto.getBids()));
		}
		auctionVo.setCreatedDate(auctionDto.getCreated_date());
		auctionVo.setExpiredate(auctionDto.getExpiration_date());
		auctionVo.setImage(auctionDto.getImage());
		auctionVo.setPrice(auctionDto.getMin_bid_amount());
		auctionVo.setName(auctionDto.getName());
		auctionVo.setEmail(auctionDto.getOwner_email());
		auctionVo.setProduct_id(auctionDto.getProduct_id());
		auctionVo.setMaxBidAmount(auctionDto.getMaxBidAmount());
		auctionVo.setType(auctionDto.getType());
		auctionVo.setMaxBidAmount(auctionDto.getMaxBidAmount());		
		return auctionVo;
	}

	@Override
	public List<AuctionVo> toVoList(List<AuctionDto> auctionDtos) {
		List<AuctionVo> auctionVos = new ArrayList<>();

		for (AuctionDto auctionDto : auctionDtos)
			auctionVos.add(toVo(auctionDto));

		return auctionVos;

	}

	@Override
	public List<AuctionDto> toDtoList(List<AuctionVo> auctionVos) {
		List<AuctionDto> auctionDtos = new ArrayList<>();

		for (AuctionVo auctionVo : auctionVos)
			auctionDtos.add(toDto(auctionVo));

		return auctionDtos;
	}

	@Override
	public List<CompletedAuctionVo> toCompletedVoList(List<CompletedAuctionDto> completedAuctionDtos) {
		List<CompletedAuctionVo> completedAuctionVos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(completedAuctionDtos)) {
			completedAuctionVos.addAll(completedAuctionDtos.stream()
					.map(this::toCompletedVo).collect(Collectors.toList()));
		}	
		return completedAuctionVos;
	}

	private CompletedAuctionVo toCompletedVo(CompletedAuctionDto completedAuctionDto) {
		CompletedAuctionVo completedAuctionVo = new CompletedAuctionVo();
		completedAuctionVo.setName(completedAuctionDto.getName());
		completedAuctionVo.setMinBidAmount(completedAuctionDto.getMinBidAmount());
		completedAuctionVo.setBidderName(completedAuctionDto.getBidderName());
		completedAuctionVo.setMaxBidAmount(completedAuctionDto.getMaxBidAmount());
		completedAuctionVo.setProductId(completedAuctionDto.getProductId());
		return completedAuctionVo;
	}


}


