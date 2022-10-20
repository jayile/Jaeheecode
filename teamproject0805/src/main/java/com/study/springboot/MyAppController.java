package com.study.springboot;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

import com.study.springboot.dto.AddressDto;
import com.study.springboot.dto.AppMemberDto;
import com.study.springboot.dto.PaidDto;
import com.study.springboot.dto.QnADto;
import com.study.springboot.service.IAppGameShopService;
import com.study.springboot.service.IMService;

@Controller
public class MyAppController
{
	@Autowired
	IAppGameShopService appGss;
	
	@Value("${upload.path}")
    private String uploadPath;
	
	@Autowired
	private HttpSession httpSession;
	@Autowired
	IMService ims;
	
	// 유저 정보 호출
	public Model usercheck(Model model) {
		String mid = (String)httpSession.getAttribute("mid");
		if(mid != null) {
			model.addAttribute("uinfo", ims.userinfo(mid));
		}
		return model;
	}
	
	@RequestMapping("/app/join")
	public @ResponseBody String appJoin(HttpServletRequest request) {
		int num = 0;
		try
		{
			num = appGss.join(
					request.getParameter("mId"),
					request.getParameter("mPw"),
					request.getParameter("mName"),
					request.getParameter("mEmail"),
					request.getParameter("mPnum")
					);
			System.out.println("Join : "+num);
		} catch (Exception e)
		{			
			if(e.toString().indexOf("SCOTT.SM_MID_PK")>0) {
				System.out.println("Duplicated ID!");
				num=2;
			};
		} 
		
		JSONObject obj = new JSONObject();
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","가입이 완료되었습니다.");
		} else if(num==2) {
			obj.put("code","fail");
			obj.put("desc","이미 존재하는 아이디입니다.");
		} else {
			obj.put("code","fail");
			obj.put("desc","가입에 실패하였습니다.");
		}
		
