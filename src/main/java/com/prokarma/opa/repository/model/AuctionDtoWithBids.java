package com.prokarma.opa.repository.model;

import java.util.List;

public class AuctionDtoWithBids {
	
	private AuctionDto auction;
	private List<BidDto> bids;

	public AuctionDto getAuction() {
		return auction;
	}

	public void setAuctionDto(AuctionDto auction) {
		this.auction = auction;
	}

	public List<BidDto> getBids() {
		return bids;
	}

	public void setBids(List<BidDto> bids) {
		this.bids = bids;
	}
	
}
