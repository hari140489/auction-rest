package com.prokarma.opa.fixture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.prokarma.opa.repository.model.AccessTokenDto;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.BidDto;
import com.prokarma.opa.repository.model.BuyerBidDto;
import com.prokarma.opa.repository.model.CompletedAuctionDto;
import com.prokarma.opa.repository.model.RegisterUserDto;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.BidVo;
import com.prokarma.opa.web.domain.BuyerBidVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;
import com.prokarma.opa.web.domain.LoginRequestVo;
import com.prokarma.opa.web.domain.RegistrationForm;

public final class OnlineProductAuctionFixture {

	private OnlineProductAuctionFixture() {
		throw new AssertionError("Cannot create instance");
	}

	public static UserDto userDto() {
		UserDto userDto = new UserDto();
		userDto.setEmail("");
		return userDto;
	}
	
	public static UserDto user() {
		UserDto userdto = new UserDto();
		userdto.setEmail("nishal@prokarma.com");
		userdto.setName("Nishal");
		userdto.setPassword("S@nhiser");
		userdto.setToken("abcde123");
		return userdto;
	}
	
	public static RegistrationForm registrationForm() {
		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.setEmail("user@mail.com");
		registrationForm.setName("user");
		registrationForm.setPassword("P@ssw0rd");
		registrationForm.setConfirmPassword("P@ssw0rd");
		registrationForm.setAddressLineOne("address line 1");
		registrationForm.setAddressLineTwo("address line 2");
		registrationForm.setCity("city");
		registrationForm.setState("state");
		registrationForm.setPincode("123456");
		registrationForm.setNumber("8969658547");
		return registrationForm;
	}
	
	public static RegisterUserDto registerUserDto() {
		RegisterUserDto registerUserDto = new RegisterUserDto();
		registerUserDto.setEmail("user@mail.com");
		registerUserDto.setName("user");
		registerUserDto.setPassword("P@ssw0rd");
		registerUserDto.setAddressLineOne("address line 1");
		registerUserDto.setAddressLineTwo("address line 2");
		registerUserDto.setCity("city");
		registerUserDto.setState("state");
		registerUserDto.setPincode("123456");
		registerUserDto.setMobileNo("8969658547");
		return registerUserDto;
	}
	
	public static LoginRequestVo requestVo() {
		LoginRequestVo userVo = new LoginRequestVo();
		userVo.setEmail("nishal@prokarma.com");
		userVo.setPassword("S@nhiser");
		return userVo;
		
	}
	
	public static String createBidJson() {
		StringBuilder createBidJson = new StringBuilder("{");
		createBidJson.append("\"amount\": 2000,\r\n");	
		createBidJson.append("\"productId\": 27");
		createBidJson.append("}");
		return createBidJson.toString();
	}

	public static BidVo bidVo() {
		BidVo bidVo = new BidVo();
		bidVo.setId(1l);
		bidVo.setBidderEmail("bidder@mail.com");
		bidVo.setAmount(35000);
		bidVo.setProductId(1l);
		return bidVo;
	}
	
	public static BidDto bidDto() {
		BidDto bidDto = new BidDto();
		bidDto.setId(1l);
		bidDto.setProductId(1l);
		bidDto.setBidderEmail("bidder@mail.com");
		bidDto.setCreatedDate(new Date());
		return bidDto;
	}
	
    public static BuyerBidVo buyerBidVo() {
    	BuyerBidVo buyerBidVo = new BuyerBidVo();
    	buyerBidVo.setProductId(9001);
		buyerBidVo.setProductName("product1");
		buyerBidVo.setBidId(16003);
		buyerBidVo.setBidderName("bidder1@mail.com");
		buyerBidVo.setActualAmount(200);
		buyerBidVo.setBuyerBidAmount(350);
		buyerBidVo.setMaximumBidAmount(450);
		return buyerBidVo;
    }
    
    public static BuyerBidDto buyerBidDto() {
    	BuyerBidDto buyerBidDto = new BuyerBidDto();
    	buyerBidDto.setProductId(9001);
    	buyerBidDto.setProductName("product1");
		buyerBidDto.setBidId(16003);
		buyerBidDto.setBidderName("bidder1@mail.com");
		buyerBidDto.setActualAmount(200);
		buyerBidDto.setBuyerBidAmount(350);
		buyerBidDto.setMaximumBidAmount(450);
		
		return buyerBidDto;
    }
	
