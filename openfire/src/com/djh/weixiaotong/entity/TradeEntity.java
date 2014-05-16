package com.djh.weixiaotong.entity;

import java.io.Serializable;

/**
 * 交易记录实体类
 * @author Administrator
 *
 */
public class TradeEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String month; 	 // 月份
	private double income; // 收入
	private double outlay;   // 支出

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getOutlay() {
		return outlay;
	}

	public void setOutlay(double outlay) {
		this.outlay = outlay;
	}

	@Override
	public String toString() {
		return "TradeEntity [month=" + month + ", income=" + income
				+ ", outlay=" + outlay + "]";
	}

}
