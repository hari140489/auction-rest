package com.prokarma.opa.repository;

import java.util.List;

import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.BuyerBidDto;

public interface BidRepository {

	public void saveBid(BidDto bidDto);
	
	public List<BidDto> getBidsByProduct(long productId);
	
	public List<BuyerBidDto> findMyCompletedBids(String email);
	
	public List<BuyerBidDto> findActiveBidsByEmail(String email);

	public BidDto getBidForUserByProduct(String email, long productId);

	public void updateBid(BidDto bidDto);
	
}
