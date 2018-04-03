package com.prokarma.opa.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.sql.AuctionSQL;
import com.prokarma.opa.sql.BidSQL;

import ch.qos.logback.classic.Logger;

@Repository
public class SchedulerRepositoryImpl implements SchedulerRepository {
	private final Logger logger = (Logger) LoggerFactory.getLogger(SchedulerRepositoryImpl.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public SchedulerRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public List<Integer> findProductDetailsWithStatusY() {
		List<Integer> results = (List<Integer>) jdbcTemplate.queryForList(
				AuctionSQL.FIND_ACTIVE_PRODUCTS_WITH_PAST_EXPIRATION_DATE_SQL, new HashMap<>(), Integer.class);
		return results;
	}

	@Override
	public void updateActiveStatus(List<Integer> expiredActiveAuctions) {
		List<AuctionDto> auctionDtos = expiredActiveAuctions.stream().map(productId -> {
			AuctionDto auctionDto = new AuctionDto();
			auctionDto.setProduct_id(productId);
			return auctionDto;
		}).collect(Collectors.toList());
		final SqlParameterSource[] activeStatus = SqlParameterSourceUtils.createBatch(auctionDtos.toArray());
		final int[] updatedActiveStatus = jdbcTemplate.batchUpdate(AuctionSQL.UPDATE_ACTIVE_STATUS_TO_N, activeStatus);
		logger.info(updatedActiveStatus.toString());
	}

	@Override
	public List<BidDto> findWinningBids(List<Integer> expiredActiveAuctions) {
		List<BidDto> bids = new ArrayList<>();
		for (Integer productId : expiredActiveAuctions) {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("productId", productId);
			bids.addAll(jdbcTemplate.query(BidSQL.FIND_WINNING_BIDS, params, new RowMapper<BidDto>() {

				@Override
				public BidDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
					BidDto bidDto = new BidDto();
					bidDto.setId(resultSet.getLong("bid_id"));
					bidDto.setProductId(resultSet.getInt("product_id"));
					bidDto.setAmount(resultSet.getInt("bid_amount"));
					return bidDto;
				}
			}));
		}
		return bids;
	}

	@Override
	public void updateWinnerStatusToY(List<BidDto> winningBids) {
		final SqlParameterSource[] winner = SqlParameterSourceUtils.createBatch(winningBids.toArray());
		final int[] updateWinnerStatus = jdbcTemplate.batchUpdate(BidSQL.UPDATE_WINNER_STATUS_TO_Y, winner);
		logger.info(updateWinnerStatus.toString());
	}
}
