package com.prokarma.opa.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.opa.OnlineProductAuctionApplication;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.BuyerBidDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineProductAuctionApplication.class)
@Transactional
public class BidRepositoryImplTest {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private BidRepository bidRepository;
	
	@Test
	public void testSaveBid() throws Exception {
		insertDummyUsers();
		
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		String retrieveProductDetailsSql="INSERT into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
		
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(0).getName());
		params.addValue("productId",863);
		params.addValue("type",auctionDtos.get(0).getType());
		params.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params.addValue("minBidAmount",55000);
		params.addValue("active","N");
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		BidDto bidDto = new BidDto();
		bidDto.setProductId(863);
		bidDto.setId(1563);
		bidDto.setAmount(70001);
		SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		bidDto.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto.setBidderEmail("dv@gmail.com");
		
		bidRepository.saveBid(bidDto);
		
		BidDto savedBid = jdbcTemplate.query("SELECT bid_id, product_id, bid_amount FROM opa.bid WHERE product_id=863", resultSet -> {
			if(resultSet.next()) {
				BidDto bid = new BidDto();
				bid.setId(resultSet.getLong("bid_id"));
				bid.setProductId(resultSet.getLong("product_id"));
				bid.setAmount(resultSet.getInt("bid_amount"));
				return bid;
			}
			return null;
		});
		
