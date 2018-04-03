package com.prokarma.opa.web.domain;

import java.util.Date;
import java.util.List;

public class AuctionVo {
	private int product_id;
	private String name;
	private String type;
	private int price;
	private Date expiredate;
	private String email;
	private String active;
	private Date createdDate;
	private byte[] image;
	private List<BidVo> bids;
	private int maxBidAmount; 

	public List<BidVo> getBids() {
		return bids;
	}


	public void setBids(List<BidVo> bids) {
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

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
