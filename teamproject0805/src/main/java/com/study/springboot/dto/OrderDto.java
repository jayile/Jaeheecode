package com.study.springboot.dto;

import java.sql.Timestamp;

public class OrderDto {
	String pId;
	String mId;
	int cId;
	int pCount;
	int cPrice;
	int pPrice;
	String cName;
	String cPhoto1;
	Timestamp pDate;
	
	
	public OrderDto() {}


	public OrderDto(String pId, String mId, int cId, int pCount, int cPrice, int pPrice, Timestamp pDate, String cName,
			String cPhoto1) {
		super();
		this.pId = pId;
		this.mId = mId;
		this.cId = cId;
		this.pCount = pCount;
		this.cPrice = cPrice;
		this.pPrice = pPrice;
		this.pDate = pDate;
		this.cName = cName;
		this.cPhoto1 = cPhoto1;
	}

	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getpCount() {
		return pCount;
	}
	public void setpCount(int pCount) {
		this.pCount = pCount;
	}
	public int getcPrice() {
		return cPrice;
	}
	public void setcPrice(int cPrice) {
		this.cPrice = cPrice;
	}
	public int getpPrice() {
		return pPrice;
	}
	public void setpPrice(int pPrice) {
		this.pPrice = pPrice;
	}
	public Timestamp getpDate() {
		return pDate;
	}
	public void setpDate(Timestamp pDate) {
		this.pDate = pDate;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getcPhoto1() {
		return cPhoto1;
	}
	public void setcPhoto1(String cPhoto1) {
		this.cPhoto1 = cPhoto1;
	}

}
