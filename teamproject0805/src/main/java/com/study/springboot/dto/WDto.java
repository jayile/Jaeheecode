package com.study.springboot.dto;

import java.sql.Timestamp;

public class WDto {
	private String mid;
	private int cId;
	private Timestamp wDate;
	
	public WDto() {}

	public WDto(String mid, int cId, Timestamp wDate)
	{
		super();
		this.mid = mid;
		this.cId = cId;
		this.wDate = wDate;
	}

	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public Timestamp getwDate()
	{
		return wDate;
	}

	public void setwDate(Timestamp wDate)
	{
		this.wDate = wDate;
	}

}
