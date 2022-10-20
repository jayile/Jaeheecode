package com.study.springboot;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.oauth2.SessionUser;
import com.study.springboot.service.IBbsService;
import com.study.springboot.service.IMService;

@Controller
public class BBSController
{
	@Autowired
	IMService ims;
	
	@Autowired
	IBbsService bbs;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping("/list")
	public String listPage(HttpServletRequest request, Model model) {
		
		bbs.list(request, model);
		return "list";
	}
	
	@RequestMapping("/writeForm")
	public String writeForm() {
		
		return "writeForm";
	}
	
	@RequestMapping("/write")
	public @ResponseBody String write(HttpServletRequest request, Model model) {
		
		JSONObject obj = new JSONObject();
		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("bName", bName);
		map.put("bTitle", bTitle);
		map.put("bContent", bContent);
		
		int result = bbs.write(map);
		System.out.println("Write : " + result);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "글이 게시되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "글 게시에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request, Model model) {
		
		String bId = request.getParameter("bId");
		System.out.println(bId);
		
		bbs.upHit(bId);
		model.addAttribute("dto", bbs.view(bId));
		
		return "/view";
	}
	
	@RequestMapping("modifyForm")
	public String modifyForm(HttpServletRequest request, Model model) {
		
		String bId = request.getParameter("bId");
		System.out.println(bId);
		
		model.addAttribute("dto", bbs.view(bId));
		
		return "/modifyForm";
	}
	
	@RequestMapping("modify")
	public @ResponseBody String modify(HttpServletRequest request, Model model) {
		
		JSONObject obj = new JSONObject();
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("bName", bName);
		map.put("bTitle", bTitle);
		map.put("bContent", bContent);
		map.put("bId", bId);
		
		int result = bbs.modify(map);
		System.out.println("Modify : " + result);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "글이 수정되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "글 수정에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		
		String bId = request.getParameter("bId");
		
		int result = bbs.delete(bId);
		System.out.println("Delete : " + result);

		return "redirect:list";
	}
	
	@RequestMapping("/notice/notice_view")
	public String noticePage(HttpServletRequest request, Model model) {
		usercheck(model);
		
		bbs.noticelist(request, model);
		return "notice_view";
	}
	
	@RequestMapping("/admin/notice_write")
	public String noticeWrite(HttpServletRequest request, Model model) {
		usercheck(model);
		
		return "notice_write_view";
	}
	
	@RequestMapping("/admin/notice_modify_view")
	public String noticeModify(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String bId = request.getParameter("bId");
		
		model.addAttribute("dto", bbs.notice_modify_view(bId));
		
		return "/notice_modify_view";
	}
	
	
	@RequestMapping("/admin/notice_delete")
	public @ResponseBody String noticedelete(HttpServletRequest request, Model model) {
		usercheck(model);
		
		JSONObject obj = new JSONObject();
		
		String bId = request.getParameter("bId");
		
		int result = bbs.notice_delete(bId);
		System.out.println("delete : " + result);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "공지사항이 삭제되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "공지사항 삭제에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/admin/notice_dowrite")
	public @ResponseBody String notice_doWrite(HttpServletRequest request, Model model) {
		
		JSONObject obj = new JSONObject();
		
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("bTitle", bTitle);
		map.put("bContent", bContent);
		
		int result = bbs.notice_write(map);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "공지사항이 게시되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "공지사항 게시에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/admin/notice_modify_do")
	public @ResponseBody String noticeDoModify(HttpServletRequest request, Model model) {
		JSONObject obj = new JSONObject();
		
		String bId = request.getParameter("bId");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		System.out.println("bId : " + bId + "bTitle : " + bTitle + "bContent : " + bContent);
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("bTitle", bTitle);
		map.put("bContent", bContent);
		map.put("bId", bId);
		
		int result = bbs.notice_modify_do(map);
		System.out.println("Modify : " + result);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "공지사항이 수정되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "공지사항 수정에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
	
	
	@RequestMapping("/map_info")
	public String map(HttpServletRequest request, Model model) {
		usercheck(model);
		
		return "map_view";
	}
	
	public Model usercheck(Model model) {
		String mid = (String)httpSession.getAttribute("mid");

		if(mid != null) {
			model.addAttribute("uinfo", ims.userinfo(mid));
		} 
		return model;
	}
}
