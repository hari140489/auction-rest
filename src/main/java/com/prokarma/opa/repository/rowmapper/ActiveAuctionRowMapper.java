package com.prokarma.opa.repository.rowmapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.prokarma.opa.repository.model.AuctionDto;

public class ActiveAuctionRowMapper implements RowMapper<AuctionDto> {

	@Override
	public AuctionDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		AuctionDto auctionDto = new AuctionDto();
		auctionDto.setName(resultSet.getString("name"));
		auctionDto.setType(resultSet.getString("type"));
		auctionDto.setProduct_id(resultSet.getInt("product_id"));
		auctionDto.setMin_bid_amount(resultSet.getInt("min_bid_amount"));
		auctionDto.setMaxBidAmount(resultSet.getInt("max_bid_amount"));
		auctionDto.setImage(resultSet.getBytes("image"));
		return auctionDto;
	}

}

