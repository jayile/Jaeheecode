package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.GDto;
import com.study.springboot.dto.OrderDto;
import com.study.springboot.dto.PaidDto;
import com.study.springboot.dto.WDto;

@Mapper
public interface IGDao {
	public int goods_writeDao1(Map map);
	public int goods_writeDao2(Map map);
	public int del_goodsDao(String cId);
	
	public List<GDto> goods_listDao(int start, int end);
	public int selectCountDao();
	public List<GDto> search_goodsDao(String word, int start, int end);
	public int search_selectCountDao(String word);
	public List<GDto> kind_goodsDao(String kind, int nStart, int nEnd);
	public int kind_selectCountDao(String kind);
	public int modify_goodsDao(String cId, String cName, String cPrice, String cCount, String cDown);
	
	public GDto goods_viewDao(String cId);
	public List<GDto> avgScoreDao(String cId);
	
	public int review_writeDao(Map map);
	public void up_reviewDao(Map map);
	public void down_reviewDao(String cId);
	public List<GDto> review_ListDao(String cId);
	public int delete_reviewDao(String userId, String cId, String rContent);
	public int modify_reviewDao(String mId, String cId, String rScore, String rContent, String rId);
	public List<GDto> reviewListDao(String mId, int start, int end);
	public int selectReviewCountDao(String mId);
	public int delete_myreviewDao(String rId);
	
	public int goods_put1Dao(Map map);
	public int goods_put2Dao(Map map);
	public int goods_confirmDao(Map map);
	public int goods_upDao(Map map);
	public List<GDto> cart_listDao(String userId);
	public int cart_deleteDao(String cId, String userId);
	public int cart_AlldeleteDao(String userId);	
	
	// 위시리스트
	public int add_wish(String mid, String cId);
	public int del_wish(String mid, String cId);
	public WDto wish_list(String mid, String cId);
	
	// 결제페이지
	public List<GDto> get_price(String mid);
	public List<GDto> check_cCount(String cId);
	public String add_ordernumber();
	public int add_orderdb(String pid, String mid, int cid, int pcount, int cprice, int pprice, String name, String photo);
	public int add_paydb(String pid, String mid, int totalprice, int aZipcode ,String aMain, String aSub, String memo, String adrname);
	public int minus_count(int cId, int cCount);
	public int del_cart(int cId);
	
	// 구매내역
	public List<OrderDto> get_pay(String mid);
	public List<OrderDto> order_payinfo(String pid);
	public PaidDto pay_payinfo(String pid);
}
