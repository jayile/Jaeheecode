package com.study.springboot.service;

import java.util.List;
import java.util.Map;

import com.study.springboot.dto.AddressDto;
import com.study.springboot.dto.AppMemberDto;
import com.study.springboot.dto.PaidDto;
import com.study.springboot.dto.QnADto;

public interface IAppGameShopService
{
	public int join(String mId, String mPw, String mName, String mEmail, String mPnum);
	public AppMemberDto login(String mId);
	public int modify(String mId, String mName, String mEmail, String mPnum);
	public int modifyPW(String mId, String mPw);
	
	public int addAddress(String mId, String aName, String aZipcode, String aMain, String aSub, String aPrimary);
	public List<AddressDto> getAddressList(String mId);
	public int deleteAddress(String mId, String aName);
	public int modifyAInfo(String mId, String oriName, String aName, String aZipcode, String aMain, String aSub, String aPrimary);
		
	public int writeQnA(Map<String, Object> mQnAInfo);
	public List<QnADto> getQnAList(String mId);
	public int deleteQnA(String qId);
	public int modifyQnA(Map<String, Object> mQnAInfo);
	public QnADto getModifiedQnA(String qId);
	
	public int addPaidInfo(Map<String, Object> paidInfo);
	public List<PaidDto> getPaidList(String mId);

	// 웹용
	public AddressDto findaName(String aName);
	public AddressDto findaPrimary(String mId);
	
//	public BPageInfo articlePage(int curPage);
//	public BDto contentView(String strId);
//	public int delete(String bId);
//	public ArrayList<BDto> list(String curPage, Model model);
//	public int modify(String bName, String bTitle, String bContent, String bId);	
//	public int reply( String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent);
//	public BDto reply_view(String bId);
//	public int replyShape(String strGroup, String strStep);
//	public int upHit(String strId);
//	public ArrayList<BCommentInfo> commentList(String bId);
}
