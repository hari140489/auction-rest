package com.prokarma.opa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prokarma.opa.converter.AuctionConverter;
import com.prokarma.opa.exception.InvalidAuctionSearchException;
import com.prokarma.opa.exception.ProductNotFoundException;
import com.prokarma.opa.repository.AuctionRepository;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.AuctionDtoWithBids;
import com.prokarma.opa.repository.model.CompletedAuctionDto;
import com.prokarma.opa.service.validator.AuctionValidator;
import com.prokarma.opa.util.ValidationUtils;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;

@Service
public class AuctionServiceImpl implements AuctionService {

	private AuctionRepository auctionRepository;

	private AuctionValidator auctionValidator;

	private AuctionConverter auctionConverter;

	@Autowired
	public AuctionServiceImpl(AuctionRepository auctionrepository,
			AuctionValidator auctionValidator, AuctionConverter auctionConverter) {
		this.auctionRepository = auctionrepository;
		this.auctionValidator = auctionValidator;
		this.auctionConverter = auctionConverter;

	}

	@Override
	public void addAuction(AuctionVo auctionVo) {
		auctionValidator.validate(auctionVo);
		AuctionDto auctionDto = auctionConverter.toDto(auctionVo);
		auctionRepository.saveAuction(auctionDto);
	}

	@Override
	public List<AuctionVo> viewProducts(final String email, String name, String type) {
		if (ValidationUtils.isEmpty(name) && ValidationUtils.isEmpty(type)) {
			throw new InvalidAuctionSearchException();
		} else {
			List<AuctionDto> auctionDtos = auctionRepository.viewAllProducts(email, name, type);
			List<AuctionVo> auctionVos = auctionConverter.toVoList(auctionDtos);
			return auctionVos;
		}
	}

	@Override
	public List<AuctionVo> findMyActiveAuctions(String email) {
		List<AuctionDto> auctionDto= auctionRepository.findActiveAuctionsByEmail(email);
		List<AuctionVo> auctionVos=auctionConverter.toVoList(auctionDto);
		return auctionVos;

	}

	@Override
	public List<CompletedAuctionVo> retrieveCompletedAuctions(String email) {
		List<CompletedAuctionDto> completedAuctionDtos = auctionRepository.findCompletedAuctionsByEmail(email);
		List<CompletedAuctionVo> completedAuctionVos= auctionConverter.toCompletedVoList(completedAuctionDtos);
		return completedAuctionVos;
	}

	@Override
	public AuctionVo findAuctionByIdWithMaximumBid(long productId) {
		AuctionDto auctionDto = auctionRepository.getAuctionByIdWithMaximumBid(productId);
		if(auctionDto != null)
			return auctionConverter.toVo(auctionDto);
		throw new ProductNotFoundException();
	}
}
