package com.study.springboot.dto;

import java.sql.Timestamp;

public class PDto
{
	String pNum;
	String pId;
	String mId;
	String totalprice;
	String paddress;
	String pdeliverymemo;
	Timestamp pDate;
	
	public PDto() {}
	
	public PDto(String pNum, String pId, String mId, String totalprice, String paddress, String pdeliverymemo,
			Timestamp pDate)
	{
		super();
		this.pNum = pNum;
		this.pId = pId;
		this.mId = mId;
		this.totalprice = totalprice;
		this.paddress = paddress;
		this.pdeliverymemo = pdeliverymemo;
		this.pDate = pDate;
	}
	public String getpNum()
	{
		return pNum;
	}
	public void setpNum(String pNum)
	{
		this.pNum = pNum;
	}
	public String getpId()
	{
		return pId;
	}
	public void setpId(String pId)
	{
		this.pId = pId;
	}
	public String getmId()
	{
		return mId;
	}
	public void setmId(String mId)
	{
		this.mId = mId;
	}
	public String getTotalprice()
	{
		return totalprice;
	}
	public void setTotalprice(String totalprice)
	{
		this.totalprice = totalprice;
	}
	public String getPaddress()
	{
		return paddress;
	}
	public void setPaddress(String paddress)
	{
		this.paddress = paddress;
	}
	public String getPdeliverymemo()
	{
		return pdeliverymemo;
	}
	public void setPdeliverymemo(String pdeliverymemo)
	{
		this.pdeliverymemo = pdeliverymemo;
	}
	public Timestamp getpDate()
	{
		return pDate;
	}
	public void setpDate(Timestamp pDate)
	{
		this.pDate = pDate;
	}
	
	
	
}
