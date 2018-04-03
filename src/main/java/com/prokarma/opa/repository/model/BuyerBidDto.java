package com.prokarma.opa.repository.model;

import java.util.Date;

public class BuyerBidDto {

	private long productId;
	private String productName;
	private long bidId;
	private byte[] image;
	private String bidderName;
	private int actualAmount;
	private int buyerBidAmount;
	private int maximumBidAmount;
	private Date bidCreatedDate;


	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getBidId() {
		return bidId;
	}

	public void setBidId(long bidId) {
		this.bidId = bidId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getBidderName() {
		return bidderName;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public int getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(int actualAmount) {
		this.actualAmount = actualAmount;
	}

	public int getBuyerBidAmount() {
		return buyerBidAmount;
	}

	public void setBuyerBidAmount(int buyerBidAmount) {
		this.buyerBidAmount = buyerBidAmount;
	}

	public int getMaximumBidAmount() {
		return maximumBidAmount;
	}

	public void setMaximumBidAmount(int maximumBidAmount) {
		this.maximumBidAmount = maximumBidAmount;
	}
	
	public Date getBidCreatedDate() {
		return bidCreatedDate;
	}

	public void setBidCreatedDate(Date bidCreatedDate) {
		this.bidCreatedDate = bidCreatedDate;
	}

}
