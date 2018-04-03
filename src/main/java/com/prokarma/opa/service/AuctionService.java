package com.prokarma.opa.service;

import java.util.List;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;

public interface AuctionService {

	void addAuction(AuctionVo auctionvo);

	List<AuctionVo> viewProducts(String email,String name,String type);

	List<AuctionVo> findMyActiveAuctions(String email);
	
	List<CompletedAuctionVo> retrieveCompletedAuctions(String email);

	AuctionVo findAuctionByIdWithMaximumBid(long productId);

}
