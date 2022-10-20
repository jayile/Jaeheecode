package com.study.springboot;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.service.IAdminService;
import com.study.springboot.service.IMService;

@Controller
public class AdminController {
	@Autowired
	IMService ims;
	@Autowired
	IAdminService ias;
	
	@Autowired
	private HttpSession httpSession;
	
	// 유저 정보 호출
	public Model usercheck(Model model) {
		String mid = (String)httpSession.getAttribute("mid");

		if(mid != null) {
			model.addAttribute("uinfo", ims.userinfo(mid));
		} 
		return model;
	}
	
	// 관리자 메인
	@RequestMapping("/admin/adminmain")
	public String amain(Model model) {
		usercheck(model);
		return "/admin/adminmain";
	}
		
	
	// 유저 조회
	@RequestMapping("/admin/getlist")
	public String memberlist(HttpServletRequest request, Model model) {
		usercheck(model);
		
		ias.userlist(request, model);
		
		return "/admin/userlist";
	}
	
	// 유저 검색
	@RequestMapping("/admin/searchUser")
	public String searchMember(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String word = request.getParameter("key");
		System.out.println("검색어 : "+word);
		
		ias.searchUserlist(word, request, model);
		
		return "/admin/search_userlist";
	}

	// 유저 상세 정보
	@RequestMapping("/admin/userview")
	public String memberinfo(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String id = request.getParameter("mid");
		ias.userinfo(id, model);

		return "/admin/userview";
	}
	
	
	// 팝업
	@RequestMapping("/admin/b_popup")
	public String black(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String id = request.getParameter("mid");
		ias.userinfo(id, model);
		
		return "/admin/black";
	}
	
	// 블랙 유저 추가
	@RequestMapping("/admin/b_add")
	public @ResponseBody String addBlack(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String mid = request.getParameter("mid");
		String btype = request.getParameter("btype");
		String bmemo = request.getParameter("bmemo");
		
		int nResult = ias.addBList(mid, btype, bmemo);

		JSONObject obj = new JSONObject();
		
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "블랙 처리되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "내부서버 오류입니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/admin/blacklist")
	public String see_blacklist(HttpServletRequest request, Model model) {
		usercheck(model);
		ias.blacklist(request, model);
		
		return "/admin/black_list";
	}
	
	@RequestMapping("/admin/b_del")
	public @ResponseBody String delBlack(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String mid = request.getParameter("mid");
		
		int nResult = ias.delBList(mid);

		JSONObject obj = new JSONObject();
		
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "블랙 해제되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "내부서버 오류입니다.");
		}
		
		return obj.toJSONString();
	}
	
	// 주문내역 관리
	@RequestMapping("/admin/pay_list")
	public String payList(HttpServletRequest request, Model model) {
		usercheck(model);
		ias.paylist(request, model);
		
		return "/admin/paylist";
	}
	
	@RequestMapping("/admin/pay_info")
	public String payList_info(HttpServletRequest request, Model model) {
		usercheck(model);
		String pid = request.getParameter("pId");
		ias.paylist_info(pid, model);
		
		return "/admin/paylist_info";
	}
	
	@RequestMapping(value="/admin/order_status", method=RequestMethod.GET)
	public String orderStatus(@RequestParam List<String> pids, Model model) {
		usercheck(model);
		int nResult = 0;
		try {
			for (String c : pids) {
	            System.out.println("들어온 번호 : " + c);
	            ias.deliveryStart(c);
	        }
			nResult = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/pay_list";
	}
	
	@RequestMapping("/admin/order_status_one")
	public @ResponseBody String orderStatus_one(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String pid = request.getParameter("pid");
		
		int nResult = ias.deliveryStart(pid);
		
		JSONObject obj = new JSONObject();
		
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "발송 처리되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "내부서버 오류입니다.");
		}
		
		return obj.toJSONString();
	}
}