	public static List<BuyerBidVo> buyerBidVoList() {
		List<BuyerBidVo> buyerBidVos = new ArrayList<>();
		
		BuyerBidVo buyerBidVo = new BuyerBidVo();
		buyerBidVo.setProductId(9001);
		buyerBidVo.setProductName("product1");
		buyerBidVo.setBidId(16003);
		buyerBidVo.setBidderName("bidder1@mail.com");
		buyerBidVo.setImage(new byte[] {0x14, 0x20, 0x33});
		buyerBidVo.setActualAmount(200);
		buyerBidVo.setBuyerBidAmount(350);
		buyerBidVo.setMaximumBidAmount(450);
		buyerBidVos.add(buyerBidVo);
		
		buyerBidVo = new BuyerBidVo();
		buyerBidVo.setProductId(9002);
		buyerBidVo.setProductName("product2");
		buyerBidVo.setBidId(16005);
		buyerBidVo.setBidderName("bidder2@mail.com");
		buyerBidVo.setImage(new byte[] {0x17, 0x3D, 0x7E});
		buyerBidVo.setActualAmount(380);
		buyerBidVo.setBuyerBidAmount(590);
		buyerBidVo.setMaximumBidAmount(680);
		buyerBidVos.add(buyerBidVo);

		buyerBidVo = new BuyerBidVo();
		buyerBidVo.setProductId(9003);
		buyerBidVo.setProductName("product3");
		buyerBidVo.setBidId(16008);
		buyerBidVo.setBidderName("bidder3@mail.com");
		buyerBidVo.setImage(new byte[] {0x6A, 0x0B, 0x12});
		buyerBidVo.setActualAmount(440);
		buyerBidVo.setBuyerBidAmount(780);
		buyerBidVo.setMaximumBidAmount(1100);
		buyerBidVos.add(buyerBidVo);
		
		return buyerBidVos;
	}
	
	public static List<BuyerBidDto> buyerBidDtoList() {
		List<BuyerBidDto> buyerBidDtos = new ArrayList<>();
		
		BuyerBidDto buyerBidDto = new BuyerBidDto();
		
		buyerBidDto.setProductId(9001);
		buyerBidDto.setProductName("product1");
		buyerBidDto.setBidId(16003);
		buyerBidDto.setBidderName("bidder1@mail.com");
		buyerBidDto.setImage(new byte[] {0x14, 0x20, 0x33});
		buyerBidDto.setActualAmount(200);
		buyerBidDto.setBuyerBidAmount(350);
		buyerBidDto.setMaximumBidAmount(450);
		buyerBidDtos.add(buyerBidDto);
		
		buyerBidDto = new BuyerBidDto();
		buyerBidDto.setProductId(9002);
		buyerBidDto.setProductName("product2");
		buyerBidDto.setBidId(16005);
		buyerBidDto.setBidderName("bidder2@mail.com");
		buyerBidDto.setImage(new byte[] {0x17, 0x3D, 0x7E});
		buyerBidDto.setActualAmount(380);
		buyerBidDto.setBuyerBidAmount(590);
		buyerBidDto.setMaximumBidAmount(680);
		buyerBidDtos.add(buyerBidDto);

		buyerBidDto = new BuyerBidDto();
		buyerBidDto.setProductId(9003);
		buyerBidDto.setProductName("product3");
		buyerBidDto.setBidId(16008);
		buyerBidDto.setBidderName("bidder3@mail.com");
		buyerBidDto.setImage(new byte[] {0x6A, 0x0B, 0x12});
		buyerBidDto.setActualAmount(440);
		buyerBidDto.setBuyerBidAmount(780);
		buyerBidDto.setMaximumBidAmount(1100);
		buyerBidDtos.add(buyerBidDto);
		
		return buyerBidDtos;
	}
	
	

	public static LoginRequestVo loginRequestVo() {
		LoginRequestVo userVo = new LoginRequestVo();
		userVo.setEmail("saihussain@prokarma.com");
		userVo.setPassword("lS@i1994");
		return userVo;
	}
	
	public static UserDto dto(LoginRequestVo userVo) {
		UserDto userDto = new UserDto();
		userDto.setEmail(userVo.getEmail());
		userDto.setPassword(userVo.getPassword());
		userDto.setToken("abcde");
		return userDto;
		
	}
	
	public static AccessTokenDto accessTokenDto() {
		AccessTokenDto accessTokenDto = new AccessTokenDto();
		accessTokenDto.setEmail("dv@gmail.com");
		accessTokenDto.setToken("o182edsciusya87dQR98");
		return accessTokenDto;
	}
	

