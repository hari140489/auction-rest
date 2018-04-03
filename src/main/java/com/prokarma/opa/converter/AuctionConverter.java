package com.prokarma.opa.converter;

import java.util.List;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.CompletedAuctionDto;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;

public interface AuctionConverter {

	AuctionDto toDto(AuctionVo AuctionVo);

	AuctionVo toVo(AuctionDto auctionDto);

	List<AuctionDto> toDtoList(List<AuctionVo> auctionVos);

	List<AuctionVo> toVoList(List<AuctionDto> auctionDtos);

	List<CompletedAuctionVo> toCompletedVoList(List<CompletedAuctionDto> completedAuctionDtos);

}
