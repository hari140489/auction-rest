package com.prokarma.opa.repository;

import java.util.List;

import com.prokarma.opa.repository.model.BidDto;

public interface SchedulerRepository {

	void updateActiveStatus(List<Integer> expiredActiveAuctions);
	
	public List<BidDto> findWinningBids(List<Integer> expiredActiveAuctions);
	
	public void updateWinnerStatusToY(List<BidDto> bidIds);

	List<Integer> findProductDetailsWithStatusY();

}
