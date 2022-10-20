package com.study.springboot.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.study.springboot.dto.MDto;

public interface IAdminService {
	public void userlist(HttpServletRequest request, Model model);
	public void searchUserlist(String word, HttpServletRequest request, Model model);
	
	public void userinfo(String mid, Model model);
	public void userinfo_pay(String mid, Model model);
	public void userinfo_review(String mid, Model model);
	
	public int addBList(String mid, String btype, String bmemo);
	public void blacklist(HttpServletRequest request, Model model);
	public int delBList(String mid);
	
	public void paylist(HttpServletRequest request, Model model);
	public void paylist_info(String pid, Model model);
	
	public int deliveryStart(String pid);
}
