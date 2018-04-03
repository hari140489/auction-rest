package com.prokarma.opa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prokarma.opa.exception.UnauthenticatedException;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.service.AuctionService;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;
import com.prokarma.opa.web.domain.GenericResponse;


@RestController
@RequestMapping("/auction")
public class AuctionController {

	@Autowired
	private AuctionService auctionService;

	@RequestMapping(value = "/active-auctions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AuctionVo> viewActiveAuctions(@AuthenticationPrincipal final UserDto userDto) {
		if (userDto == null) {
			throw new UnauthenticatedException("User not authenticated");
		}
		return auctionService.findMyActiveAuctions(userDto.getEmail());

	}

	@PostMapping(value = "/create-auction")
	public ResponseEntity<GenericResponse> createAuction(@RequestBody AuctionVo auctionVo,
			@AuthenticationPrincipal final UserDto userDto) {
		if (userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		auctionVo.setEmail(userDto.getEmail());
		auctionService.addAuction(auctionVo);
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setMessage("User created successfully");
		return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
	}
	
	@GetMapping("/view-auctions")
	public  ResponseEntity<List<AuctionVo>> listallproducts(@AuthenticationPrincipal final UserDto userDto,@RequestParam(value="name",required=false) String name,@RequestParam(value="type",required=false) String type ) {
		if(userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		List<AuctionVo> viewAuctions =auctionService.viewProducts(userDto.getEmail(),name,type);
		return new ResponseEntity<>(viewAuctions,HttpStatus.OK);
	}
	
	@GetMapping(value ="/completed-auction")
	public List<CompletedAuctionVo> completedAuction(@AuthenticationPrincipal final UserDto userDto){
		if(userDto==null) {
			throw new UnauthenticatedException("User not logged in");
		}
		return auctionService.retrieveCompletedAuctions(userDto.getEmail());
	}
	
	@GetMapping("/get-auction-max-bid/{productId}")
	public AuctionVo getAuctionByIdWithMaximumBid(@AuthenticationPrincipal final UserDto userDto, @PathVariable("productId") long productId) {
		if(userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		return auctionService.findAuctionByIdWithMaximumBid(productId);
	}
}
