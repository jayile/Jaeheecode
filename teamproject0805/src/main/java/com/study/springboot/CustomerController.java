package com.study.springboot;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.springboot.dto.MDto;
import com.study.springboot.service.ICustomerService;
import com.study.springboot.service.IMService;

@Controller
public class CustomerController
{
	@Autowired
	IMService ims;
	
	@Autowired
	ICustomerService customer;
	
	@Autowired
	private HttpSession httpSession;
	
	@Value("${qna.path}")
    private String qnaPath;
	
	@RequestMapping("/member/customerPage")
	public String customerpage(HttpServletRequest request, Model model) {
		usercheck(model);
		
		return "customer_page";
	}
	
	@RequestMapping(value="/member/customer_write", method=RequestMethod.POST)
	public @ResponseBody String customer_write(HttpServletRequest request, Model model) {
		
		JSONObject obj = new JSONObject();
		
		Map<String, Object> map = new HashMap<>();
		
		try {
				String path = ResourceUtils.getFile(qnaPath).toPath().toString();
				
				MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
				
				Iterator<String> itr = mhsr.getFileNames();
				
				MultipartFile mfile = null;			
				String fileName = "";
				
//				List resultList = new ArrayList();
				
				String qName = (String)httpSession.getAttribute("mid");
				String qKind = mhsr.getParameter("qKind");
				String qTitle = mhsr.getParameter("qTitle");
				String qContent = mhsr.getParameter("qContent");
				
				System.out.println("qName : " + qName);
				System.out.println("qKind : " + qKind);
				System.out.println("qTitle : " + qTitle);
				System.out.println("qContent : " + qContent);
				
				map.put("qName", qName);
				map.put("qKind", qKind);
				map.put("qTitle", qTitle);
				map.put("qContent", qContent);
				
				int i = 1;
				// 업로드폼의 file속성의 필드의 갯수만큼 반복
				while ( itr.hasNext() ) {
	
					fileName = (String)itr.next();
//					System.out.println("fileName : " + fileName);
					
					mfile = mhsr.getFile(fileName);
//					System.out.println(mfile);			//CommonsMultipartFile@1366c0b 형태
					
					String oriName = new String(mfile.getOriginalFilename().getBytes(),"UTF-8"); 
//					System.out.println("upload :" + oriName);
					
					if("".equals(oriName)) { 
						map.put("file" + i, "null");
						continue; 
					}	// 파일이 더 없으면 여기서 멈춘다.
					
					String saveFileName = getUuid() + "." + oriName;
					
					File serverFullName = new File(path + File.separator + saveFileName);	//서버에 저장된 전체경로 및 파일명
					
//					System.out.println("oriName : " + oriName);
					System.out.println("saveFileName : " + saveFileName);
//					System.out.println("serverFullName : " + serverFullName);
					
					// 업로드한 파일을 지정한 파일에 저장한다.
					mfile.transferTo(serverFullName);
					
					map.put("file" + i, saveFileName);
					
					i++;
				}			
			
//			Iterator<String> keys = map.keySet().iterator();
//			// 저장된 값 확인용!
//	        while( keys.hasNext() ){
//	            String key = keys.next();
//	            String value = (String) map.get(key);
//	            System.out.println("키 : " + key + ", 값 : " + value);
//	        }
			
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		int result = customer.customer_write(map);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "문의가 게시되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "서버 오류로 문의 게시에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
	
	public static String getUuid(){
		String uuid = UUID.randomUUID().toString();
		//System.out.println(uuid);		
		uuid = uuid.replaceAll("-", "");
		//System.out.println("생성된UUID:"+ uuid);
		return uuid;
	}
	
	// 유저 정보 호출
	public Model usercheck(Model model) {
		String mid = (String)httpSession.getAttribute("mid");

		if(mid != null) {
			model.addAttribute("uinfo", ims.userinfo(mid));
		} 
		return model;
	}
	
	@RequestMapping("/member/qna_list")
	public String qna_list(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String mid = (String)httpSession.getAttribute("mid");
		MDto data = ims.userinfo(mid);
        String role = data.getAuthority();
        
        if ( role.equals("ROLE_ADMIN") ) {
    		customer.all_qna_list(request, model);
        } else {
        	customer.qna_list(mid, request, model);
        }
		return "qna_list";
	}
	
	@RequestMapping("/member/qna_view")
	public String qna_view(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String qId = request.getParameter("qId");
		System.out.println("qId : " + qId);
		
		model.addAttribute("dto", customer.qna_view(qId, request, model));
		return "qna_view";
	}
	
	@RequestMapping("/admin/qna_reply_view")
	public String qna_reply_view(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String qId = request.getParameter("qId");
		System.out.println("qId : " + qId);
		
		model.addAttribute("dto", customer.qna_view(qId, request, model));
		return "qna_answerview";
	}
	
	@RequestMapping("/admin/qna_reply_write")
	public @ResponseBody String qna_reply_write(HttpServletRequest request, Model model) {
		usercheck(model);
		
		JSONObject obj = new JSONObject();
		
		Map<String, Object> map = new HashMap<>();
		
		String mid = (String)httpSession.getAttribute("mid");
		String qId = request.getParameter("qId");
		String qName = request.getParameter("qName");
		String qGroup = request.getParameter("qGroup");
		String qContent = request.getParameter("qContent");
		String qStep = request.getParameter("qStep");
		String qIndent = request.getParameter("qIndent");

		map.put("mid", mid);
		map.put("qId", qId);
		map.put("qName", qName);
		map.put("qGroup", qGroup);
		map.put("qContent", qContent);
		map.put("qStep", qStep);
		map.put("qIndent", qIndent);
		
		int result = customer.qna_reply_write(map);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "답변이 게시되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "답변 게시에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
}
