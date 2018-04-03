package com.prokarma.opa.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.BuyerBidDto;
import com.prokarma.opa.sql.BidSQL;

@Repository
public class BidRepositoryImpl implements BidRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public BidRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void saveBid(BidDto bidDto) {
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("bidder_email", bidDto.getBidderEmail());
		sqlParameters.addValue("product_id", bidDto.getProductId());
		sqlParameters.addValue("bid_amount", bidDto.getAmount());
		jdbcTemplate.update(BidSQL.SAVE_BID_SQL, sqlParameters);
	}

	@Override
	public List<BidDto> getBidsByProduct(long productId) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", productId);

		return jdbcTemplate.query(BidSQL.VIEW_ALL_BIDS_SQL, parameters, new RowMapper<BidDto>() {
			
			@Override
			public BidDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
					BidDto bidDto = new BidDto();

					bidDto.setBidderEmail(resultSet.getString("bidder_email"));
					bidDto.setAmount(resultSet.getInt("bid_amount"));
					bidDto.setCreatedDate(resultSet.getDate("created_date"));
					bidDto.setId(resultSet.getLong("bid_id"));
					bidDto.setProductId(resultSet.getLong("product_id"));
					
					return bidDto;

			}

		});

	}

	@Override
	public List<BuyerBidDto> findMyCompletedBids(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);
		
		return jdbcTemplate.query(BidSQL.GET_COMPLETED_BIDS_SQL, parameters, new RowMapper<BuyerBidDto>() {

			@Override
			public BuyerBidDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				BuyerBidDto buyerbidDto=new BuyerBidDto();
				buyerbidDto.setProductName(resultSet.getString("name"));
				buyerbidDto.setActualAmount(resultSet.getInt("min_bid_amount"));
				buyerbidDto.setBidderName(resultSet.getString("bidder_name"));
				buyerbidDto.setMaximumBidAmount(resultSet.getInt("bid_amount"));
				buyerbidDto.setBidCreatedDate(resultSet.getDate("bid_created_date"));
				return buyerbidDto;
			}
		
		});
	}

	@Override
	public List<BuyerBidDto> findActiveBidsByEmail(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);
		
		return jdbcTemplate.query(BidSQL.GET_ACTIVE_BIDS_SQL, parameters, new RowMapper<BuyerBidDto>() {

			@Override
			public BuyerBidDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				BuyerBidDto buyerBidDto = new BuyerBidDto();
				buyerBidDto.setProductId(rs.getLong("product_id"));
				buyerBidDto.setBidId(rs.getLong("bid_id"));
				buyerBidDto.setProductName(rs.getString("name"));
				buyerBidDto.setImage(rs.getBytes("image"));
				buyerBidDto.setActualAmount(rs.getInt("min_bid_amount"));
				buyerBidDto.setBuyerBidAmount(rs.getInt("bid_amount"));
				buyerBidDto.setMaximumBidAmount(rs.getInt("max_bid_amount"));
				return buyerBidDto;
			}
			
		});
	}

	@Override
	public BidDto getBidForUserByProduct(String email, long productId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);
		parameters.addValue("productId", productId);
		
		
		
		return jdbcTemplate.query(BidSQL.GET_BID_FOR_USER_BY_PRODUCT, parameters, new ResultSetExtractor<BidDto>() {

			@Override
			public BidDto extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if(resultSet.next()) {
					BidDto bidDto = new BidDto();
					bidDto.setId(resultSet.getLong("bid_id"));
					bidDto.setBidderEmail(resultSet.getString("bidder_email"));
					bidDto.setAmount(resultSet.getInt("bid_amount"));
					bidDto.setProductId(resultSet.getLong("product_id"));
					bidDto.setCreatedDate(resultSet.getDate("created_date"));
					return bidDto;
				}
				return null;
			}
		});
	}

	@Override
	public void updateBid(BidDto bidDto) {
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("bidId", bidDto.getId());
		sqlParameters.addValue("amount", bidDto.getAmount());
		jdbcTemplate.update(BidSQL.UPDATE_BID_SQL, sqlParameters);
	}
}