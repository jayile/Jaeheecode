package com.study.springboot.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.study.springboot.dto.GDto;
import com.study.springboot.dto.PDto;
import com.study.springboot.dto.WDto;

public interface IGoodsService {
	public int goods_write(Map map);
	public int del_goods(String cId);
	
	public void goods_list(HttpServletRequest request, Model model);
	public GDto goods_view(String cId, HttpServletRequest request, Model model);
	public void search_goods(String search, String word, HttpServletRequest request, Model model);
	public void kind_goods(String kind, HttpServletRequest request, Model model);
	
	public int goods_review_write(Map map);
	public void goods_reviewList(String cId, HttpServletRequest request, Model model);
	public int delete_review(String userId, String cId, String rContent);
	public int modify_review(String mId, String cId, String rScore, String rContent, String rId);
	public void reviewList(String mId, HttpServletRequest request, Model model); // 이거 진행
	public int delete_myreview(String rId, String cId);
	
	public int goods_put1(Map map);
	public int goods_put2(Map map);
	public void cart_list(String userId, HttpServletRequest request, Model model);
	public int cart_delete(String cId, String userId, HttpServletRequest request, Model model);
	public int cart_Alldelete(String userId, HttpServletRequest request, Model model);
	public int modify_goods(String cId, String cName, String cPrice, String cCount, String cDown);
	
	// 위시리스트
	public int add_wish(String mid, String cId);
	public int del_wish(String mid, String cId);
	public WDto wish_list(String mid, String cId);
	
	// 결제페이지
	public int totalP(String mid);
	public String add_paydb(String mid, String aZip, String aMain, String aSub, String memo, String adrname);
	
	// 구매 내역
	public void get_paydata(String mid, Model model);
	public void get_payinfo(String pid, Model model);
}
