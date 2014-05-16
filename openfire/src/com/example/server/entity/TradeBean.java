package com.example.server.entity;

public class TradeBean {
	private String typeName;
	private String typeUrl;
	private String goodName;
	private String goodInfo;
	private String goodDetail;
	//信誉
	private String goodWill;
	//运费
	private String goodFare;
	private String goodAddr;
	//库存
	private String goodStock;
	//买家
	private String buyerInfo;
	//卖家
	private String sellerInfo;
	private String tradeUrl;
	private float goodPrice;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeUrl() {
		return typeUrl;
	}
	public void setTypeUrl(String typeUrl) {
		this.typeUrl = typeUrl;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodInfo() {
		return goodInfo;
	}
	public void setGoodInfo(String goodInfo) {
		this.goodInfo = goodInfo;
	}
	public float getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(float goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getGoodDetail() {
		return goodDetail;
	}
	public void setGoodDetail(String goodDetail) {
		this.goodDetail = goodDetail;
	}
	public String getGoodWill() {
		return goodWill;
	}
	public void setGoodWill(String goodWill) {
		this.goodWill = goodWill;
	}
	public String getGoodFare() {
		return goodFare;
	}
	public void setGoodFare(String goodFare) {
		this.goodFare = goodFare;
	}
	public String getGoodAddr() {
		return goodAddr;
	}
	public void setGoodAddr(String goodAddr) {
		this.goodAddr = goodAddr;
	}
	public String getGoodStock() {
		return goodStock;
	}
	public void setGoodStock(String goodStock) {
		this.goodStock = goodStock;
	}
	public String getBuyerInfo() {
		return buyerInfo;
	}
	public void setBuyerInfo(String buyerInfo) {
		this.buyerInfo = buyerInfo;
	}
	public String getSellerInfo() {
		return sellerInfo;
	}
	public void setSellerInfo(String sellerInfo) {
		this.sellerInfo = sellerInfo;
	}
	public String getTradeUrl() {
		return tradeUrl;
	}
	public void setTradeUrl(String tradeUrl) {
		this.tradeUrl = tradeUrl;
	}
	
}
