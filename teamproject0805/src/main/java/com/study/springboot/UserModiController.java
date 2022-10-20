package com.study.springboot;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.dto.AddressDto;
import com.study.springboot.oauth2.SessionUser;
import com.study.springboot.service.IMService;

@Controller
public class UserModiController
{
	// 있는 기능 : sns/홈유저 확인, 정보수정, sns유저는 db추가, db에서 정보삭제
	
	@Autowired
	IMService ims;
	
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
	
	// 수정전 sns유저인지 홈유저인지 확인
	@RequestMapping("/usercheck")
	public @ResponseBody String usercheck(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		
		int nResult = ims.idcheck(mid);
		System.out.println("result : " + nResult);
		
		JSONObject obj = new JSONObject();
		if(nResult == 1) {
			obj.put("code", "homeuser");
		} else {
			obj.put("code", "sns");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/member/change_pw")
	public String change_password(HttpServletRequest request, Model model) {
		usercheck(model);
		
		return "/security/pwcheck_pwmodi";
	}
	
	@RequestMapping("/member/change_pw_view")
	public String change_pw_view(HttpServletRequest request, Model model) {
		usercheck(model);
		
		return "/member/modi_pw";
	}
	
	@RequestMapping("/modi")
	public @ResponseBody String modiuser(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		
		String memail = request.getParameter("memail");
		String mpnum = request.getParameter("mpnum");
		

		
		int nResult = ims.modiuser(mid, memail, mpnum);
		
		JSONObject obj = new JSONObject();
		
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "정보수정 성공");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "정보수정 실패");
		}
		return obj.toJSONString();
	}
	
	@RequestMapping("/snsmodi")
	public @ResponseBody String modiSNSuser(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = request.getParameter("mid");
		String mname = request.getParameter("mname");
		String memail = request.getParameter("memail");
		String mpnum = request.getParameter("mpnum");
		
		int nResult = ims.snsjoin(mid, mname, memail, mpnum);
		
		JSONObject obj = new JSONObject();
		
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "sns 정보수정 성공");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "sns 정보수정 실패");
		}
		return obj.toJSONString();
	}
	
	@RequestMapping("/deleteuser")
	public @ResponseBody String deleteUser(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		System.out.println("delete id : " + mid);
		
		int nResult = ims.deleteuser(mid);
		System.out.println("delete result : " + nResult);
		
		JSONObject obj = new JSONObject();
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "탈퇴 성공");
			
		} else {
			obj.put("code", "fail");
			obj.put("desc", "탈퇴 실패");
		}
		
		return obj.toJSONString();
	}
	
	// 비밀번호 변경 - sns 유저인지 체크
	@RequestMapping("/modi_check")
	public @ResponseBody String modi_ckeck_sns(HttpServletRequest request, Model model) {
		usercheck(model);
		SessionUser snsdata = (SessionUser) httpSession.getAttribute("user");
		
		JSONObject obj = new JSONObject();
		
		if(snsdata == null) {
			obj.put("code", "success");
			obj.put("desc", "변경 가능");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "SNS 유저는 변경이 불가능합니다.");
		}

		return obj.toJSONString();
	}
	
	@RequestMapping("/modi_pw")
	public @ResponseBody String modi_pw(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		String mpw = request.getParameter("mpw");
		
		int nResult = ims.modipw(mid, mpw);
		System.out.println("pw수정결과:"+nResult);
		
		JSONObject obj = new JSONObject();
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "비밀번호가 수정되었습니다.");
			
		} else {
			obj.put("code", "fail");
			obj.put("desc", "서버 오류입니다.");
		}
		
		return obj.toJSONString();
	}
	
	// 주소
	@RequestMapping("/member/getList")
	public String addressList(Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		ims.adr_List(mid, model);
		
		return "/member/useraddress";
	}
	
	@RequestMapping("/member/adrForm")
	public String addressForm(Model model) {
		usercheck(model);
		
		return "/member/useraddress_add";
	}

	@RequestMapping("/member/addAdr")
	public @ResponseBody String addressAdd(HttpServletRequest request, Model model) {
		usercheck(model);
		int nResult = 0;
		
		String mId = request.getParameter("mId");
		String adrName = request.getParameter("adrName");
		String aName = request.getParameter("aName");
		String aZipcode = request.getParameter("aZipcode");
		String aMain = request.getParameter("aMain");
		String aSub = request.getParameter("aSub");
		String aPrimary = request.getParameter("aPrimary");
		if(aPrimary == null) {
			aPrimary = "0";
			nResult = ims.adr_Add(mId, adrName, aName, aZipcode, aMain, aSub, aPrimary);
		} else {
			nResult = ims.adr_Add(mId, adrName, aName, aZipcode, aMain, aSub, aPrimary);
		}

		JSONObject obj = new JSONObject();
		
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "주소가 등록되었습니다.");
			
		} else if(nResult == 0) {
			obj.put("code", "fail");
			obj.put("desc", "이미 존재하는 배송지 이름입니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "서버 오류입니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/member/delAdr")
	public @ResponseBody String addressDel(HttpServletRequest request, Model model) {
		usercheck(model);
		int nResult = 0;
		
		String mid = (String)httpSession.getAttribute("mid");
		String ajax_mid = request.getParameter("mid");
		String aname = request.getParameter("aname");
		
//		System.out.println("데이터:"+aname + "," + ajax_mid);
		
		if(mid.equals(ajax_mid)) {
			nResult = ims.adr_Del(mid, aname);
		} 
	
		JSONObject obj = new JSONObject();
		
		if(nResult == 1) {
			obj.put("code", "success");
			obj.put("desc", "주소가 삭제되었습니다.");
			
		} else {
			obj.put("code", "fail");
			obj.put("desc", "서버 오류입니다.");
		}
		
		return obj.toJSONString();
	}
}
