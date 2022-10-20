package com.study.jsp.dto;

import java.sql.Timestamp;


public class RDto {
	
	private String rId;
	private String bId;
	private String rName;
	private String rContent;
	private Timestamp rDate;
	
    public RDto() {
    }
    
	public RDto(String rId, String bId, String rName, String rContent, Timestamp rDate)
	{
		super();
		this.rId = rId;
		this.bId = bId;
		this.rName = rName;
		this.rContent = rContent;
		this.rDate = rDate;
	}
	
	public String getrId()
	{
		return rId;
	}

	public void setrId(String rId)
	{
		this.rId = rId;
	}

	public String getbId()
	{
		return bId;
	}

	public void setbId(String bId)
	{
		this.bId = bId;
	}

	public String getrName()
	{
		return rName;
	}

	public void setrName(String rName)
	{
		this.rName = rName;
	}

	public String getrContent()
	{
		return rContent;
	}

	public void setrContent(String rContent)
	{
		this.rContent = rContent;
	}

	public Timestamp getrDate()
	{
		return rDate;
	}

	public void setrDate(Timestamp rDate)
	{
		this.rDate = rDate;
	}
}
