package com.study.jsp.dto;

import java.sql.Timestamp;

public class MemberDto
{
	private String mId;
	private String mPw;
	private String mName;
	private String mEmail;
	private String mAddress;
	private Timestamp mDate;
	private Timestamp mBlack;
	private String grade;
	
	public MemberDto() {}

	public MemberDto(String mId, String mPw, String mName, String mEmail, String mAddress,
			Timestamp mDate, Timestamp mBlack, String grade)
	{
		this.mId = mId;
		this.mPw = mPw;
		this.mName = mName;
		this.mEmail = mEmail;
		this.mAddress = mAddress;
		this.mDate = mDate;
		this.mBlack = mBlack;
		this.grade = grade;
	}

	public Timestamp getmBlack()
	{
		return mBlack;
	}

	public void setmBlack(Timestamp mBlack)
	{
		this.mBlack = mBlack;
	}

	public String getmId()
	{
		return mId;
	}

	public void setmId(String mId)
	{
		this.mId = mId;
	}

	public String getmPw()
	{
		return mPw;
	}

	public void setmPw(String mPw)
	{
		this.mPw = mPw;
	}

	public String getmName()
	{
		return mName;
	}

	public void setmName(String mName)
	{
		this.mName = mName;
	}

	public String getmEmail()
	{
		return mEmail;
	}

	public void setmEmail(String mEmail)
	{
		this.mEmail = mEmail;
	}

	public String getmAddress()
	{
		return mAddress;
	}

	public void setmAddress(String mAddress)
	{
		this.mAddress = mAddress;
	}

	public Timestamp getmDate()
	{
		return mDate;
	}

	public void setmDate(Timestamp mDate)
	{
		this.mDate = mDate;
	}

	public String getGrade()
	{
		return grade;
	}

	public void setGrade(String grade)
	{
		this.grade = grade;
	}
}