		assertNotEquals(null, savedBid);
		assertEquals(863, savedBid.getProductId());
		assertEquals(70001, savedBid.getAmount());
		
	}
	
	@Test
	public void testFindActiveBidsByEmail() throws Exception {
		insertDummyUsers();
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		String retrieveProductDetailsSql="INSERT into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
		
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(0).getName());
		params.addValue("productId",863);
		params.addValue("type",auctionDtos.get(0).getType());
		params.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params.addValue("minBidAmount",55000);
		params.addValue("active","Y");
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("name", auctionDtos.get(1).getName());
		parameters.addValue("productId", 963);
		parameters.addValue("type",auctionDtos.get(1).getType());
		parameters.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		parameters.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		parameters.addValue("minBidAmount",65000);
		parameters.addValue("active","Y");
		parameters.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, parameters);
		
		insertDummyActiveBids();
		
		List<BuyerBidDto> buyerBidDtos = bidRepository.findActiveBidsByEmail("vbd@gmail.com");
		
		assertEquals(2, buyerBidDtos.size());
		assertEquals(863, buyerBidDtos.get(1).getProductId());
		assertEquals(1696, buyerBidDtos.get(1).getBidId());
		assertEquals(70003, buyerBidDtos.get(1).getBuyerBidAmount());
		assertEquals(963, buyerBidDtos.get(0).getProductId());
		assertEquals(2305, buyerBidDtos.get(0).getBidId());
		assertEquals(80003, buyerBidDtos.get(0).getBuyerBidAmount());
		
		
		
	}
	
	@Test
	public void testMyCompletedBidsByEmail() throws Exception{
		
		insertDummyUsers();
		
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		String retrieveProductDetailsSql="INSERT into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
		
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(0).getName());
		params.addValue("productId",863);
		params.addValue("type",auctionDtos.get(0).getType());
		params.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params.addValue("minBidAmount",55000);
		params.addValue("active","N");
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("name", auctionDtos.get(1).getName());
		parameters.addValue("productId", 963);
		parameters.addValue("type",auctionDtos.get(1).getType());
		parameters.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		parameters.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		parameters.addValue("minBidAmount",65000);
		parameters.addValue("active","N");
		parameters.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, parameters);
		
		insertDummyCompletedBids();
		
		List<BuyerBidDto> buyerBidDtos = bidRepository.findMyCompletedBids("vbd@gmail.com");
		
		assertEquals(2, buyerBidDtos.size());
	    assertEquals(auctionDtos.get(1).getName(), buyerBidDtos.get(0).getProductName());
	    assertEquals(65000, buyerBidDtos.get(0).getActualAmount());
	    assertEquals("vbd", buyerBidDtos.get(0).getBidderName());
	    assertEquals(80003, buyerBidDtos.get(0).getMaximumBidAmount());

	}
	
	@Test
	public void testGetBidsByProductForUser() throws Exception {
		insertDummyUsers();
		
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		String retrieveProductDetailsSql="INSERT into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
		
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(0).getName());
		params.addValue("productId",863);
		params.addValue("type",auctionDtos.get(0).getType());
		params.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params.addValue("minBidAmount",55000);
		params.addValue("active","Y");
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("name", auctionDtos.get(1).getName());
		parameters.addValue("productId", 963);
		parameters.addValue("type",auctionDtos.get(1).getType());
		parameters.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		parameters.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		parameters.addValue("minBidAmount",65000);
		parameters.addValue("active","N");
		parameters.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, parameters);
		
		
		insertDummyActiveBids();
		
		BidDto bid = bidRepository.getBidForUserByProduct("dv@gmail.com", 863);
		
		assertEquals(863, bid.getProductId());
		assertEquals(1563, bid.getId());
		assertEquals(70001, bid.getAmount());
		
	}
	
	@Test
	public void testGetBidsByProduct() throws Exception {
		insertDummyUsers();
		
		int productId = 1;
		BidDto bidDto = new BidDto();
		bidDto.setAmount(50000);
		bidDto.setBidderEmail("dv@gmail.com");
		SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		bidDto.setCreatedDate(dateFormat.parse("21/03/2018"));
		bidDto.setId(10000024);
		bidDto.setProductId(1);
		
		String sql_product = "INSERT INTO opa.product_auction (product_id, owner_email) VALUES (:productId, :ownerEmail)";
		MapSqlParameterSource par = new MapSqlParameterSource();
		par.addValue("productId", 1);
		par.addValue("ownerEmail", "abc@gmail.com");
		jdbcTemplate.update(sql_product, par);

		String sql_bid = "insert into opa.bid(bid_id, bidder_email, bid_amount, created_date, product_id) values(:bid_id, :bidder_email,"
				+ " :bid_amount, :created_date, :product_id)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("bid_id", 10000024);
		parameters.addValue("bidder_email", "dv@gmail.com");
		parameters.addValue("bid_amount", 50000);
		parameters.addValue("created_date", dateFormat.parse("21/03/2018"));
		parameters.addValue("product_id", 1);
		jdbcTemplate.update(sql_bid, parameters);

		List<BidDto> bids = bidRepository.getBidsByProduct(productId);
		
		assertEquals(bidDto.getAmount(), bids.get(0).getAmount());
		assertEquals(bidDto.getBidderEmail(), bids.get(0).getBidderEmail());
		assertEquals(bidDto.getCreatedDate(), bids.get(0).getCreatedDate());
		assertEquals(bidDto.getId(), bids.get(0).getId());
		assertEquals(bidDto.getProductId(), bids.get(0).getProductId());

	}
	
	@Test
	public void testUpdateBid() throws Exception {
		insertDummyUsers();
		
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		String retrieveProductDetailsSql="INSERT into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
		
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(0).getName());
		params.addValue("productId",863);
		params.addValue("type",auctionDtos.get(0).getType());
		params.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params.addValue("minBidAmount",55000);
		params.addValue("active","Y");
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("name", auctionDtos.get(1).getName());
		parameters.addValue("productId", 963);
		parameters.addValue("type",auctionDtos.get(1).getType());
		parameters.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		parameters.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		parameters.addValue("minBidAmount",65000);
		parameters.addValue("active","N");
		parameters.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, parameters);
		
		
		List<BidDto> testBids = insertDummyActiveBids();
		
		BidDto bidToBeUpdated = testBids.get(0);
		bidToBeUpdated.setAmount(80000);
		
		bidRepository.updateBid(testBids.get(0));
		
		BidDto updatedBid = jdbcTemplate.query("SELECT bid_id, product_id, bid_amount FROM opa.bid WHERE product_id=863", resultSet -> {
			if(resultSet.next()) {
				BidDto bid = new BidDto();
				bid.setId(resultSet.getLong("bid_id"));
				bid.setProductId(resultSet.getLong("product_id"));
				bid.setAmount(resultSet.getInt("bid_amount"));
				return bid;
			}
			return null;
		});
		
		assertNotEquals(null, updatedBid);
		assertEquals(863, updatedBid.getProductId());
		assertEquals(80000, updatedBid.getAmount());
		
	}

	private void insertDummyUsers() {
		String sql = "INSERT INTO opa.user_opa (email, name, password, active) VALUES (:email, :name, 'pwd', 'Y')";
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("email", "dv@gmail.com");
		sqlParameters.addValue("name", "dv");
		jdbcTemplate.update(sql, sqlParameters);
		sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("email", "vbd@gmail.com");
		sqlParameters.addValue("name", "vbd");
		jdbcTemplate.update(sql, sqlParameters);sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("email", "abc@gmail.com");
		sqlParameters.addValue("name", "abc");
		jdbcTemplate.update(sql, sqlParameters);sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("email", "ab@c.com");
		sqlParameters.addValue("name", "ab");
		jdbcTemplate.update(sql, sqlParameters);				
	}
	
	private List<BidDto> insertDummyCompletedBids() throws Exception {
		List<BidDto> bidDtos=new ArrayList<BidDto>();
		BidDto bidDto1=new BidDto();
		BidDto bidDto2=new BidDto();
		BidDto bidDto3=new BidDto();
		BidDto bidDto4=new BidDto();
		
		bidDto1.setProductId(863);
		bidDto1.setId(1563);
		bidDto1.setAmount(70001);
		SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		bidDto1.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto1.setBidderEmail("dv@gmail.com");
		
		bidDto2.setProductId(863);
		bidDto2.setId(1696);
		bidDto2.setAmount(70003);
		bidDto2.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto2.setBidderEmail("vbd@gmail.com");
		
		bidDto3.setProductId(963);
		bidDto3.setId(1985);
		bidDto3.setAmount(80002);
		bidDto3.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto3.setBidderEmail("abc@gmail.com");
		
		bidDto4.setProductId(963);
		bidDto4.setId(2305);
		bidDto4.setAmount(80003);
		bidDto4.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto4.setBidderEmail("vbd@gmail.com");
		
		bidDtos.add(bidDto1);
		bidDtos.add(bidDto2);
		bidDtos.add(bidDto3);	
		bidDtos.add(bidDto4);
		
		String sql1="insert into opa.bid(bid_id,product_id,bidder_email,bid_amount,created_date, win_status) values(:id,:productId,:bidderEmail,:bidAmount,:createdDate, :winStatus)";
		MapSqlParameterSource map1=new MapSqlParameterSource();
		map1.addValue("id", bidDtos.get(0).getId());
		map1.addValue("productId",bidDtos.get(0).getProductId());
		map1.addValue("bidderEmail", bidDtos.get(0).getBidderEmail());
		map1.addValue("bidAmount",bidDtos.get(0).getAmount());
		map1.addValue("createdDate",bidDtos.get(0).getCreatedDate());
		map1.addValue("winStatus", "N");
		jdbcTemplate.update(sql1, map1);
		
		MapSqlParameterSource mapSql=new MapSqlParameterSource();
		mapSql.addValue("id", bidDtos.get(1).getId());
		mapSql.addValue("productId",bidDtos.get(1).getProductId());
		mapSql.addValue("bidderEmail", bidDtos.get(1).getBidderEmail());
		mapSql.addValue("bidAmount",bidDtos.get(1).getAmount());
		mapSql.addValue("createdDate",bidDtos.get(1).getCreatedDate());
		mapSql.addValue("winStatus", "Y");
		jdbcTemplate.update(sql1, mapSql);
		
		MapSqlParameterSource mapSqlParameter=new MapSqlParameterSource();
		mapSqlParameter.addValue("id", bidDtos.get(2).getId());
		mapSqlParameter.addValue("productId",bidDtos.get(2).getProductId());
		mapSqlParameter.addValue("bidderEmail", bidDtos.get(2).getBidderEmail());
		mapSqlParameter.addValue("bidAmount",bidDtos.get(2).getAmount());
		mapSqlParameter.addValue("createdDate",bidDtos.get(2).getCreatedDate());
		mapSqlParameter.addValue("winStatus", "N");
		jdbcTemplate.update(sql1, mapSqlParameter);
		
		MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", bidDtos.get(3).getId());
		mapSqlParameterSource.addValue("productId",bidDtos.get(3).getProductId());
		mapSqlParameterSource.addValue("bidderEmail", bidDtos.get(3).getBidderEmail());
		mapSqlParameterSource.addValue("bidAmount",bidDtos.get(3).getAmount());
		mapSqlParameterSource.addValue("createdDate",bidDtos.get(3).getCreatedDate());
		mapSqlParameterSource.addValue("winStatus", "Y");
		jdbcTemplate.update(sql1, mapSqlParameterSource);
		return bidDtos;
	}
	
	private List<BidDto> insertDummyActiveBids() throws Exception {
		List<BidDto> bidDtos=new ArrayList<BidDto>();
		BidDto bidDto1=new BidDto();
		BidDto bidDto2=new BidDto();
		BidDto bidDto3=new BidDto();
		BidDto bidDto4=new BidDto();
		
		bidDto1.setProductId(863);
		bidDto1.setId(1563);
		bidDto1.setAmount(70001);
		SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		bidDto1.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto1.setBidderEmail("dv@gmail.com");
		
		bidDto2.setProductId(863);
		bidDto2.setId(1696);
		bidDto2.setAmount(70003);
		bidDto2.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto2.setBidderEmail("vbd@gmail.com");
		
		bidDto3.setProductId(963);
		bidDto3.setId(1985);
		bidDto3.setAmount(80002);
		bidDto3.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto3.setBidderEmail("abc@gmail.com");
		
		bidDto4.setProductId(963);
		bidDto4.setId(2305);
		bidDto4.setAmount(80003);
		bidDto4.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto4.setBidderEmail("vbd@gmail.com");
		
		bidDtos.add(bidDto1);
		bidDtos.add(bidDto2);
		bidDtos.add(bidDto3);	
		bidDtos.add(bidDto4);
		
		String sql1="insert into opa.bid(bid_id,product_id,bidder_email,bid_amount,created_date, win_status) values(:id,:productId,:bidderEmail,:bidAmount,:createdDate, :winStatus)";
		MapSqlParameterSource map1=new MapSqlParameterSource();
		map1.addValue("id", bidDtos.get(0).getId());
		map1.addValue("productId",bidDtos.get(0).getProductId());
		map1.addValue("bidderEmail", bidDtos.get(0).getBidderEmail());
		map1.addValue("bidAmount",bidDtos.get(0).getAmount());
		map1.addValue("createdDate",bidDtos.get(0).getCreatedDate());
		map1.addValue("winStatus", "N");
		jdbcTemplate.update(sql1, map1);
		
		MapSqlParameterSource mapSql=new MapSqlParameterSource();
		mapSql.addValue("id", bidDtos.get(1).getId());
		mapSql.addValue("productId",bidDtos.get(1).getProductId());
		mapSql.addValue("bidderEmail", bidDtos.get(1).getBidderEmail());
		mapSql.addValue("bidAmount",bidDtos.get(1).getAmount());
		mapSql.addValue("createdDate",bidDtos.get(1).getCreatedDate());
		mapSql.addValue("winStatus", "N");
		jdbcTemplate.update(sql1, mapSql);
		
		MapSqlParameterSource mapSqlParameter=new MapSqlParameterSource();
		mapSqlParameter.addValue("id", bidDtos.get(2).getId());
		mapSqlParameter.addValue("productId",bidDtos.get(2).getProductId());
		mapSqlParameter.addValue("bidderEmail", bidDtos.get(2).getBidderEmail());
		mapSqlParameter.addValue("bidAmount",bidDtos.get(2).getAmount());
		mapSqlParameter.addValue("createdDate",bidDtos.get(2).getCreatedDate());
		mapSqlParameter.addValue("winStatus", "N");
		jdbcTemplate.update(sql1, mapSqlParameter);
		
		MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", bidDtos.get(3).getId());
		mapSqlParameterSource.addValue("productId",bidDtos.get(3).getProductId());
		mapSqlParameterSource.addValue("bidderEmail", bidDtos.get(3).getBidderEmail());
		mapSqlParameterSource.addValue("bidAmount",bidDtos.get(3).getAmount());
		mapSqlParameterSource.addValue("createdDate",bidDtos.get(3).getCreatedDate());
		mapSqlParameterSource.addValue("winStatus", "N");
		jdbcTemplate.update(sql1, mapSqlParameterSource);
		return bidDtos;
	}
	
}





