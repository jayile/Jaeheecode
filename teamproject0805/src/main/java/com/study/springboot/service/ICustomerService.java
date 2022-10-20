package com.study.springboot.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.study.springboot.dto.CDto;

public interface ICustomerService
{
	public int customer_write(Map map);
	public void qna_list(String mid, HttpServletRequest request, Model model);
	public void all_qna_list(HttpServletRequest request, Model model);
	
	public CDto qna_view(String qId, HttpServletRequest request, Model model);	
	public int qna_reply_write(Map map);
}