	public static AuctionVo auctionVo() throws Exception {
		AuctionVo auctionVo = new AuctionVo();
		auctionVo.setActive("Y");
		BidVo bidVo = new BidVo();
		bidVo.setAmount(100001);
		bidVo.setBidderEmail("xyz@gmail.com");
		bidVo.setId(3);
		bidVo.setProductId(1);
		auctionVo.setBids(Collections.singletonList(bidVo));
		auctionVo.setEmail("abc@gmail.com");
		auctionVo.setName("iPhone X");
		auctionVo.setPrice(100000);
		auctionVo.setProduct_id(1);
		auctionVo.setType("Mobile");
		auctionVo.setImage(new byte[] {0x14, 0x25, 0x59});
		SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
	    auctionVo.setExpiredate(dateFormat.parse("28/03/2095"));
	    auctionVo.setCreatedDate(dateFormat.parse("19/03/2018"));
		
		return auctionVo;
	}
	
	public static 	List<BuyerBidVo> buyerBidVos(){
		
     List<BuyerBidVo> buyerBidVos = new ArrayList<BuyerBidVo>();
		
		BuyerBidVo buyerBidVo = new BuyerBidVo();
		
		buyerBidVo.setProductName("Lenovo K6");
		buyerBidVo.setActualAmount(1200);
		buyerBidVo.setBidderName("Likhitha");
		buyerBidVo.setMaximumBidAmount(1300);
		
		BuyerBidVo buyerBidVo1 = new BuyerBidVo();
		buyerBidVo1.setProductName("Sony");
		buyerBidVo1.setActualAmount(1500);
		buyerBidVo1.setBidderName("Bablu");
		buyerBidVo1.setMaximumBidAmount(2000);
		
		
		buyerBidVos.add(buyerBidVo);
		buyerBidVos.add(buyerBidVo1);
		
		return buyerBidVos;
		
	}
	public static AuctionDto auctionDto() {
		AuctionDto auctionDto= new AuctionDto();
		
		auctionDto.setProduct_id(1);
		auctionDto.setActive("y");
		auctionDto .setName("samsung");
		auctionDto.setType("moble");
		auctionDto.setMaxBidAmount(50000);
		auctionDto.setImage(new byte[] {0x14, 0x20, 0x33});
		auctionDto.setOwner_email("saihussain666@gmail.com");
	
		return auctionDto;
		
	}
	
	public static AuctionVo auctionVO() {
		AuctionVo auctionVo= new AuctionVo();
		
		auctionVo.setProduct_id(1);
		auctionVo.setActive("Y");
		auctionVo .setName("samsung");
		auctionVo.setType("moble");
		auctionVo.setMaxBidAmount(50000);
		auctionVo.setImage(new byte[] {0x14, 0x20, 0x33});
		auctionVo.setEmail("saihussain666@gmail.com");
		auctionVo.setPrice(49000);
	
		return auctionVo;
		
	}
	
	public static 	List<AuctionVo> auctionVos(){
		List<AuctionVo> auctionVos=new ArrayList<>();
		AuctionVo auctionVo =new AuctionVo();
		auctionVo.setName("Lenovo");
		auctionVo.setType("Mobile");
		auctionVo.setPrice(10000);
		auctionVo.setActive("Y");
		auctionVo.setImage(new byte[] {0x14, 0x20, 0x33});
		auctionVo.setEmail("saihussain666@gmail.com");
		auctionVos.add(auctionVo);
		
		return auctionVos;
	}
	

  public static List<AuctionDto> findActiveExpiredProducts() throws Exception{
	  List<AuctionDto>auctionDtos=new ArrayList<AuctionDto>();
		AuctionDto auctionDto1=new AuctionDto();
		AuctionDto auctionDto2=new AuctionDto();
		AuctionDto auctionDto3=new AuctionDto();
		auctionDto1.setName("Samsung J5");
		auctionDto1.setProduct_id(200);
	    auctionDto1.setType("Mobile");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
	    auctionDto1.setExpiration_date(dateFormat.parse("23/03/2018"));
	    auctionDto1.setCreated_date(dateFormat.parse("19/03/2018"));
	    auctionDto1.setMin_bid_amount(7000);
	    auctionDto1.setOwner_email("sammy@gmail.com");
	    auctionDto1.setActive("Y");
	    
	    
	    auctionDto2.setName("Samsung J2");
	    auctionDto2.setProduct_id(201);
	    auctionDto2.setType("Mobile");
	    auctionDto2.setExpiration_date(dateFormat.parse("23/03/2018"));
	    auctionDto2.setCreated_date(dateFormat.parse("20/03/2018"));
	    auctionDto2.setMin_bid_amount(8000);
	    auctionDto2.setOwner_email("nishal@prokarma.com");
	    auctionDto2.setActive("Y");
	    
	    auctionDto3.setName("Samsung J3");
	    auctionDto3.setProduct_id(202);
	    auctionDto3.setType("Mobile");
	    auctionDto3.setExpiration_date(dateFormat.parse("25/03/2018"));
	    auctionDto3.setCreated_date(dateFormat.parse("21/03/2018"));
	    auctionDto3.setMin_bid_amount(9000);
	    auctionDto3.setOwner_email("saihussain@prokarma.com");
	    auctionDto3.setActive("Y");
	    auctionDto1.setImage(new byte[] {0x14, 0x20, 0x33});
	    
	    auctionDtos.add(auctionDto1);
	    auctionDtos.add(auctionDto2);
	    auctionDtos.add(auctionDto3);
		return auctionDtos;
	  
  }

