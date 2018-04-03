package com.prokarma.opa.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.CompletedAuctionDto;
import com.prokarma.opa.repository.rowmapper.ActiveAuctionRowMapper;
import com.prokarma.opa.sql.AuctionSQL;

@Repository
public class AuctionRepositoryImpl implements AuctionRepository {	

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public AuctionRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void saveAuction(AuctionDto auctionDto) {
		byte[] image = auctionDto.getImage();
		MapSqlParameterSource sqlparameter = new MapSqlParameterSource();
		sqlparameter.addValue("name", auctionDto.getName());
		sqlparameter.addValue("type", auctionDto.getType());
		sqlparameter.addValue("min_price", auctionDto.getMin_bid_amount());
		sqlparameter.addValue("expired_date", auctionDto.getExpiration_date());
		sqlparameter.addValue("email", auctionDto.getOwner_email());
		sqlparameter.addValue("active", "Y");
		sqlparameter.addValue("image", image);
		jdbcTemplate.update(AuctionSQL.SAVE_AUCTION_SQL, sqlparameter);
	}

	@Override
	public AuctionDto getProductById(long productId) {
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlParams.addValue("productId", productId);
		return jdbcTemplate.query(AuctionSQL.GET_PRODUCT_BY_ID_SQL, sqlParams, new ResultSetExtractor<AuctionDto>() {

			@Override
			public AuctionDto extractData(ResultSet resultSet) throws SQLException, DataAccessException {

				if (resultSet.next()) {
					AuctionDto auctionDto = new AuctionDto();
					auctionDto.setProduct_id(resultSet.getInt("product_id"));
					auctionDto.setName(resultSet.getString("name"));
					auctionDto.setType(resultSet.getString("type"));
					auctionDto.setMin_bid_amount(resultSet.getInt("min_bid_amount"));
					auctionDto.setExpiration_date(resultSet.getDate("expiration_date"));
					auctionDto.setOwner_email(resultSet.getString("owner_email"));
					auctionDto.setActive(resultSet.getString("active"));
					auctionDto.setCreated_date(resultSet.getDate("created_date"));
					auctionDto.setImage(resultSet.getBytes("image"));
					return auctionDto;
				}

				return null;
			}
		});
	}

	@Override
	public List<AuctionDto> findActiveAuctionsByEmail(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);
		return jdbcTemplate.query(AuctionSQL.MY_ACTIVE_AUCTION_SQL, parameters, new ActiveAuctionRowMapper());
	}

	@Override
	public List<AuctionDto> viewAllProducts(String email, String name, String type) {
		System.out.println("Entered AuctionRepository.viewAllProducts with email = "+email+" name = "+name+" type = "+type);
		
		StringBuffer sql = new StringBuffer(AuctionSQL.FIND_ACTIVE_AUCTIONs_BY_EMAIL_NAME_TYPE_SQL);
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);
		if(StringUtils.isNotEmpty(name)) {
			sql.append(" and name like :name ");
			parameters.addValue("name", '%'+name+'%');
		}
		if(StringUtils.isNotEmpty(type)) {
			sql.append(" and type like :type ");
			parameters.addValue("type", '%'+type+'%');
		}
		
		return jdbcTemplate.query(sql.toString(), parameters, new RowMapper<AuctionDto>() {
			@Override
			public AuctionDto mapRow(ResultSet resultset, int rowNum) throws SQLException { 
				AuctionDto auctionDto = new AuctionDto();
				auctionDto.setProduct_id(resultset.getInt("product_id"));
				auctionDto.setName(resultset.getString("name"));
				auctionDto.setType(resultset.getString("type"));
				auctionDto.setMin_bid_amount(resultset.getInt("min_bid_amount"));
				auctionDto.setExpiration_date(resultset.getDate("expiration_date"));
				auctionDto.setOwner_email(resultset.getString("owner_email"));
				auctionDto.setActive(resultset.getString("active"));
				auctionDto.setCreated_date(resultset.getDate("created_date"));
				auctionDto.setImage(resultset.getBytes("image"));
				return auctionDto;
			}
		});
	}

	@Override
	public List<CompletedAuctionDto> findCompletedAuctionsByEmail(String email) {
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("email", email);
		return jdbcTemplate.query(AuctionSQL.GET_COMPLETEDAUCTIONS_SQL, parameter,
				new RowMapper<CompletedAuctionDto>() {

					@Override
					public CompletedAuctionDto mapRow(final ResultSet resultset, final int rownum) throws SQLException {
						final CompletedAuctionDto completedAuctionDto = new CompletedAuctionDto();
						completedAuctionDto.setName(resultset.getString("name"));
						completedAuctionDto.setMinBidAmount(resultset.getInt("min_bid_amount"));
						completedAuctionDto.setBidderName(resultset.getString("bidder_name"));
						completedAuctionDto.setMaxBidAmount(resultset.getInt("max_bid_amount"));
						completedAuctionDto.setProductId(resultset.getInt("product_id"));
						return completedAuctionDto;
					}
				});
	}

	@Override
	public AuctionDto getAuctionByIdWithMaximumBid(long productId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", productId);
		return jdbcTemplate.query(AuctionSQL.GET_PRODUCT_BY_ID_WITH_MAXIMUM_BID_SQL, parameters,
				new ResultSetExtractor<AuctionDto>() {
			
					@Override
					public AuctionDto extractData(ResultSet resultSet) throws SQLException, DataAccessException {
						AuctionDto auctionDto = null;
						if (resultSet.next()) {
							auctionDto = new AuctionDto();
							auctionDto.setProduct_id(resultSet.getInt("product_id"));
							auctionDto.setName(resultSet.getString("name"));
							auctionDto.setType(resultSet.getString("type"));
							auctionDto.setMin_bid_amount(resultSet.getInt("min_bid_amount"));
							auctionDto.setExpiration_date(resultSet.getDate("expiration_date"));
							auctionDto.setOwner_email(resultSet.getString("owner_email"));
							auctionDto.setActive(resultSet.getString("active"));
							auctionDto.setCreated_date(resultSet.getDate("created_date"));
							auctionDto.setImage(resultSet.getBytes("image"));
							List<BidDto> bidDtos = new ArrayList<>();
							BidDto maxBidDto = new BidDto();
							maxBidDto.setAmount(resultSet.getInt("max_bid_amount"));
							bidDtos.add(maxBidDto);
							auctionDto.setBids(bidDtos);
						}
						return auctionDto;
					}
				});
	}

}
