package com.prokarma.opa.service;

import java.util.List;

import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.BidVo;
import com.prokarma.opa.web.domain.BuyerBidVo;


public interface BidService {
	
	public void createBid(BidVo bidVo, UserDto userDto);
	
	public AuctionVo getBidsByProduct(int productId);

	public List<BuyerBidVo> getActiveBidsByBuyerEmail(String email);
	
	List<BuyerBidVo> findMyCompletedBids(String email);

	public BidVo getBidByProductForUser(String email, long productId);
	
}
