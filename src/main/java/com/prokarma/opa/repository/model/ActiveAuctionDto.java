package com.prokarma.opa.repository.model;

public class ActiveAuctionDto {
	

	private String productName;
	
	private int productId;
	
	private String type;
	
	private int maxBidAmount;
	
	private byte[] image;
	
	private float price;
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setImage(byte byte1) {

	}

	public int getMaxBidAmount() {
		return maxBidAmount;
	}

	public void setMaxBidAmount(int maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

}
