package com.prokarma.opa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prokarma.opa.converter.AuctionConverter;
import com.prokarma.opa.converter.BidConverter;
import com.prokarma.opa.converter.BuyerBidConverter;
import com.prokarma.opa.exception.ProductNotFoundException;
import com.prokarma.opa.repository.AuctionRepository;
import com.prokarma.opa.repository.BidRepository;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.BuyerBidDto;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.service.validator.BidValidator;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.BidVo;
import com.prokarma.opa.web.domain.BuyerBidVo;

@Service
public class BidServiceImpl implements BidService {
	
	private BidRepository bidRepository;
	
	private BidConverter bidConverter;
	
	private BuyerBidConverter buyerBidConverter;
	
	private BidValidator bidValidator;
	
	private AuctionRepository auctionRepository;
	
	private AuctionConverter auctionConverter;
	
	@Autowired
	public BidServiceImpl(BidRepository bidRepository, BidConverter bidConverter, BidValidator bidValidator,
			AuctionRepository auctionRepository, AuctionConverter auctionConverter, BuyerBidConverter buyerBidConverter) {
		this.bidRepository = bidRepository;
		this.bidConverter = bidConverter;
		this.buyerBidConverter = buyerBidConverter;
		this.bidValidator = bidValidator;
		this.auctionRepository = auctionRepository;
		this.auctionConverter = auctionConverter;
	}
	
	@Override
	public void createBid(BidVo bidVo, UserDto userDto) {
		bidValidator.validate(bidVo, userDto);
		BidDto bidDto = bidConverter.toDto(bidVo);
		bidDto.setBidderEmail(userDto.getEmail());
		BidDto savedBid = bidRepository.getBidForUserByProduct(userDto.getEmail(), bidVo.getProductId());
		if(savedBid == null) {
			bidRepository.saveBid(bidDto);
		} else {
			bidDto.setId(savedBid.getId());
			bidRepository.updateBid(bidDto);
		}
	}

	@Override
	public AuctionVo getBidsByProduct(int productId) {
		AuctionDto auctionDto = auctionRepository.getProductById(productId);
		if (auctionDto != null) {
			List<BidDto> bidsDto = bidRepository.getBidsByProduct(productId);
			auctionDto.setBids(bidsDto);
			return auctionConverter.toVo(auctionDto);
		} else {
			throw new ProductNotFoundException();
		}
	}

	@Override
	public List<BuyerBidVo> getActiveBidsByBuyerEmail(String email) {
		List<BuyerBidDto> activeBidDtos = bidRepository.findActiveBidsByEmail(email);
		return buyerBidConverter.toVoList(activeBidDtos);
	}

	@Override
	public List<BuyerBidVo> findMyCompletedBids(String email) {
		List<BuyerBidDto> buyerBidDto =bidRepository.findMyCompletedBids(email);
		List<BuyerBidVo> buyerBidVo = buyerBidConverter.toVoList(buyerBidDto);
		return buyerBidVo;
	}

	@Override
	public BidVo getBidByProductForUser(String email, long productId) {
		BidDto bidDto = bidRepository.getBidForUserByProduct(email, productId);
		if(bidDto != null)
			return bidConverter.toVo(bidDto);
		return null;
	}

}
