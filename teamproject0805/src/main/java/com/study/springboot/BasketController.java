package com.study.springboot;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.dto.AddressDto;
import com.study.springboot.dto.PDto;
import com.study.springboot.service.IAppGameShopService;
import com.study.springboot.service.IGoodsService;
import com.study.springboot.service.IMService;

@Controller
public class BasketController
{
	@Autowired
	IGoodsService goods;
	
	// 유저정보
	@Autowired
	IMService ims;

	
	@Autowired
	private HttpSession httpSession;
	
	public Model usercheck(Model model) {
		String mid = (String)httpSession.getAttribute("mid");

		if(mid != null) {
			model.addAttribute("uinfo", ims.userinfo(mid));
		} 
		return model;
	}
	
	@RequestMapping("/pay_page")
	public String listPage(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		
		if(mid != null) {
			String userId = mid;
			System.out.println("userId : " + userId);

			// 카트 리스트 불러옴
			goods.cart_list(userId, request, model);
			
			// 총 상품 가격 불러오기
			int totalP = goods.totalP(mid);
			if(totalP != 0) {
				model.addAttribute("total", totalP);
			}
			
			// mid가 존재시 주소지 테이블 가져오기
			// 1. 주 배송지가 있는지 찾음 > 없을시 가장 윗쪽에 있는 주소를 가져옴
			int adr_exist = ims.pay_adr(mid, model);
			
		}

		return "/payview";
	}
	
	// 팝업
	@RequestMapping("/adr_popup")
	public String adrLsit_pop(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String id = request.getParameter("mid");
		ims.adr_List(id, model);
		
		return "/adr_pop";
	}
	
	
	@RequestMapping("/pay_db")
	public @ResponseBody String shoppay(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");

		String ordernum = "";
		if(mid != null) {
			String aname = request.getParameter("adrName");
			String azip = request.getParameter("aZipcode");
			String amain = request.getParameter("aMain");
			String asub = request.getParameter("aSub");
			String memo = request.getParameter("pdeliverymemo");
			
			System.out.println("주소:"+azip);
			
			ordernum = goods.add_paydb(mid, azip, amain, asub, memo, aname);
//			System.out.println("결제 db 기록 성공?:"+result);
		}

		return String.valueOf(ordernum);
	}
	
	@RequestMapping("/paydone")
	public String payok(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		String pid = request.getParameter("pid");
		
		if(pid != null && mid != null) {
			goods.get_payinfo(pid, model);
		}
		
		return "/paydone";
	}

	@RequestMapping("/member/go_mypay")
	public String mypaypage(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		int result = 0;
		if(mid != null) {
			goods.get_paydata(mid, model);
		}
		
		return "/member/mypay";
	}
	
	@RequestMapping("/member/mypay_info")
	public String mypay_moreinfo(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String pid = request.getParameter("pid");
		if(pid != null) {
			goods.get_payinfo(pid, model);
		}
		
		return "/member/mypay_info";
	}
	
}