	public static 	List<AuctionDto> auctionDtos(){
		List<AuctionDto> auctionDtos=new ArrayList<>();
		AuctionDto auctionDto =new AuctionDto();
		auctionDto.setName("Lenovo");
		auctionDto.setType("Mobile");
		auctionDto.setMin_bid_amount(10000);
		auctionDto.setActive("Y");
		auctionDto.setImage(new byte[] {0x14, 0x20, 0x33});
		auctionDto.setOwner_email("saihussain666@gmail.com");
		auctionDtos.add(auctionDto);
		
		return auctionDtos;
	}

	
	public static List<BidVo> bidVos() {
		BidVo bidVo = new BidVo();
		bidVo.setId(1l);
		bidVo.setBidderEmail("bidder@mail.com");
		bidVo.setAmount(35000);
		bidVo.setProductId(1l);
		
		BidVo bidVo1 = new BidVo();
		bidVo1.setId(2l);
		bidVo1.setBidderEmail("user@mail.com");
		bidVo1.setAmount(3500);
		bidVo1.setProductId(2l);
		
		List<BidVo> bidVos = new ArrayList<>();
		bidVos.add(bidVo);
		bidVos.add(bidVo1);
		
		return bidVos;
	}
	
	public static List<BidDto> bidDtos() {
		BidDto bidDto = new BidDto();
		bidDto.setId(1l);
		bidDto.setBidderEmail("bidder@mail.com");
		bidDto.setAmount(35000);
		bidDto.setProductId(1l);
		
		BidDto bidDto1 = new BidDto();
		bidDto1.setId(1l);
		bidDto1.setBidderEmail("bidder@mail.com");
		bidDto1.setAmount(35000);
		bidDto1.setProductId(1l);
		
		List<BidDto> bidDtos = new ArrayList<>();
		bidDtos.add(bidDto);
		bidDtos.add(bidDto1);
		
		return bidDtos;
	}
	



	public static CompletedAuctionVo completedAuctionVo() {
		CompletedAuctionVo completedAuctionVo= new CompletedAuctionVo();
		completedAuctionVo.setName("samsung");
		completedAuctionVo.setMinBidAmount(5000);
		completedAuctionVo.setBidderName("saihussain");
		completedAuctionVo.setMaxBidAmount(10000);
		completedAuctionVo.setEmail("saihussain666@gmail.com");
		return completedAuctionVo;
		
	}
	public static CompletedAuctionDto completedAuctionDto() {
		CompletedAuctionDto completedAuctionDto= new CompletedAuctionDto();
		completedAuctionDto.setName("samsung");
		completedAuctionDto.setMinBidAmount(5000);
		completedAuctionDto.setBidderName("saihussain");
		completedAuctionDto.setMaxBidAmount(10000);
		completedAuctionDto.setEmail("saihussain666@gmail.com");
		return completedAuctionDto;
		
	}

	public static 	List<CompletedAuctionVo> completedAuctionVos(){
		List<CompletedAuctionVo> completedAuctionVos=new ArrayList<>();
		CompletedAuctionVo completedAuctionVo =new CompletedAuctionVo();
		completedAuctionVo.setName("samsung");
		completedAuctionVo.setMinBidAmount(5000);
		completedAuctionVo.setBidderName("saihussain");
		completedAuctionVo.setMaxBidAmount(10000);
		completedAuctionVo.setEmail("saihussain666@gmail.com");
		completedAuctionVos.add(completedAuctionVo);
		
		return completedAuctionVos;
	}
	
	public static 	List<CompletedAuctionDto> completedAuctionDtos(){
		List<CompletedAuctionDto> completedAuctionDtos=new ArrayList<>();
		CompletedAuctionDto completedAuctionDto =new CompletedAuctionDto();
		completedAuctionDto.setName("samsung");
		completedAuctionDto.setMinBidAmount(5000);
		completedAuctionDto.setBidderName("saihussain");
		completedAuctionDto.setMaxBidAmount(10000);
		completedAuctionDto.setEmail("saihussain666@gmail.com");
		completedAuctionDtos.add(completedAuctionDto);
		
		return completedAuctionDtos;
	}
	
	
}
