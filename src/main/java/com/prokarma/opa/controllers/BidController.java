package com.prokarma.opa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prokarma.opa.exception.UnauthenticatedException;
import com.prokarma.opa.repository.model.UserDto;
import com.prokarma.opa.service.BidService;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.BidVo;
import com.prokarma.opa.web.domain.BuyerBidVo;
import com.prokarma.opa.web.domain.GenericResponse;

@RestController
@RequestMapping("/bid")
public class BidController {
	
	private BidService bidService;

	@Autowired
	public BidController(BidService bidService) {
		this.bidService = bidService;
	}
	
	@PostMapping("/create-bid")
	public ResponseEntity<GenericResponse> createBid(@AuthenticationPrincipal final UserDto userDto, @RequestBody BidVo bidVo) {
		if(userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		bidService.createBid(bidVo, userDto);
		GenericResponse response = new GenericResponse();
		response.setMessage("Bid created successfully");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/view-all-bids")
	public ResponseEntity<AuctionVo> viewBids(@AuthenticationPrincipal final UserDto userDto, @RequestParam(value="productId") int productId) {
		if(userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		AuctionVo auctionVo = bidService.getBidsByProduct(productId);
		return new ResponseEntity<>(auctionVo, HttpStatus.OK);
	}
	
	@GetMapping("/my-active-bids")
	public ResponseEntity<List<BuyerBidVo>> myActiveBids(@AuthenticationPrincipal final UserDto userDto) {
		if (userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		List<BuyerBidVo> buyerBids = bidService.getActiveBidsByBuyerEmail(userDto.getEmail());
		return new ResponseEntity<>(buyerBids, HttpStatus.OK);
	}
	
	@GetMapping("/view-my-completed-bids")
	public ResponseEntity<List<BuyerBidVo>> listallproducts(@AuthenticationPrincipal final UserDto userDto) {
		if(userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		List<BuyerBidVo> buyerCompletedBids = bidService.findMyCompletedBids(userDto.getEmail());
		return new ResponseEntity<>(buyerCompletedBids,HttpStatus.OK);
	}
	
	@GetMapping("/get-bid-by-product-for-user/{productId}")
	public ResponseEntity<BidVo> getBidStatusByProduct(@PathVariable("productId") long productId, @AuthenticationPrincipal final UserDto userDto) {
		if(userDto == null) {
			throw new UnauthenticatedException("User not logged in");
		}
		return new ResponseEntity<>(bidService.getBidByProductForUser(userDto.getEmail(), productId), HttpStatus.OK);
	}
	
	
}
