package com.study.springboot.dto;

import java.sql.Timestamp;

public class BlackDto
{
	private String mid;
	private int btype;
	private Timestamp btime;
	private String bmemo;
	
	public BlackDto() {}
	
	public BlackDto(String mid, int btype, Timestamp btime, String bmemo)
	{
		super();
		this.mid = mid;
		this.btype = btype;
		this.btime = btime;
		this.bmemo = bmemo;
	}
	
	public String getMid()
	{
		return mid;
	}
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	public int getBtype()
	{
		return btype;
	}
	public void setBtype(int btype)
	{
		this.btype = btype;
	}
	public Timestamp getBtime()
	{
		return btime;
	}
	public void setBtime(Timestamp btime)
	{
		this.btime = btime;
	}
	public String getBmemo()
	{
		return bmemo;
	}
	public void setBmemo(String bmemo)
	{
		this.bmemo = bmemo;
	}
}
