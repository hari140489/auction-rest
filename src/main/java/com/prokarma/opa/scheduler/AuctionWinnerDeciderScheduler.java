package com.prokarma.opa.scheduler;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prokarma.opa.service.SchedulerService;

import ch.qos.logback.classic.Logger;

@Component
public class AuctionWinnerDeciderScheduler {
	private final Logger logger = (Logger) LoggerFactory.getLogger(AuctionWinnerDeciderScheduler.class);

	private final SchedulerService schedulerService;

	@Autowired
	public AuctionWinnerDeciderScheduler(final SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@Scheduled(cron = "0 0/5 * * * * ")
	public void findWinner() {
		logger.info("Scheduler triggered - Find winner");
		schedulerService.declareWinnersForExpiredAuctions();
		logger.info("Scheduler stopped - Find winner");
	}
}
