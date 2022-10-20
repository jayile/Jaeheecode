package com.study.springboot.dto;

import java.sql.Timestamp;

public class GDto {
	
	private int cId;
	private String cName;
	private int cPrice;
	private int cCount;
	private String cDetail;
	private String cPhoto1;
	private String cPhoto2;
	private String cPhoto3;
	private String kind;
	private int cSale;
	private int cDown;
	
	private int rCount;
	private Timestamp gDate;
	
	private String mId;
	private int count;
	
	private String rContent;
	private int rScore;
	private Timestamp rDate;
	private int rId;
	private int avgScore;
	private int rModi;
	
	public GDto() {}
	
	public GDto(int cId, String cName, int cPrice, int cCount, String cDetail, String cPhoto1, String cPhoto2,
			String cPhoto3, String kind, int cSale, int cDown, int rCount, Timestamp gDate, String mId, int count,
			String rContent, int rScore, Timestamp rDate, int rId, int avgScore, int rModi)
	{
		super();
		this.cId = cId;
		this.cName = cName;
		this.cPrice = cPrice;
		this.cCount = cCount;
		this.cDetail = cDetail;
		this.cPhoto1 = cPhoto1;
		this.cPhoto2 = cPhoto2;
		this.cPhoto3 = cPhoto3;
		this.kind = kind;
		this.cSale = cSale;
		this.cDown = cDown;
		this.rCount = rCount;
		this.gDate = gDate;
		this.mId = mId;
		this.count = count;
		this.rContent = rContent;
		this.rScore = rScore;
		this.rDate = rDate;
		this.rId = rId;
		this.avgScore = avgScore;
		this.rModi = rModi;
	}

	public int getcId()
	{
		return cId;
	}

	public void setcId(int cId)
	{
		this.cId = cId;
	}

	public String getcName()
	{
		return cName;
	}

	public void setcName(String cName)
	{
		this.cName = cName;
	}

	public int getcPrice()
	{
		return cPrice;
	}

	public void setcPrice(int cPrice)
	{
		this.cPrice = cPrice;
	}

	public int getcCount()
	{
		return cCount;
	}

	public void setcCount(int cCount)
	{
		this.cCount = cCount;
	}

	public String getcDetail()
	{
		return cDetail;
	}

	public void setcDetail(String cDetail)
	{
		this.cDetail = cDetail;
	}

	public String getcPhoto1()
	{
		return cPhoto1;
	}

	public void setcPhoto1(String cPhoto1)
	{
		this.cPhoto1 = cPhoto1;
	}

	public String getcPhoto2()
	{
		return cPhoto2;
	}

	public void setcPhoto2(String cPhoto2)
	{
		this.cPhoto2 = cPhoto2;
	}

	public String getcPhoto3()
	{
		return cPhoto3;
	}

	public void setcPhoto3(String cPhoto3)
	{
		this.cPhoto3 = cPhoto3;
	}

	public String getKind()
	{
		return kind;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}

	public int getcSale()
	{
		return cSale;
	}

	public void setcSale(int cSale)
	{
		this.cSale = cSale;
	}

	public int getcDown()
	{
		return cDown;
	}

	public void setcDown(int cDown)
	{
		this.cDown = cDown;
	}

	public int getrCount()
	{
		return rCount;
	}

	public void setrCount(int rCount)
	{
		this.rCount = rCount;
	}

	public Timestamp getgDate()
	{
		return gDate;
	}

	public void setgDate(Timestamp gDate)
	{
		this.gDate = gDate;
	}

	public String getmId()
	{
		return mId;
	}

	public void setmId(String mId)
	{
		this.mId = mId;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getrContent()
	{
		return rContent;
	}

	public void setrContent(String rContent)
	{
		this.rContent = rContent;
	}

	public int getrScore()
	{
		return rScore;
	}

	public void setrScore(int rScore)
	{
		this.rScore = rScore;
	}

	public Timestamp getrDate()
	{
		return rDate;
	}

	public void setrDate(Timestamp rDate)
	{
		this.rDate = rDate;
	}

	public int getrId()
	{
		return rId;
	}

	public void setrId(int rId)
	{
		this.rId = rId;
	}

	public int getAvgScore()
	{
		return avgScore;
	}

	public void setAvgScore(int avgScore)
	{
		this.avgScore = avgScore;
	}

	public int getrModi()
	{
		return rModi;
	}

	public void setrModi(int rModi)
	{
		this.rModi = rModi;
	}
}
