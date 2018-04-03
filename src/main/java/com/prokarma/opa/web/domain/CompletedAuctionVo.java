package com.prokarma.opa.web.domain;

public class CompletedAuctionVo {
	private String name;

	private int minBidAmount;

	private int productId;

	private String bidderName;

	private int maxBidAmount;

	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinBidAmount() {
		return minBidAmount;
	}

	public void setMinBidAmount(int minBidAmount) {
		this.minBidAmount = minBidAmount;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getBidderName() {
		return bidderName;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public int getMaxBidAmount() {
		return maxBidAmount;
	}

	public void setMaxBidAmount(int maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