		return obj.toJSONString();
	}	
	
	@RequestMapping("/app/login")
	public @ResponseBody String appLogin(HttpServletRequest request) {
		
		int num = 0;		
		String id = request.getParameter("mId");
		
		Map<String, Object> data = new HashMap<>();
		
		AppMemberDto memberDTO = appGss.login(id);
		System.out.println(!Objects.isNull(memberDTO));
		if(!Objects.isNull(memberDTO)){			
			data.put("mId", memberDTO.getMId());
			data.put("mPw", memberDTO.getMPw());
			data.put("mName", memberDTO.getMName());
			data.put("mPnum", memberDTO.getMPnum());
			data.put("mEmail", memberDTO.getMEmail());
			data.put("mBlack", memberDTO.getMBlack());
			data.put("enabled", memberDTO.getEnabled());
			
			num = 1;
		}
		
		JSONObject obj = new JSONObject();
		
		if(num == 1)
		{
			obj.put("code","success");
			obj.put("desc","["+id+"]에 해당하는 회원 정보를 불러왔습니다.");
			obj.put("data", data);
		} else {
			obj.put("code","fail");
			obj.put("desc","["+id+"]에 해당하는 회원 정보를 불러오지 못 했습니다.");
		}
		
		return obj.toJSONString();		
	}
	
	@RequestMapping("/app/modify")
	public @ResponseBody String appModify(HttpServletRequest request) {		
		int num = appGss.modify(
						request.getParameter("mId"),						
						request.getParameter("mName"),
						request.getParameter("mEmail"),
						request.getParameter("mPnum")
						);
		System.out.println("Modify : "+num);
		
		JSONObject obj = new JSONObject();
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","수정이 완료되었습니다.");
		} else {
			obj.put("code","fail");
			obj.put("desc","서버 에러입니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/modifyPw")
	public @ResponseBody String appModifyPw(HttpServletRequest request) {		
		int num = appGss.modifyPW(
						request.getParameter("mId"),						
						request.getParameter("mPw")						
						);
		System.out.println("Modify : "+num);
		
		JSONObject obj = new JSONObject();
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","수정이 완료되었습니다.");
		} else {
			obj.put("code","fail");
			obj.put("desc","서버 에러입니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/address/addAdress")
	public @ResponseBody String addAdress(HttpServletRequest request) {
		
		int num = 0;
		
		try
		{
			num = appGss.addAddress(
					request.getParameter("mId"),
					request.getParameter("aName"),					
					request.getParameter("aZipcode"),
					request.getParameter("aMain"),
					request.getParameter("aSub"),
					request.getParameter("aPrimary")
					);
			System.out.println("Added : "+num);
		} catch (Exception e)
		{			
			e.printStackTrace();
		} 
		
		JSONObject obj = new JSONObject();
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","배송지가 추가되었습니다.");
		} else if(num==2) {
			obj.put("code","fail");
			obj.put("desc","이미 존재하는 배송지명입니다.");
		} else {
			obj.put("code","fail");
			obj.put("desc","배송지 추가에 실패하였습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/address/getList")
	public @ResponseBody String getAddressList(HttpServletRequest request) {
		
		JSONObject obj = new JSONObject();
		
		int num = 0;
		
		List<Map> addressList = new ArrayList<>();
		
		try
		{
			List<AddressDto> list = appGss.getAddressList(request.getParameter("mId"));
			if(!list.isEmpty()) {
				for(AddressDto dto: list) {
					Map<String, String> address = new HashMap<>();
					address.put("aName", dto.getaName());
					address.put("aZipcode", dto.getaZipcode());
					address.put("aMain", dto.getaMain());
					address.put("aSub", dto.getaSub());
					address.put("aPrimary", dto.getaPrimary());	
					
					addressList.add(address);					
				}
				num = 1;
			}
			
		} catch (Exception e)
		{			
			e.printStackTrace();
		} 	
		
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","배송지 목록을 불러왔습니다.");
			obj.put("addressList", addressList);
		} else {
			obj.put("code","fail");
			obj.put("desc","저장된 배송지가 없습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/address/delete")
	public @ResponseBody String deleteAddress(HttpServletRequest request) {
		
		JSONObject obj = new JSONObject();
		
		int num = appGss.deleteAddress(
				request.getParameter("mId"), 
				request.getParameter("aName")
				);
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","배송지를 삭제하였습니다.");			
		} else {
			obj.put("code","fail");
			obj.put("desc","배송지 삭제에 실패하였습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/address/modify")
	public @ResponseBody String modifyAInfo(HttpServletRequest request) {
		
		int num = 0;
		
		try
		{
			num = appGss.modifyAInfo(					
					request.getParameter("mId"),
					request.getParameter("oriName"),
					request.getParameter("aName"),					
					request.getParameter("aZipcode"),
					request.getParameter("aMain"),
					request.getParameter("aSub"),
					request.getParameter("aPrimary")
					);
			System.out.println("Added : "+num);
		} catch (Exception e)
		{			
			e.printStackTrace();
		} 
		
		JSONObject obj = new JSONObject();
		
		if(num==1) {
			obj.put("code","success");
			obj.put("desc","배송지 정보가 수정되었습니다.");
		} else if(num==2) {
			obj.put("code","fail");
			obj.put("desc","이미 존재하는 배송지명입니다.");
		} else {
			obj.put("code","fail");
			obj.put("desc","배송지 정보 수정에 실패하였습니다.");
		}
		
		return obj.toJSONString();
	}	
	
	@RequestMapping(value="/app/service/write", method=RequestMethod.POST)
	public @ResponseBody String wirteQnA(HttpServletRequest request, Model model) {

		JSONObject obj = new JSONObject();

		Map<String, Object> map = new HashMap<>();
		
		try 
		{
			
			String path = ResourceUtils.getFile(uploadPath).toPath().toString();
			
			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
			
			String mId = mhsr.getParameter("mId");
			String qTitle = mhsr.getParameter("qTitle");
			String qContent = mhsr.getParameter("qContent");			
			
//			System.out.println("cName :" + cName);
//			System.out.println("cPrice :" + cPrice);
//			System.out.println("cCount :" + cCount);
//			System.out.println("cDetail :" + cDetail);
			
			map.put("mId", mId);
			map.put("qTitle", qTitle);
			map.put("qContent", qContent);			
						
			List<MultipartFile> list = mhsr.getFiles("files");
			
			MultipartFile mfile = null;			
			
			for(int i=0;i<list.size();i++)
			{
				
//				System.out.println(list.get(i).toString());
				
				mfile = list.get(i);

				String oriName = new String(mfile.getOriginalFilename().getBytes(),"UTF-8");
				String saveFileName = getUuid() + "_" + oriName;
				File serverFullName = new File(path + File.separator + saveFileName);
								
//				System.out.println("oriName : " + oriName);
//				System.out.println("saveFileName : " + saveFileName);
//				System.out.println("serverFullName : " + serverFullName);

				mfile.transferTo(serverFullName);
				
				if("".equals(oriName)) { 
					System.out.println(1111);
					map.put("file" + i, "null");
					continue; 
				}
				
				map.put("file" + (i+1), saveFileName);
				
				
			}
			
			for(int i=list.size()+1; i<4;i++) {
				System.out.println(i);
				map.put("file" + i, "null");				
			}
			
//			Iterator<String> keys = map.keySet().iterator();			
//			while( keys.hasNext() ){
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
		
		int result = appGss.writeQnA(map);
		
		if(result == 1) {
			obj.put("code", "success");
			obj.put("desc", "문의사항이 등록되었습니다.");
			obj.put("Uploaded Photo", map);
		} else {
			obj.put("code", "fail");
			obj.put("desc", "문의사항 등록에 실패했습니다.");
		}
		
		System.out.println("문의글 등록 완료.");
		
		return obj.toJSONString();
	}
	
	public static String getUuid(){
		String uuid = UUID.randomUUID().toString();
		//System.out.println(uuid);		
		uuid = uuid.replaceAll("-", "");
		//System.out.println("생성된UUID:"+ uuid);
		return uuid;
	}
	
	@RequestMapping("/app/service/getList")
	public @ResponseBody String getQnAList(HttpServletRequest request) {
		
		JSONObject obj = new JSONObject();
		
		int num = 0;
		
		List<Map> qnaList = new ArrayList<>();
		
		try
		{
			List<QnADto> qnaInfos = appGss.getQnAList(request.getParameter("mId"));
			
			if(!qnaInfos.isEmpty()) {			
				for(QnADto dto: qnaInfos) {
					Map<String, String> qna = new HashMap<>();
					qna.put("qId", dto.getQId());
					qna.put("mId", dto.getMId());
					qna.put("qTitle", dto.getQTitle());
					qna.put("qContent", dto.getQContent());
					qna.put("qDate", dto.getQDate());
					qna.put("qGroup", dto.getQGroup());
					qna.put("qStep", dto.getQStep());
					qna.put("qIndent", dto.getQIndent());
					qna.put("qPhoto1", dto.getQPhoto1());
					qna.put("qPhoto2", dto.getQPhoto2());
					qna.put("qPhoto3", dto.getQPhoto3());
					qna.put("qRead", dto.getQRead());
					
					
					qnaList.add(qna);					
				}
				num = 1;
			}
			
		} catch (Exception e)
		{			
			e.printStackTrace();
		} 	
		
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","문의글 목록을 불러왔습니다.");
			obj.put("QnAList", qnaList);
		} else {
			obj.put("code","fail");
			obj.put("desc","작성된 문의글이 없습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/service/delete")
	public @ResponseBody String deleteQnA(HttpServletRequest request) {
		
		JSONObject obj = new JSONObject();
		
		int num = appGss.deleteQnA(
				request.getParameter("qId")				
				);
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","문의글을 삭제하였습니다.");			
		} else {
			obj.put("code","fail");
			obj.put("desc","문의글 삭제에 실패하였습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping(value="/app/service/modify", method=RequestMethod.POST)
	public @ResponseBody String modifyQnA(HttpServletRequest request, Model model) {

		JSONObject obj = new JSONObject();

		Map<String, Object> map = new HashMap<>();
		
		try 
		{
			
			String path = ResourceUtils.getFile(uploadPath).toPath().toString();
			
			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
			
			String qId = mhsr.getParameter("qId");
			String qTitle = mhsr.getParameter("qTitle");
			String qContent = mhsr.getParameter("qContent");			
			
//			System.out.println("cName :" + cName);
//			System.out.println("cPrice :" + cPrice);
//			System.out.println("cCount :" + cCount);
//			System.out.println("cDetail :" + cDetail);
			
			map.put("qId", qId);
			map.put("qTitle", qTitle);
			map.put("qContent", qContent);			
						
			List<MultipartFile> list = mhsr.getFiles("files");
			
			MultipartFile mfile = null;			
			
			for(int i=0;i<list.size();i++)
			{
				
//				System.out.println(list.get(i).toString());
				
				mfile = list.get(i);

				String oriName = new String(mfile.getOriginalFilename().getBytes(),"UTF-8");
				String saveFileName = oriName;
				File serverFullName = new File(path + File.separator + saveFileName);
								
//				System.out.println("oriName : " + oriName);
//				System.out.println("saveFileName : " + saveFileName);
//				System.out.println("serverFullName : " + serverFullName);

				mfile.transferTo(serverFullName);
				
				if("".equals(oriName)) { 
					System.out.println(1111);
					map.put("file" + i, "null");
					continue; 
				}
				
				map.put("file" + (i+1), saveFileName);
				
				
			}
			
			for(int i=list.size()+1; i<4;i++) {
				System.out.println(i);
				map.put("file" + i, "null");				
			}
			
//			Iterator<String> keys = map.keySet().iterator();			
//			while( keys.hasNext() ){
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
		
		int result = appGss.modifyQnA(map);
		
		if(result == 1) {
			obj.put("code", "success");
			obj.put("desc", "문의글이 수정되었습니다.");
			obj.put("Uploaded Photo", map);
		} else {
			obj.put("code", "fail");
			obj.put("desc", "문의글 수정에 실패했습니다.");
		}
		
		System.out.println("문의글 수정 완료.");
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/service/getModified")
	public @ResponseBody String getModifiedQnA(HttpServletRequest request) {
		
		JSONObject obj = new JSONObject();
		
		int num = 0;
		
		Map<String, String> modifiedQnA = new HashMap<>();
		
		try
		{
			System.out.println("Dto");
			QnADto qnaInfos = appGss.getModifiedQnA(request.getParameter("qId"));	
			
			modifiedQnA.put("qId", qnaInfos.getQId());
			modifiedQnA.put("mId", qnaInfos.getMId());
			modifiedQnA.put("qTitle", qnaInfos.getQTitle());
			modifiedQnA.put("qContent", qnaInfos.getQContent());
			modifiedQnA.put("qDate", qnaInfos.getQDate());
			modifiedQnA.put("qGroup", qnaInfos.getQGroup());
			modifiedQnA.put("qStep", qnaInfos.getQStep());
			modifiedQnA.put("qIndent", qnaInfos.getQIndent());
			modifiedQnA.put("qPhoto1", qnaInfos.getQPhoto1());
			modifiedQnA.put("qPhoto2", qnaInfos.getQPhoto2());
			modifiedQnA.put("qPhoto3", qnaInfos.getQPhoto3());
			modifiedQnA.put("qRead", qnaInfos.getQRead());									
				
			num = 1;			
			
		} catch (Exception e)
		{			
			e.printStackTrace();
		} 	
		
		
		if(num==1)
		{
			obj.put("code","success");
			obj.put("desc","수정된 문의글을 불러왔습니다.");
			obj.put("modifiedQnA", modifiedQnA);
		} else {
			obj.put("code","fail");
			obj.put("desc","수정된 문의글을 불러오지 못 했습니다.");
		}
		
		System.out.println("End");
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/app/product/addPaidInfo")
	public @ResponseBody String addPaidInfo(HttpServletRequest request) {
		
		JSONObject obj = new JSONObject();

		Map<String, Object> map = new HashMap<>();
		
		String mId = request.getParameter("mId");
		String pcid = request.getParameter("pcid");
		String pcount = request.getParameter("pcount");
		String paddress = request.getParameter("paddress");
		String pdeliverymemo = request.getParameter("pdeliverymemo");
		String totalprice = request.getParameter("paid");

		map.put("mId", mId);
		map.put("pcid", pcid);
		map.put("pcount", pcount);
		map.put("paddress", paddress);
		map.put("pdeliverymemo", pdeliverymemo);
		map.put("totalprice", totalprice);
		
		int result = appGss.addPaidInfo(map);
		
		if(result == 1) {
			obj.put("code", "success");
			obj.put("desc", "결제가 성공했습니다.");			
		} else {
			obj.put("code", "fail");
			obj.put("desc", "결제가 실패했습니다.");
		}
		
		System.out.println("결제 프로세스 완료.");
		
		return obj.toJSONString();
	}
	
//	@RequestMapping("/app/product/getPaidList")
//	public @ResponseBody String getPaidList(HttpServletRequest request) {
//		
//		JSONObject obj = new JSONObject();
//		
//		int num = 0;
//		
//		List<Map> paidList = new ArrayList<>();
//		
//		try
//		{
//			List<PaidDto> list = appGss.getPaidList(request.getParameter("mId"));
//			if(!list.isEmpty()) {
//				for(PaidDto dto: list) {
//					Map<String, String> paid = new HashMap<>();
//					paid.put("pId", dto.getPId());
//					paid.put("mId", dto.getMId());
//					paid.put("pcId", dto.getPcId());
//					paid.put("pCount", dto.getPCount());
//					paid.put("pAddress", dto.getPAddress());
//					paid.put("pDeliverymemo", dto.getPDeliverymemo());
//					paid.put("totalPrice", dto.getTotalPrice());
//					paid.put("pDate", dto.getPDate());
//					
//					paidList.add(paid);					
//				}
//				num = 1;
//			}
//			
//		} catch (Exception e)
//		{			
//			e.printStackTrace();
//		} 	
//		
//		
//		if(num==1)
//		{
//			obj.put("code","success");
//			obj.put("desc","주문 내역을 불러왔습니다.");
//			obj.put("paidList", paidList);
//		} else {
//			obj.put("code","fail");
//			obj.put("desc","주문 내역이 없습니다.");
//		}
//		
//		return obj.toJSONString();
//	}
	
	
	
}
