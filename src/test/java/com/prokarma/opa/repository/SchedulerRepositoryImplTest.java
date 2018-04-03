package com.prokarma.opa.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.sql.BidSQL;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SchedulerRepositoryImplTest {

	@Autowired
	private   NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private  SchedulerRepository schedulerRepository;
	

	@Test
	public  void testFindProductDetailsWithStatusY_whenProductsWithActiveStatusYExists_thenRetrieveProductDetails() throws Exception {
		insertDummyUsers();
		
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		String retrieveProductDetailsSql="INSERT  into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
				
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(0).getName());
		params.addValue("productId",auctionDtos.get(0).getProduct_id());
		params.addValue("type",auctionDtos.get(0).getType());
		params.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params.addValue("minBidAmount",auctionDtos.get(0).getMin_bid_amount());
		params.addValue("active",auctionDtos.get(0).getActive());
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("name", auctionDtos.get(1).getName());
		parameters.addValue("productId", auctionDtos.get(1).getProduct_id());
		parameters.addValue("type",auctionDtos.get(1).getType());
		parameters.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		parameters.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		parameters.addValue("minBidAmount",auctionDtos.get(1).getMaxBidAmount());
		parameters.addValue("active",auctionDtos.get(1).getActive());
		parameters.addValue("ownerEmail","vbd@gmail.com");
		jdbcTemplate.update(retrieveProductDetailsSql, parameters);
		
		
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("name", auctionDtos.get(2).getName());
		map.addValue("productId", auctionDtos.get(2).getProduct_id());
		map.addValue("type",auctionDtos.get(2).getType());
		map.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		map.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		map.addValue("minBidAmount",auctionDtos.get(2).getMin_bid_amount());
		map.addValue("active",auctionDtos.get(2).getActive());
		map.addValue("ownerEmail","abc@gmail.com");
		jdbcTemplate.update(retrieveProductDetailsSql, map);
			
		List<Integer>activeExpiredProducts=schedulerRepository.findProductDetailsWithStatusY();
		
		
		assertEquals(auctionDtos.get(0).getProduct_id(), activeExpiredProducts.get(0).intValue());
		assertEquals(auctionDtos.get(1).getProduct_id(), activeExpiredProducts.get(1).intValue());	
	}
	
	@Test
	public void testUpdateActiveStatus_whenActiveExpiredProductsRetrieved_thenUpdateActiveStatusOfThoseProductsToN() throws Exception {
		insertDummyUsers();
		
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		
		String retrieveProductDetailsSql="INSERT  into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
				
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(0).getName());
		params.addValue("productId",auctionDtos.get(0).getProduct_id());
		params.addValue("type",auctionDtos.get(0).getType());
		params.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params.addValue("minBidAmount",auctionDtos.get(0).getMin_bid_amount());
		params.addValue("active",auctionDtos.get(0).getActive());
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue("name", auctionDtos.get(1).getName());
		parameters.addValue("productId", auctionDtos.get(1).getProduct_id());
		parameters.addValue("type",auctionDtos.get(1).getType());
		parameters.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		parameters.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		parameters.addValue("minBidAmount",auctionDtos.get(1).getMaxBidAmount());
		parameters.addValue("active",auctionDtos.get(1).getActive());
		parameters.addValue("ownerEmail","vbd@gmail.com");
		jdbcTemplate.update(retrieveProductDetailsSql, parameters);
		
		
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("name", auctionDtos.get(2).getName());
		map.addValue("productId", auctionDtos.get(2).getProduct_id());
		map.addValue("type",auctionDtos.get(2).getType());
		map.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		map.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		map.addValue("minBidAmount",auctionDtos.get(2).getMin_bid_amount());
		map.addValue("active",auctionDtos.get(2).getActive());
		map.addValue("ownerEmail","dv@gmail.com");
		jdbcTemplate.update(retrieveProductDetailsSql, map);
		
		
		String sql="select product_id from opa.product_auction where active='Y' and expiration_date<=To_date(sysdate, 'dd-MM-yy')";
		List<Integer> results=jdbcTemplate.queryForList(sql,new HashMap<>(), Integer.class);		
		schedulerRepository.updateActiveStatus(results);   
		
		assertEquals(auctionDtos.get(0).getActive(),"Y");
        assertEquals(auctionDtos.get(1).getActive(),"Y");
	}
	
	
	@Test
	public void testFindWinningBids_whenActiveStatusUpdateToN_thenReturnWinningBids() throws Exception {
		insertDummyUsers();
		List<Integer> expiredActiveAuctions = insertDummyProducts_andReturnExpiredAuctionIds();
		List<BidDto> bidDtos = insertDummyActiveBids(expiredActiveAuctions);
		
		List<BidDto> findWinningBids=schedulerRepository.findWinningBids(expiredActiveAuctions);
		
		assertEquals(2, findWinningBids.size());
		assertEquals(bidDtos.get(1).getId(),findWinningBids.get(0).getId());
		assertEquals(bidDtos.get(1).getAmount(),findWinningBids.get(0).getAmount());
		assertEquals(bidDtos.get(1).getProductId(),findWinningBids.get(0).getProductId());
		assertEquals(bidDtos.get(3).getId(),findWinningBids.get(1).getId());
		assertEquals(bidDtos.get(3).getAmount(),findWinningBids.get(1).getAmount());		
	    assertEquals(bidDtos.get(3).getProductId(), findWinningBids.get(1).getProductId());
		
	}
	
	@Test
	public void testUpdateWinnerStatusToY_givenWinningBids_updateWinStatusOfBids() throws Exception {
		insertDummyUsers();
		List<Integer> expiredActiveAuctions = insertDummyProducts_andReturnExpiredAuctionIds();
		List<BidDto> bidDtos = insertDummyActiveBids(expiredActiveAuctions);
		
		
		
		List<BidDto> winningBids = new ArrayList<>();
		for (Integer productId : expiredActiveAuctions) {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("productId", productId);
			System.out.println(params.getValues());
			winningBids.addAll(jdbcTemplate.query(BidSQL.FIND_WINNING_BIDS, params, new RowMapper<BidDto>() {

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
		
		schedulerRepository.updateWinnerStatusToY(winningBids);
		
		if(winningBids.size() == 0)
			fail("No winning bids were given for updating");
		
		StringBuilder updatedWinningBidsSql = new StringBuilder("SELECT win_status FROM opa.bid WHERE bid_id IN (");
		for(BidDto winningBid : winningBids) {
			updatedWinningBidsSql.append("'");
			updatedWinningBidsSql.append(winningBid.getId());
			updatedWinningBidsSql.append("',");
		}
		updatedWinningBidsSql.replace(updatedWinningBidsSql.length() - 1, updatedWinningBidsSql.length(), ")");
		List<String> winStatuses = jdbcTemplate.query(updatedWinningBidsSql.toString(), new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int i) throws SQLException {
				return rs.getString("win_status");
			}
			
		});
		
		assertEquals("Y", winStatuses.get(0));
		assertEquals("Y", winStatuses.get(1));		
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
	
	private List<Integer> insertDummyProducts_andReturnExpiredAuctionIds() throws Exception {
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		String retrieveProductDetailsSql="INSERT  into opa.product_auction(name,product_id,type,expiration_date,created_date,min_bid_amount,active,owner_email) values(:name,:productId,:type,:expirationDate,:createdDate,:minBidAmount,:active,:ownerEmail)";
		
		MapSqlParameterSource params1=new MapSqlParameterSource();
		params1.addValue("name", auctionDtos.get(0).getName());
		params1.addValue("productId",auctionDtos.get(0).getProduct_id());
		params1.addValue("type",auctionDtos.get(0).getType());
		params1.addValue("expirationDate",auctionDtos.get(0).getExpiration_date());
		params1.addValue("createdDate",auctionDtos.get(0).getCreated_date());
		params1.addValue("minBidAmount",auctionDtos.get(0).getMin_bid_amount());
		params1.addValue("active", "N");
		params1.addValue("ownerEmail","dv@gmail.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params1);
		
		
		MapSqlParameterSource params2=new MapSqlParameterSource();
		params2.addValue("name", auctionDtos.get(1).getName());
		params2.addValue("productId", auctionDtos.get(1).getProduct_id());
		params2.addValue("type",auctionDtos.get(1).getType());
		params2.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		params2.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		params2.addValue("minBidAmount",auctionDtos.get(1).getMaxBidAmount());
		params2.addValue("active", "N");
		params2.addValue("ownerEmail","abc@gmail.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params2);
		
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", auctionDtos.get(2).getName());
		params.addValue("productId", auctionDtos.get(2).getProduct_id());
		params.addValue("type",auctionDtos.get(2).getType());
		params.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		params.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		params.addValue("minBidAmount",auctionDtos.get(2).getMin_bid_amount());
		params.addValue("active","Y");
		params.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params);
		
		List<Integer> expiredActiveAuctions = new ArrayList<>();
		expiredActiveAuctions.add(auctionDtos.get(0).getProduct_id());
		expiredActiveAuctions.add(auctionDtos.get(1).getProduct_id());
		
		return expiredActiveAuctions;
	}
	
	private List<BidDto> insertDummyActiveBids(List<Integer> expiredActiveAuctions) throws Exception {
		List<BidDto> bidDtos=new ArrayList<BidDto>();
		BidDto bidDto1=new BidDto();
		BidDto bidDto2=new BidDto();
		BidDto bidDto3=new BidDto();
		BidDto bidDto4=new BidDto();
		
		bidDto1.setProductId(expiredActiveAuctions.get(0));
		bidDto1.setId(1563);
		bidDto1.setAmount(70001);
		SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		bidDto1.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto1.setBidderEmail("dv@gmail.com");
		
		bidDto2.setProductId(expiredActiveAuctions.get(0));
		bidDto2.setId(1696);
		bidDto2.setAmount(70003);
		bidDto2.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto2.setBidderEmail("vbd@gmail.com");
		
		bidDto3.setProductId(expiredActiveAuctions.get(1));
		bidDto3.setId(1985);
		bidDto3.setAmount(80002);
		bidDto3.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto3.setBidderEmail("abc@gmail.com");
		
		bidDto4.setProductId(expiredActiveAuctions.get(1));
		bidDto4.setId(2305);
		bidDto4.setAmount(80003);
		bidDto4.setCreatedDate(dateFormat.parse("22/03/2018"));
		bidDto4.setBidderEmail("ab@c.com");
		
		bidDtos.add(bidDto1);
		bidDtos.add(bidDto2);
		bidDtos.add(bidDto3);	
		bidDtos.add(bidDto4);
		
		String sql1="insert into opa.bid(bid_id,product_id,bidder_email,bid_amount,created_date, win_status) values(:id,:productId,:bidderEmail,:bidAmount,:createdDate, 'N')";
		MapSqlParameterSource map1=new MapSqlParameterSource();
		map1.addValue("id", bidDtos.get(0).getId());
		map1.addValue("productId",bidDtos.get(0).getProductId());
		map1.addValue("bidderEmail", bidDtos.get(0).getBidderEmail());
		map1.addValue("bidAmount",bidDtos.get(0).getAmount());
		map1.addValue("createdDate",bidDtos.get(0).getCreatedDate());
		jdbcTemplate.update(sql1, map1);
		
		MapSqlParameterSource mapSql=new MapSqlParameterSource();
		mapSql.addValue("id", bidDtos.get(1).getId());
		mapSql.addValue("productId",bidDtos.get(1).getProductId());
		mapSql.addValue("bidderEmail", bidDtos.get(1).getBidderEmail());
		mapSql.addValue("bidAmount",bidDtos.get(1).getAmount());
		mapSql.addValue("createdDate",bidDtos.get(1).getCreatedDate());
		jdbcTemplate.update(sql1, mapSql);
		
		MapSqlParameterSource mapSqlParameter=new MapSqlParameterSource();
		mapSqlParameter.addValue("id", bidDtos.get(2).getId());
		mapSqlParameter.addValue("productId",bidDtos.get(2).getProductId());
		mapSqlParameter.addValue("bidderEmail", bidDtos.get(2).getBidderEmail());
		mapSqlParameter.addValue("bidAmount",bidDtos.get(2).getAmount());
		mapSqlParameter.addValue("createdDate",bidDtos.get(2).getCreatedDate());
		jdbcTemplate.update(sql1, mapSqlParameter);
		
		MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", bidDtos.get(3).getId());
		mapSqlParameterSource.addValue("productId",bidDtos.get(3).getProductId());
		mapSqlParameterSource.addValue("bidderEmail", bidDtos.get(3).getBidderEmail());
		mapSqlParameterSource.addValue("bidAmount",bidDtos.get(3).getAmount());
		mapSqlParameterSource.addValue("createdDate",bidDtos.get(3).getCreatedDate());
		jdbcTemplate.update(sql1, mapSqlParameterSource);
		return bidDtos;
	}

}
