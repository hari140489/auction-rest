package com.prokarma.opa.service.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prokarma.opa.exception.InvalidBidException;
import com.prokarma.opa.repository.AuctionRepository;
import com.prokarma.opa.repository.BidRepository;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.web.domain.BidVo;

@Component
public class BidValidatorImpl implements BidValidator {
	
	AuctionRepository auctionRepository;
	
	BidRepository bidRepository;
	
	@Autowired
	public BidValidatorImpl(AuctionRepository auctionRepository, BidRepository bidRepository) {
		this.auctionRepository = auctionRepository;
		this.bidRepository = bidRepository;
	}

	@Override
	public void validate(BidVo bidVo, UserDto bidder) {
		AuctionDto auction = auctionRepository.getProductById(bidVo.getProductId());
		auction.setBids(bidRepository.getBidsByProduct(bidVo.getProductId()));
		if(isBidBySeller(auction, bidder)) {
			throw new InvalidBidException("Seller cannot bid on his own product");
		}
		if(isAuctionInactive(auction)) {
			throw new InvalidBidException("Cannot bid on an inactive auction");
		}
		int bidAmount = bidVo.getAmount();
		if(bidAmount <= 0) {
			throw new InvalidBidException("Bid amount should be non-negative");
		} else if(isBidGreaterThanMinimumBidAmount(bidAmount, auction.getMin_bid_amount())) { 
			throw new InvalidBidException("Bid amount should be greater than minimum bid amount");
		} else if(!isBidTheHighestBid(auction.getBids(), bidAmount)) {
			throw new InvalidBidException("Bid amount should be greater than the current highest bid");
		}
	}

	private boolean isAuctionInactive(AuctionDto auction) {
		return "N".equalsIgnoreCase(auction.getActive());
	}

	private boolean isBidTheHighestBid(List<BidDto> bids, int bidAmount) {
		return bids.isEmpty() || (bidAmount > getHighestBid(bids));
	}

	private boolean isBidGreaterThanMinimumBidAmount(int bidAmount, int minBidAmount) {
		return bidAmount < minBidAmount;
	}

	private boolean isBidBySeller(AuctionDto auction, UserDto bidder) {
		return auction.getOwner_email().equals(bidder.getEmail());
	}

	private int getHighestBid(List<BidDto> bids) {
		return bids.stream().map(BidDto::getAmount).reduce(Integer::max).get();
	}
	
}
