package com.prokarma.opa.repository;

import java.util.List;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.CompletedAuctionDto;

public interface AuctionRepository {

	void saveAuction(AuctionDto auctionDto);

	public List<AuctionDto> viewAllProducts(String email, String name, String type);

	AuctionDto getProductById(long productId);

	List<AuctionDto> findActiveAuctionsByEmail(String email);

	List<CompletedAuctionDto> findCompletedAuctionsByEmail(String email);

	AuctionDto getAuctionByIdWithMaximumBid(long productId);
	
	

}
