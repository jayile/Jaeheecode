package com.study.springboot.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.study.springboot.BPageInfo;
import com.study.springboot.dto.BDto;

public interface IBbsService
{
	public void list(HttpServletRequest request, Model model);
	public BPageInfo articlePage(int curPage);
	
	public BDto view(String bId);
	public int upHit(String bId);
	
	public int write(Map<String, String> map);
	public int modify(Map<String, String> map);
	public int delete(String bid);
	
	public void noticelist(HttpServletRequest request, Model model);
	public int notice_write(Map<String, String> map);
	public BDto notice_modify_view(String bId);
	public int notice_modify_do(Map<String, String> map);
	public int notice_delete(String bId);
	
}
