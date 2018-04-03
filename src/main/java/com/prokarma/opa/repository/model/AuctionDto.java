package com.prokarma.opa.repository.model;

import java.util.Date;
import java.util.List;

public class AuctionDto {

	private int product_id;
	private String name;
	private String type;
	private int min_bid_amount;
	private Date expiration_date;
	private String owner_email;
	private String active;
	private Date created_date;
	private byte[] image;
	private List<BidDto> bids;
	private int maxBidAmount;
	
	

	public List<BidDto> getBids() {
		return bids;
	}

	public void setBids(List<BidDto> bids) {
		this.bids = bids;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMin_bid_amount() {
		return min_bid_amount;
	}

	public void setMin_bid_amount(int min_bid_amount) {
		this.min_bid_amount = min_bid_amount;
	}

	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getOwner_email() {
		return owner_email;
	}

	public void setOwner_email(String owner_email) {
		this.owner_email = owner_email;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	public int getMaxBidAmount() {
		return maxBidAmount;
	}

	public void setMaxBidAmount(int maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}

}
