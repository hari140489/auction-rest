package com.prokarma.opa.repository;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.CompletedAuctionDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuctionRepositoryImplTest {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private AuctionRepository auctionRepository;

	@Test
	public void testSaveAuction() throws Exception {
		insertDummyUsers();

		AuctionDto auctionDto = new AuctionDto();

		auctionDto.setName("sony");
		auctionDto.setType("mobile");
		auctionDto.setMin_bid_amount(2000);
		auctionDto.setOwner_email("abc@gmail.com");
		auctionDto.setActive("Y");

		auctionRepository.saveAuction(auctionDto);

		String sql1 = "select name, type, min_bid_amount, owner_email, active from opa.product_auction where owner_email=:email";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", auctionDto.getOwner_email());

		AuctionDto auction = jdbcTemplate.queryForObject(sql1, parameters, new RowMapper<AuctionDto>() {

			@Override
			public AuctionDto mapRow(ResultSet resultSet, int row) throws SQLException {
				auctionDto.setActive(resultSet.getString("active"));
				auctionDto.setName(resultSet.getString("name"));
				auctionDto.setOwner_email(resultSet.getString("Owner_email"));
				auctionDto.setMin_bid_amount(resultSet.getInt("min_bid_amount"));
				auctionDto.setType(resultSet.getString("type"));
				return auctionDto;
			}

		});
		assertEquals(auctionDto.getName(), auction.getName());
		assertEquals(auctionDto.getActive(), auction.getActive());
		assertEquals(auctionDto.getMin_bid_amount(), auction.getMin_bid_amount());
		assertEquals(auctionDto.getOwner_email(), auction.getOwner_email());
		assertEquals(auctionDto.getType(), auction.getType());
	}

	@Test
	public void testFindAuctionsByEmailNameType() {
		insertDummyUsers();
		
		String email = "dv@gmail.com";
		String name = "Book";
		String type = "Book";

		AuctionDto auctionDto = new AuctionDto();
		auctionDto.setOwner_email("dv@gmail.com");
		auctionDto.setName("Book");
		auctionDto.setType("Book");
		auctionDto.setProduct_id(111);
		auctionDto.setMin_bid_amount(23213);
		auctionDto.setActive("Y");

		String sql = "insert into opa.product_auction(owner_email,name,type,product_id,min_bid_amount,active) values(:email,:name,:type,:productid,:amount,:active)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", "abc@gmail.com");
		parameters.addValue("type", "Book");
		parameters.addValue("name", "Book");
		parameters.addValue("productid", 111);
		parameters.addValue("amount", 23213);
		parameters.addValue("active", "Y");
		jdbcTemplate.update(sql, parameters);

		List<AuctionDto> auctionDtos = auctionRepository.viewAllProducts(email, name, type);

		assertNotEquals(auctionDto.getOwner_email(), auctionDtos.get(0).getOwner_email());
		assertEquals(auctionDto.getName(), auctionDtos.get(0).getName());
		assertEquals(auctionDto.getType(), auctionDtos.get(0).getType());
		assertEquals(auctionDto.getProduct_id(), auctionDtos.get(0).getProduct_id());
		assertEquals(auctionDto.getMin_bid_amount(), auctionDtos.get(0).getMin_bid_amount());
		assertEquals(auctionDto.getActive(), auctionDtos.get(0).getActive());

	}
	@Test
	public void testViewActiveAuctions_whenUserLoggedInAndSelectingMyActiveAuctions_thenDisplayAllTheActiveAuctions() throws Exception {
		insertDummyUsers();
		List<AuctionDto> auctionDtos = OnlineProductAuctionFixture.findActiveExpiredProducts();
		
		List<BidDto> bidDtos=new ArrayList<>();
		BidDto bidDto1=new BidDto();
		BidDto bidDto2=new BidDto();
		bidDto1.setBidderEmail("ab@c.com");
		bidDto1.setId(1);
		bidDto1.setProductId(202);
		bidDto1.setAmount(12000);
		
		bidDto2.setBidderEmail("abc@gmail.com");
		bidDto2.setId(1);
		bidDto2.setProductId(202);
		bidDto2.setAmount(12001);
		
		bidDtos.add(bidDto1);
		bidDtos.add(bidDto2);
		
		String sql = "insert into opa.product_auction(owner_email,name,type,product_id,min_bid_amount,active,image) values(:email,:name,:type,:productid,:amount,:active,:image)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", "dv@gmail.com");
		parameters.addValue("type", auctionDtos.get(2).getType());
		parameters.addValue("name", auctionDtos.get(2).getName());
		parameters.addValue("productid",auctionDtos.get(2).getProduct_id());
		parameters.addValue("amount", auctionDtos.get(2).getMin_bid_amount());
		parameters.addValue("active", auctionDtos.get(2).getActive());
		parameters.addValue("image",auctionDtos.get(2).getImage());
		jdbcTemplate.update(sql, parameters);		
	
	
		String sql1="insert into opa.bid(bidder_email,product_id,bid_id,bid_amount) values(:bidderEmail,:productId,:bidId,:bidAmount)";
		MapSqlParameterSource mapSql=new MapSqlParameterSource();
		mapSql.addValue("bidderEmail",bidDtos.get(0).getBidderEmail());
		mapSql.addValue("productId", bidDtos.get(0).getProductId());
		mapSql.addValue("bidId", bidDtos.get(0).getId());
		mapSql.addValue("bidAmount",bidDtos.get(0).getAmount());
		jdbcTemplate.update(sql1, mapSql);
		
		MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource();
		mapSqlParameterSource.addValue("bidderEmail",bidDtos.get(1).getBidderEmail());
		mapSqlParameterSource.addValue("productId", bidDtos.get(1).getProductId());
		mapSqlParameterSource.addValue("bidId", bidDtos.get(1).getId());
		mapSqlParameterSource.addValue("bidAmount",bidDtos.get(1).getAmount());
		
		List<AuctionDto> activeAuctions=auctionRepository.findActiveAuctionsByEmail("dv@gmail.com");
		
		assertEquals(1, activeAuctions.size());
		assertEquals(auctionDtos.get(2).getName(), activeAuctions.get(0).getName());
		assertEquals(auctionDtos.get(2).getProduct_id(), activeAuctions.get(0).getProduct_id());
		assertEquals(auctionDtos.get(2).getType(), activeAuctions.get(0).getType());
		assertEquals(bidDtos.get(0).getAmount(), activeAuctions.get(0).getMaxBidAmount());
		assertEquals(auctionDtos.get(2).getImage(), activeAuctions.get(0).getImage());
		
	}

	@Test
	public void testCompletedAuctionByEmail() throws Exception {

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
		
		
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("name", auctionDtos.get(2).getName());
		map.addValue("productId", 980);
		map.addValue("type",auctionDtos.get(2).getType());
		map.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		map.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		map.addValue("minBidAmount",72000);
		map.addValue("active","Y");
		map.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, map);
		insertDummyCompletedBids();

		List<CompletedAuctionDto> completedAuctionDto = auctionRepository.findCompletedAuctionsByEmail("ab@c.com");

		assertEquals(2, completedAuctionDto.size());
		assertEquals(863, completedAuctionDto.get(0).getProductId());
		assertEquals(70003, completedAuctionDto.get(0).getMaxBidAmount());
		assertEquals(55000, completedAuctionDto.get(0).getMinBidAmount());
		assertEquals(completedAuctionDto.get(0).getName(), auctionDtos.get(0).getName());
		assertEquals(963, completedAuctionDto.get(1).getProductId());
		assertEquals(80003, completedAuctionDto.get(1).getMaxBidAmount());
		assertEquals(65000, completedAuctionDto.get(1).getMinBidAmount());
		assertEquals(completedAuctionDto.get(1).getName(), auctionDtos.get(1).getName());

	}
	
	@Test
	public void testGetProductById() throws Exception {
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
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("name", auctionDtos.get(2).getName());
		map.addValue("productId", 980);
		map.addValue("type",auctionDtos.get(2).getType());
		map.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		map.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		map.addValue("minBidAmount",72000);
		map.addValue("active","Y");
		map.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, map);
		insertDummyCompletedBids();
		
		AuctionDto auctionDto = auctionRepository.getProductById(963);
		
		assertEquals(auctionDtos.get(1).getName(), auctionDto.getName());
		assertEquals(auctionDtos.get(1).getType(), auctionDto.getType());
		assertEquals(65000, auctionDto.getMin_bid_amount());
		assertEquals("ab@c.com", auctionDto.getOwner_email());

	}
	
	@Test
	public void testGetProductById_whenProductNotFound_thenReturnNull() throws Exception {
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
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("name", auctionDtos.get(2).getName());
		map.addValue("productId", 980);
		map.addValue("type",auctionDtos.get(2).getType());
		map.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		map.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		map.addValue("minBidAmount",72000);
		map.addValue("active","Y");
		map.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, map);
		insertDummyCompletedBids();
		
		AuctionDto auctionDto = auctionRepository.getProductById(964);
		
		assertEquals(null, auctionDto);

	}
	
	@Test
	public void testGetAuctionByIdWithMaximumBid() throws Exception {
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
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("name", auctionDtos.get(2).getName());
		map.addValue("productId", 980);
		map.addValue("type",auctionDtos.get(2).getType());
		map.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		map.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		map.addValue("minBidAmount",72000);
		map.addValue("active","Y");
		map.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, map);
		insertDummyCompletedBids();
		
		AuctionDto auctionDto = auctionRepository.getAuctionByIdWithMaximumBid(963);
		
		assertEquals(auctionDtos.get(1).getName(), auctionDto.getName());
		assertEquals(auctionDtos.get(1).getType(), auctionDto.getType());
		assertEquals(65000, auctionDto.getMin_bid_amount());
		assertEquals("ab@c.com", auctionDto.getOwner_email());
		assertEquals(80003, auctionDto.getBids().get(0).getAmount());		
	}

	@Test
	public void testGetAuctionByIdWithMaximumBid_whenProductNotFound_thenReturnNull() throws Exception {
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
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("name", auctionDtos.get(2).getName());
		map.addValue("productId", 980);
		map.addValue("type",auctionDtos.get(2).getType());
		map.addValue("expirationDate",auctionDtos.get(2).getExpiration_date());
		map.addValue("createdDate",auctionDtos.get(2).getCreated_date());
		map.addValue("minBidAmount",72000);
		map.addValue("active","Y");
		map.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, map);
		insertDummyCompletedBids();
		
		AuctionDto auctionDto = auctionRepository.getAuctionByIdWithMaximumBid(964);
		
		assertEquals(null, auctionDto);
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
	
	private List<AuctionDto> insertDummyProducts_andReturnExpiredAuctionIds() throws Exception {
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
		params1.addValue("ownerEmail","ab@c.com");
		jdbcTemplate.update(retrieveProductDetailsSql, params1);
		
		
		MapSqlParameterSource params2=new MapSqlParameterSource();
		params2.addValue("name", auctionDtos.get(1).getName());
		params2.addValue("productId", auctionDtos.get(1).getProduct_id());
		params2.addValue("type",auctionDtos.get(1).getType());
		params2.addValue("expirationDate",auctionDtos.get(1).getExpiration_date());
		params2.addValue("createdDate",auctionDtos.get(1).getCreated_date());
		params2.addValue("minBidAmount",auctionDtos.get(1).getMaxBidAmount());
		params2.addValue("active", "N");
		params2.addValue("ownerEmail","ab@c.com");
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
		
		return auctionDtos;
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
}

