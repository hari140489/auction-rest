package com.prokarma.opa.service.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.prokarma.opa.exception.InvalidCreateAuctionException;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.web.domain.AuctionVo;

public class AuctionValidatorImplTest {

	private AuctionValidator auctionValidator;
	
	private AuctionVo auctionVo;
	
	@Before
	public void setUp() throws Exception {
		auctionValidator = new AuctionValidatorImpl();

		auctionVo = OnlineProductAuctionFixture.auctionVo();
	}
	
	@Test
	public void testValidate() {
		auctionValidator.validate(auctionVo);
	}
	
	@Test
	public void whenNameFieldIsEmpty_ReturnEmptyNameError() {
		auctionVo.setName("");

		InvalidCreateAuctionException exception = assertThrows(InvalidCreateAuctionException.class,
				() -> auctionValidator.validate(auctionVo));

		assertEquals(1, exception.getErrors().size());
		assertEquals("name", exception.getErrors().get(0).getField());
		assertEquals("Name cannot be empty", exception.getErrors().get(0).getMessage());
	}
	
	@Test
	public void  whenTypeIsEmpty_ReturnEmptyTypeError() {
		auctionVo.setType("");
		
		InvalidCreateAuctionException exception = assertThrows(InvalidCreateAuctionException.class,
				() -> auctionValidator.validate(auctionVo));

		assertEquals(1, exception.getErrors().size());
		assertEquals("type", exception.getErrors().get(0).getField());
		assertEquals("type cannot be empty", exception.getErrors().get(0).getMessage());
	}
	
	@Test
	public void  whenPriceIsNegative_ReturnNegativePriceError() {
		auctionVo.setPrice(-1);
		
		InvalidCreateAuctionException exception = assertThrows(InvalidCreateAuctionException.class,
				() -> auctionValidator.validate(auctionVo));

		assertEquals(1, exception.getErrors().size());
		assertEquals("price", exception.getErrors().get(0).getField());
		assertEquals("price must be positive number", exception.getErrors().get(0).getMessage());
	}
	
	@Test
	public void whenDateisEmpty_ReturnEmptyDateError() {
		auctionVo.setExpiredate(null);
		
		InvalidCreateAuctionException exception = assertThrows(InvalidCreateAuctionException.class,
				() -> auctionValidator.validate(auctionVo));

		assertEquals(1, exception.getErrors().size());
		assertEquals("expiredate", exception.getErrors().get(0).getField());
		assertEquals("date cannot be empty", exception.getErrors().get(0).getMessage());
	}
	
	@Test
	public void whenImageisEmpty_ReturnEmptyImageError() {
		byte[] b = {};
		auctionVo.setImage(b);
		
		InvalidCreateAuctionException exception = assertThrows(InvalidCreateAuctionException.class,
				() -> auctionValidator.validate(auctionVo));

		assertEquals(1, exception.getErrors().size());
		assertEquals("image", exception.getErrors().get(0).getField());
		assertEquals("image cannot be empty", exception.getErrors().get(0).getMessage());
	}
}
