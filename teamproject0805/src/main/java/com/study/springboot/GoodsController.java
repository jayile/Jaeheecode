package com.study.springboot;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import com.study.springboot.oauth2.SessionUser;
import com.study.springboot.service.IGoodsService;
import com.study.springboot.service.IMService;

@Controller
public class GoodsController
{
	@Autowired
	IGoodsService goods;
	
	@Autowired
	IMService ims;
	
	@Autowired
	private HttpSession httpSession;
	
	@Value("${upload.path}")
    private String uploadPath;
	
	String search = "";
	
	public Model usercheck(Model model) {
		String mid = (String)httpSession.getAttribute("mid");

		if(mid != null) {
			model.addAttribute("uinfo", ims.userinfo(mid));
		} 
		return model;
	}
	
	@RequestMapping("/")
	public String root(HttpServletRequest request, Model model) throws Exception {
		usercheck(model);
		
		goods.goods_list(request, model);
		return "main";
	}
	
	@RequestMapping("/admin/goods_form")
	public String goods_form(HttpServletRequest request, Model model) {
		usercheck(model);
		
		return "goods_form";
	}
	
	@RequestMapping(value="/admin/goods_write", method=RequestMethod.POST)
	public @ResponseBody String goods_write(HttpServletRequest request, Model model) {

		JSONObject obj = new JSONObject();

		Map<String, Object> map = new HashMap<>();
		
		try {
				String path = ResourceUtils.getFile(uploadPath).toPath().toString();
				
				MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
				
				Iterator<String> itr = mhsr.getFileNames();
				
				MultipartFile mfile = null;			
				String fileName = "";
				
//				List resultList = new ArrayList();
				
				String kind = mhsr.getParameter("kind");
				String cName = mhsr.getParameter("cName");
				String cPrice = mhsr.getParameter("cPrice");
				String cCount = mhsr.getParameter("cCount");
				String cDetail = mhsr.getParameter("cDetail");
				
				map.put("kind", kind);
				map.put("cName", cName);
				map.put("cPrice", cPrice);
				map.put("cCount", cCount);
				map.put("cDetail", cDetail);
				
				int i = 1;
				// 업로드폼의 file속성의 필드의 갯수만큼 반복
				while ( itr.hasNext() ) {
	
					fileName = (String)itr.next();
					System.out.println("fileName : " + fileName);	
					
					mfile = mhsr.getFile(fileName);
	//					System.out.println(mfile);			//CommonsMultipartFile@1366c0b 형태
					
					String oriName = new String(mfile.getOriginalFilename().getBytes(),"UTF-8"); 
					System.out.println("upload :" + oriName);
					
					if("".equals(oriName)) { 
						map.put("file" + i, "null");
						continue; 
					}	// 파일이 더 없으면 여기서 멈춘다.
					
					String saveFileName = getUuid() + "." + oriName;
					
					File serverFullName = new File(path + File.separator + saveFileName);	//서버에 저장된 전체경로 및 파일명
					
//					System.out.println("oriName : " + oriName);
//					System.out.println("saveFileName : " + saveFileName);
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
		
		int result = goods.goods_write(map);
		System.out.println("상품 등록 : " + result);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "상품이 등록되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "서버 오류로 상품 등록에 실패했습니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/admin/delete_goods")
	public @ResponseBody String del_goods(HttpServletRequest request, Model model) {
		JSONObject obj = new JSONObject();
		
		String cId = request.getParameter("cId");
		
		int result = goods.del_goods(cId);
		System.out.println("상품 등록 : " + result);
		
		if(result == 1) {
			obj.put("code", "ok");
			obj.put("desc", "상품이 삭제되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "서버 오류로 인해 상품 삭제에 실패했습니다.");
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
	
	@RequestMapping("/goods/goods_list")
	public String goods_list(HttpServletRequest request, Model model) {
		usercheck(model);
		
		goods.goods_list(request, model);
		return "goods_list";
	}
	
	@RequestMapping("/goods/goods_view")
	public String goods_view(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String cId = request.getParameter("cId");
		
		// 위시리스트 가져오기
		get_wishList(model, cId);
		
		model.addAttribute("dto", goods.goods_view(cId, request, model));
		return "goods_view";
	}
	
	@RequestMapping("/goods/goods_search")
	public String search_goods(HttpServletRequest request, Model model) {
		usercheck(model);
		
		search = request.getParameter("word");
		String word = "%" + search + "%";
		System.out.println("word : " + word);

		goods.search_goods(search, word, request, model);
		return "search_goods_list";
	}
	
	@RequestMapping("/goods/goods_kind")
	public String kind_goods(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String kind = request.getParameter("kind");
		System.out.println("kind : " + kind);

		goods.kind_goods(kind, request, model);
		return "kind_goods_list";
	}
	
	@RequestMapping("/goods/goods_put")
	public @ResponseBody String goods_put(HttpServletRequest request, Model model) {
		usercheck(model);
		
		int result = 0;
		
		JSONObject obj = new JSONObject();
		
		String mid = (String)httpSession.getAttribute("mid");
		int cDown = Integer.parseInt(request.getParameter("cDown"));
		String goodsId = request.getParameter("cId");
		String goodsCount = request.getParameter("gCount");
		System.out.println("cDown : " + cDown);
		System.out.println("goodsId : " + goodsId);
		System.out.println("goodsCount : " + goodsCount);

		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", mid);
		map.put("goodsId", goodsId);
		map.put("goodsCount", goodsCount);
		
		if( cDown == 0 ) {
			result = goods.goods_put1(map);
		} else {
			result = goods.goods_put2(map);
		}
			
		
		
		System.out.println("result : " + result);
		if(result == 1) {
			obj.put("code", "ok");
		} else {
			obj.put("code", "fail");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/goods/direct_buy")
	public @ResponseBody String direct_buy(HttpServletRequest request, Model model) {
		usercheck(model);
		
		JSONObject obj = new JSONObject();
		
		String mid = (String)httpSession.getAttribute("mid");
		String goodsId = request.getParameter("cId");
		String goodsCount = request.getParameter("gCount");
		System.out.println("goodsId : " + goodsId);
		System.out.println("goodsCount : " + goodsCount);

		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", mid);
		map.put("goodsId", goodsId);
		map.put("goodsCount", goodsCount);
		
		int result = goods.goods_put1(map);
		System.out.println("result : " + result);
		if(result == 1) {
			obj.put("code", "ok");
		} else {
			obj.put("code", "fail");
		}
		
		return obj.toJSONString();
	}
	
	// 로그인 해야 접근 가능
	@RequestMapping("/goods_cart")
	public String goods_cart(HttpServletRequest request, Model model) {
		String mid = (String)httpSession.getAttribute("mid");		
		usercheck(model);
		
		if(mid != null) {
			String userId = mid;
			System.out.println("userId : " + userId);
			goods.cart_list(userId, request, model);
		}

		return "cart";
	}
	
	@RequestMapping("/goods/cart_delete")
	public @ResponseBody String cart_delete(HttpServletRequest request, Model model) {
		
		JSONObject json = new JSONObject();
		
		String mid = (String)httpSession.getAttribute("mid");
		
		usercheck(model);
		
		String cId = request.getParameter("cId");
		System.out.println("cId : " + cId);
		
		int result = 0;
		
		if (mid != null) {
			String userId = mid;
			System.out.println("userId : " + userId);
			result = goods.cart_delete(cId, userId, request, model);
		}

		if(result == 1) {
			json.put("code", "ok");
		} else {
			json.put("code", "fail");
		}
		
		return json.toJSONString();
	}
	
	@RequestMapping("/goods/cart_Alldelete")
	public @ResponseBody String cart_Alldelete(HttpServletRequest request, Model model) {
		
		JSONObject json = new JSONObject();
		
		String mid = (String)httpSession.getAttribute("mid");
		
		usercheck(model);
		
		String cId = request.getParameter("cId");
		System.out.println("cId : " + cId);
		
		int result = 0;
		
		if (mid != null) {
			String userId = mid;
			System.out.println("userId : " + userId);
			result = goods.cart_Alldelete(userId, request, model);
		}

		if(result > 0) {
			json.put("code", "ok");
		} else {
			json.put("code", "fail");
		}
		
		return json.toJSONString();
	}
	
	@RequestMapping("/goods/goods_review")
	public @ResponseBody String goods_review(HttpServletRequest request, Model model) {
				
		JSONObject json = new JSONObject();
		
		String mid = (String)httpSession.getAttribute("mid");
		
		String cId = request.getParameter("reviewcId");
		String rContent = request.getParameter("review");
		String score = request.getParameter("score");
		System.out.println("데이터 : " + cId + " / " + rContent + " / " + score);
		
		Map<String, Object> map = new HashMap<>();
		
		int result = 0;
		
		if (mid != null) {
			String userId = mid;
			map.put("userId", userId);
			map.put("cId", cId);
			map.put("rContent", rContent);
			map.put("score", score);
			result = goods.goods_review_write(map);
		}
		
		System.out.println("result : " + result);
		
		if(result == 1) {
			json.put("code", "ok");
		} else {
			json.put("code", "fail");
		}
		
		return json.toJSONString();
	}
	
	@RequestMapping("/goods/delete_review")
	public @ResponseBody String delete_review(HttpServletRequest request, Model model) {
		
		JSONObject json = new JSONObject();
		
		String mId = (String)httpSession.getAttribute("mid");
		String cId = request.getParameter("cId");
		String rContent = request.getParameter("rContent");
		System.out.println("데이터 : " + mId +" / " + cId + " / " + rContent);
		
		int result = 0;
		
		result = goods.delete_review(mId, cId, rContent);
		
		if(result == 1) {
			json.put("code", "ok");
		} else {
			json.put("code", "fail");
		}
		
		return json.toJSONString();
	}

	@RequestMapping("/modify_review")
	public @ResponseBody String modify_review(HttpServletRequest request, Model model) {
		
		JSONObject json = new JSONObject();
		
		String mId = (String)httpSession.getAttribute("mid");
		String cId = request.getParameter("cId");
		String rScore = request.getParameter("score");
		String rContent = request.getParameter("rContent");
		String rId = request.getParameter("rId");
		System.out.println("데이터 : "+ mId + " / " + cId +" / " + rScore + " / " + rContent + " / " + rId);
		
		int result = 0;
		
		result = goods.modify_review(mId, cId, rScore, rContent, rId);
		
		if(result == 1) {
			json.put("code", "ok");
			json.put("desc", "리뷰가 수정되었습니다.");
		} else {
			json.put("code", "fail");
			json.put("desc", "리뷰 수정에 실패했습니다.");
		}
		
		return json.toJSONString();
	}
	
	// 해당 유저가 작성한 리뷰 불러오기
	@RequestMapping("/member/myreview")
	public String myReview_List (HttpServletRequest request, Model model) {
		usercheck(model);
		String mId = (String)httpSession.getAttribute("mid");
		
		goods.reviewList(mId, request, model);		
		return "/member/myreview";
	}
	
	@RequestMapping("/member/delete_myreview")
	public @ResponseBody String delete_myreview (HttpServletRequest request, Model model) {
		
		JSONObject json = new JSONObject();
		
		usercheck(model);
		String mId = (String)httpSession.getAttribute("mid");
		String rId = request.getParameter("rId");
		String cId = request.getParameter("cId");
			
		int result = 0;
		
		result = goods.delete_myreview(rId, cId);
		
		if(result == 1) {
			json.put("code", "ok");
			json.put("desc", "리뷰가 삭제되었습니다.");
		} else {
			json.put("code", "fail");
			json.put("desc", "리뷰 삭제에 실패했습니다.");
		}
		
		return json.toJSONString();
	}
	
	@RequestMapping("/admin/goods_stock")
	public String goods_stock(HttpServletRequest request, Model model) {
		usercheck(model);
		
		goods.goods_list(request, model);
		return "goods_stock";
	}
	
	@RequestMapping("/admin/admin_goods_view")
	public String admin_goods_view(HttpServletRequest request, Model model) {
		usercheck(model);
		
		goods.goods_list(request, model);
		return "admin_goods_view";
	}
	
	@RequestMapping("/admin/modify_goods")
	public @ResponseBody String modify_goods(HttpServletRequest request, Model model) {
		
		JSONObject json = new JSONObject();
		usercheck(model);
		
		String cId = request.getParameter("cId");
		String cName = request.getParameter("cName");
		String cPrice = request.getParameter("cPrice");
		String cCount = request.getParameter("cCount");
		String cDown = request.getParameter("cDown");
		
		System.out.println("cDown : " + cDown);

		int result = 0;
		
		result = goods.modify_goods(cId, cName, cPrice, cCount, cDown);
		
		if(result == 1) {
			json.put("code", "ok");
			json.put("desc", "상품이 수정되었습니다.");
		} else {
			json.put("code", "fail");
			json.put("desc", "상품 수정에 실패했습니다.");
		}
		
		return json.toJSONString();
	}
	
	// 위시리스트 추가
	@RequestMapping("/goods/goods_wish")
	public @ResponseBody String add_wish(HttpServletRequest request, Model model) {
		usercheck(model);
		
		String mid = (String)httpSession.getAttribute("mid");
		String cId = request.getParameter("cId");
		System.out.println("데이터:"+mid+"/"+cId);
		
		int addDB = goods.add_wish(mid, cId);
		
		JSONObject obj = new JSONObject();
		if(addDB == 1) {
			obj.put("code", "ok");
			obj.put("desc", "찜목록에 추가되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "내부서버 오류입니다.");
		}

		return obj.toJSONString();
	}
	
	// 위시리스트에서 삭제
	@RequestMapping("/goods/goods_delwish")
	public @ResponseBody String del_wish(HttpServletRequest request, Model model) {
		usercheck(model);
		String mid = (String)httpSession.getAttribute("mid");
		String cId = request.getParameter("cId");


		if(mid == null) {
			mid = (String)httpSession.getAttribute("mid");
		}
		
		
		int delDB = goods.del_wish(mid, cId);
		
		JSONObject obj = new JSONObject();
		if(delDB == 1) {
			obj.put("code", "ok");
			obj.put("desc", "찜목록에서 삭제되었습니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "내부서버 오류입니다.");
		}

		return obj.toJSONString();
	}
	
	// 상품상세에서 위시리스트 불러오기
	public Model get_wishList(Model model, String cId) {
		String mid = (String)httpSession.getAttribute("mid");
		if(mid != null) {
			int usercheck = ims.idcheck(mid);
			
			if(usercheck == 1) {
				model.addAttribute("wish", goods.wish_list(mid, cId));
			}
		}
		return model;
	}
}
