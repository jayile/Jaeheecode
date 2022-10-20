package com.study.springboot.service;

import org.springframework.ui.Model;

import com.study.springboot.dto.MDto;

public interface IMService
{
	public int join(String mid, String mpw, String mname, String memail, String mpnum);
	public int idcheck(String mid);
	public int idpwcheck(String mid, String mpw);
	public MDto userinfo(String mid);
	public int modiuser(String mid, String memail, String mpnum);
	public int modipw(String mid, String mpw);
	public int snsjoin(String mid, String mname, String memail, String mpnum);
	public int deleteuser(String mid);
	
	public void findWish(String mid, Model model);
	
	//주소
	public void adr_List(String mid, Model model);
	public int adr_Add(String mid, String adrName, String aName, String aZipcode, String aMain, String aSub, String aPrimary);
	public int adr_Del(String mid, String adrName);
	// 구매용 주소
	public int pay_adr(String mid, Model model);
	
	// 마이페이지용 배송중상품
	public void del_ing(String mid, Model model);
	public void mywish(String mid, Model model);
}
