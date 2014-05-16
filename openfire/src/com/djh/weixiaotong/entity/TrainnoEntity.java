package com.djh.weixiaotong.entity;

/**
 * 培训机构号实体类
 * 
 * @author Administrator
 * 
 */
public class TrainnoEntity {
	private String trainNo; // 培训机构号
	private String trainLogo; // 培训机构logo(超链接)
	private String trainName; // 培训机构名称
	private String trainIntro; // 学校简介
	private String attentions; // 学校简介

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainLogo() {
		return trainLogo;
	}

	public void setTrainLogo(String trainLogo) {
		this.trainLogo = trainLogo;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainIntro() {
		return trainIntro;
	}

	public void setTrainIntro(String trainIntro) {
		this.trainIntro = trainIntro;
	}

	public String getAttentions() {
		return attentions;
	}

	public void setAttentions(String attentions) {
		this.attentions = attentions;
	}

	@Override
	public String toString() {
		return "TrainnoEntity [trainNo=" + trainNo + ", trainLogo=" + trainLogo
				+ ", trainName=" + trainName + ", trainIntro=" + trainIntro
				+ ", attentions=" + attentions + "]";
	}

}
