package com.prokarma.opa.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.opa.repository.SchedulerRepository;
import com.prokarma.opa.repository.model.BidDto;

@Service
public class SchedulerServiceImpl implements SchedulerService {
	private static Logger logger = Logger.getLogger(SchedulerServiceImpl.class);
	
	private final SchedulerRepository schedulerRepository;
	
	@Autowired
    public SchedulerServiceImpl(final SchedulerRepository schedulerRepository) {
	   this.schedulerRepository=schedulerRepository;
	}

	@Override
	@Transactional
	public void declareWinnersForExpiredAuctions() {
		List<Integer> expiredActiveAuctions = schedulerRepository.findProductDetailsWithStatusY();		
		if(CollectionUtils.isNotEmpty(expiredActiveAuctions)) {
			schedulerRepository.updateActiveStatus(expiredActiveAuctions);
			logger.info("ActiveStaus updated to 'N'");
			List<BidDto> winningBids = schedulerRepository.findWinningBids(expiredActiveAuctions);
			logger.info("Winning Bids retrieved");
			if(CollectionUtils.isNotEmpty(winningBids)) {				
				schedulerRepository.updateWinnerStatusToY(winningBids);
			}
			logger.info("updated winner status to 'Y'");
		} else {
			logger.info("No active expired auctions found");
		}
	}
}
