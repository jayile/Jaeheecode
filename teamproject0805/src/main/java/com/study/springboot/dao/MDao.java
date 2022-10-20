package com.study.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.AddressDto;
import com.study.springboot.dto.GDto;
import com.study.springboot.dto.MDto;
import com.study.springboot.dto.OrderDto;
import com.study.springboot.dto.WDto;

@Mapper
public interface MDao
{
	public int join(String mid, String mpw, String mname, String memail, String mpnum);
	public int idcheck(String mid);
	public String idpwcheck(String mid);
	public MDto userinfo(String mid);
	public int modiuser(String mid, String memail, String mpnum);
	public int modipw(String mid, String mpw);
	public int snsjoin(String mid, String mpw, String mname, String memail, String mpnum);
	public int deleteuser(String mid);
	
	// 아이디 기준 위시리스트
	public int getUser(String mid);
	public List<GDto> wishlist(String mid);
	
	// 주소
	public List<AddressDto> adrlist(String mid);
	public int add_adr(String mid, String adrName, String aName, int aZipcode, String aMain, String aSub, int aPrimary);
	public int adr_find(String mid, String aName);
	public int adr_find_pry(String mid);
	public int modi_primary_no(String mid);
	public int del_adr(String mid, String aName);
	// 구매용 주소
	public AddressDto pay_adr(String mid);
	
	// 배송중인 물품
	public int delivery_ing(String mid);
	public List<OrderDto> delivery_ing_info(String mid, String mid2);
	
}
