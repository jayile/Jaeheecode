package com.study.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.BlackDto;
import com.study.springboot.dto.GDto;
import com.study.springboot.dto.MDto;
import com.study.springboot.dto.OrderDto;
import com.study.springboot.dto.PaidDto;

@Mapper
public interface IADao {
	// mapper 정렬 기준 mid로 해뒀는데 이후에 바꿔야할듯
	public List<MDto> memberlist(int start, int end);
	public int countUser();
	public List<MDto> searchMemberList(String word, int start, int end);
	public int searchCountUser(String word);
	
	public MDto memberinfo(String mid);
	public BlackDto blackinfo(String mid);
	public int addBList(String mid, int btype, String bmemo);
	public int modiEnabledA(int btype ,String mid);
	public int modiEnabledB(int btype ,String mid);
	public List<BlackDto> blacklist(int start, int end);
	public int countBlack();
	public int delBlack(String mid);
	public int returnEnabled(String mid);
	
	public List<OrderDto> user_paylist(String mid);
	public List<GDto> user_reviewlist(String mid);
	
	public List<PaidDto> payinfo(int start, int end);
	public int countPay();
	
	public List<OrderDto> order_payinfo(String pid);
	public PaidDto pay_payinfo(String pid);
	
	public int delivery_start(String pid);
}
