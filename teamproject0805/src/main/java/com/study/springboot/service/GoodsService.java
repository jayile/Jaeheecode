package com.study.springboot.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.study.springboot.BPageInfo;
import com.study.springboot.dao.IAppGSDao;
import com.study.springboot.dao.IGDao;
import com.study.springboot.dto.GDto;
import com.study.springboot.dto.OrderDto;
import com.study.springboot.dto.PaidDto;
import com.study.springboot.dto.WDto;

@Service
public class GoodsService implements IGoodsService {
	
	@Autowired
	IGDao dao;
	@Autowired
	IAppGSDao appDao;
	
	int listCount = 8;		// 한 페이지당 보여줄 상품의 갯수
	int pageCount = 5;
	
	
	public int goods_write(Map map) {
		
		int result = 0;
		int i = 0;
		
		for (Object key : map.values()) {
			  System.out.println("파라미터 : " + key);
			  i++;
			}
		System.out.println("i : " + i);
		
		if ( i == 7 ) {
			result = dao.goods_writeDao1(map);
		} else if ( i == 8 ) {
			result = dao.goods_writeDao2(map);
		}
		
		return result;
	}
	
	@Override
	public int del_goods(String cId) {
		int result = dao.del_goodsDao(cId);
		return result;
	}
	
	@Override
	public void goods_list(HttpServletRequest request, Model model) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = articlePage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<GDto> dtos = dao.goods_listDao(nStart, nEnd);
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo articlePage(int curPage) {

		// 총 게시물의 갯수
		int totalCount = dao.selectCountDao();

		// 총 페이지 수
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0)
		    totalPage++;
		
		// 현재 페이지
		int myCurPage = curPage;
		if (myCurPage > totalPage)
			myCurPage = totalPage;
		if (myCurPage < 1)
			myCurPage = 1;

		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;

		// 끝 페이지
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage) 
		    endPage = totalPage;

		// 빈으로 처리 - 약한 결합
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}

	@Override
	public GDto goods_view(String cId, HttpServletRequest request, Model model) {
		goods_reviewList(cId, request, model);
		avg_Score(cId, request, model);
		return dao.goods_viewDao(cId);
	}
	
	public void avg_Score(String cId, HttpServletRequest request, Model model) {
		List<GDto> avgscore =  dao.avgScoreDao(cId);
		model.addAttribute("avgscore", avgscore);
	}
	
	@Override
	public void search_goods(String search, String word, HttpServletRequest request, Model model) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = search_articlePage(search, word, nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("searchword", search);
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<GDto> dtos = dao.search_goodsDao(word, nStart, nEnd);
		
//		for(Object ob: dtos) {
//			System.out.println(ob.toString());
//		}	// 데이터 확인용
		
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo search_articlePage(String search, String word, int curPage) {

		int totalCount = 0;
		
		if(search == null || search.equals("")) {
			totalCount = dao.selectCountDao();
		} else {
			totalCount = dao.search_selectCountDao(word);
		}
		
		// 총 페이지 수
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0)
		    totalPage++;
		
		// 현재 페이지
		int myCurPage = curPage;
		if (myCurPage > totalPage)
			myCurPage = totalPage;
		if (myCurPage < 1)
			myCurPage = 1;

		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;

		// 끝 페이지
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage) 
		    endPage = totalPage;

		// 빈으로 처리 - 약한 결합
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	@Override
	public void kind_goods(String kind, HttpServletRequest request, Model model) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = kind_articlePage(kind, nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("kind", kind);
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<GDto> dtos = dao.kind_goodsDao(kind, nStart, nEnd);
		
//		for(Object ob: dtos) {
//			System.out.println(ob.toString());
//		}	// 데이터 확인용
		
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo kind_articlePage(String kind, int curPage) {

		int totalCount = 0;
		
		totalCount = dao.kind_selectCountDao(kind);

		
		// 총 페이지 수
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0)
		    totalPage++;
		
		// 현재 페이지
		int myCurPage = curPage;
		if (myCurPage > totalPage)
			myCurPage = totalPage;
		if (myCurPage < 1)
			myCurPage = 1;

		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;

		// 끝 페이지
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage) 
		    endPage = totalPage;

		// 빈으로 처리 - 약한 결합
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	@Override
	public int goods_put1(Map map) {
		
		int add = dao.goods_confirmDao(map);
		int result = 0;
		
		if(add == 0) {
			result = dao.goods_put1Dao(map);
		} else {
			result = dao.goods_upDao(map);
		}
		
		return result;
	}
	
	@Override
	public int goods_put2(Map map) {
		
		int add = dao.goods_confirmDao(map);
		int result = 0;
		
		if(add == 0) {
			result = dao.goods_put2Dao(map);
		} else {
			result = dao.goods_upDao(map);
		}
		
		return result;
	}

	@Override
	public void cart_list(String userId, HttpServletRequest request, Model model) {
		
		List<GDto> dtos = dao.cart_listDao(userId);		
		model.addAttribute("list", dtos);
	}
	
	@Override
	public int cart_delete(String cId, String userId, HttpServletRequest request, Model model) {
		int result = dao.cart_deleteDao(cId, userId);
		return result;
	}
	
	@Override
	public int cart_Alldelete(String userId, HttpServletRequest request, Model model) {
		int result = dao.cart_AlldeleteDao(userId);
		return result;
	}
	
	@Override
	public int goods_review_write(Map map) {
		int result = dao.review_writeDao(map);
		dao.up_reviewDao(map);
		return result;
	}
	
	@Override
	public void goods_reviewList(String cId, HttpServletRequest request, Model model) {
		List<GDto> rDtos = dao.review_ListDao(cId);
		model.addAttribute("review", rDtos);
	}
	
	@Override
	public int delete_review(String userId, String cId, String rContent) {
		int nResult = dao.delete_reviewDao(userId, cId, rContent);
		dao.down_reviewDao(cId);
		return nResult;
	}
	
	@Override
	public int modify_review(String mId, String cId, String rScore, String rContent, String rId) {
		int nResult = dao.modify_reviewDao(mId, cId, rScore, rContent, rId);
		return nResult;
	}
	
	@Override
	public void reviewList(String mId, HttpServletRequest request, Model model) {
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = myreview_articlePage(mId, nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<GDto> dtos = dao.reviewListDao(mId, nStart, nEnd);
		model.addAttribute("list", dtos);
		
//		for(Object ob: dtos) {
//			System.out.println(ob.toString());
//		}	// 데이터 확인용
	}
	
	public BPageInfo myreview_articlePage(String mId, int curPage) {

		// 총 게시물의 갯수
		int totalCount = dao.selectReviewCountDao(mId);

		// 총 페이지 수
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0)
		    totalPage++;
		
		// 현재 페이지
		int myCurPage = curPage;
		if (myCurPage > totalPage)
			myCurPage = totalPage;
		if (myCurPage < 1)
			myCurPage = 1;

		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;

		// 끝 페이지
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage) 
		    endPage = totalPage;

		// 빈으로 처리 - 약한 결합
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	public int delete_myreview(String rId, String cId) {
		int nResult = dao.delete_myreviewDao(rId);
		dao.down_reviewDao(cId);
		return nResult;
	}
	
	public int modify_goods(String cId, String cName, String cPrice, String cCount, String cDown) {
		int nResult = dao.modify_goodsDao(cId, cName, cPrice, cCount, cDown);
		return nResult;
	}
	
	// 위시리스트
	@Override
	public int add_wish(String mid, String cId) {
		int nResult = dao.add_wish(mid, cId);
		return nResult;
	}
	
	@Override
	public int del_wish(String mid, String cId) {
		int nResult = dao.del_wish(mid, cId);
		return nResult;
	}
	
	@Override
	public WDto wish_list(String mid, String cId) {
		return dao.wish_list(mid, cId);
	}
	
	// 결제페이지
	@Override
	public int totalP(String mid) {
		int total = 0;
		int price = 0;
		int count = 0;
		
		try {
			List<GDto> dtos = dao.get_price(mid);
			for(int i=0; i<dtos.size(); i++) {
				GDto data = dtos.get(i);
				price = data.getcPrice();
				count = data.getCount();
				total = total+(price*count);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("총합:"+total);
		
		
		return total;
	}
	
	@Override
	public String add_paydb(String mid, String aZip, String aMain, String aSub, String memo, String adrname) {
		int nResult = 0;
		String orderdone = "";
		try {
			
			int cid = 0;
			String cname = "";
			String cphoto = "";
			int count = 0;
			int price = 0;
			int order_price = 0;
			
			int goods_count = 0;
			int cCounts = 0;
			
			
			//0. 주문번호 지정
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat timefrm = new SimpleDateFormat("yyyyMMdd");
			
			String order_day = timefrm.format(timestamp);
			String order_num = dao.add_ordernumber();
			System.out.println("현재번호 : "+ order_num);
			String pid = order_day + order_num;
			
			//1. 총합 불러오기
			int total = totalP(mid);
			
			// 2. 이름, 수량 기록
			List<GDto> dtos = dao.get_price(mid);
			for(int i=0; i<dtos.size(); i++) {
				GDto data = dtos.get(i);
				cid = data.getcId();
				cname = data.getcName();
				cphoto = data.getcPhoto1();
				price = data.getcPrice();
				count = data.getCount();
				
				// 재고 빼기
				goods_count = data.getcCount();
				cCounts = ( goods_count - count );
				if(cCounts > 0) {
					dao.minus_count(cid, cCounts);
					dao.del_cart(cid);	// 이상태면 중간에서 재고가 0인 상품이 발견되어도 앞의것들은 삭제된채임
					
					order_price = price * count;
					dao.add_orderdb(pid, mid, cid, count, price, order_price, cname, cphoto);		// 주문 테이블에 기록
					
				} else {
					// 결과가 0보다 작으면 주문불가하다고 떠야함
					nResult = -1;
				}

			}
			
			if (nResult != -1) {
				int zipcode = Integer.parseInt(aZip);
				nResult = dao.add_paydb(pid, mid, total, zipcode, aMain, aSub, memo, adrname);		// 결제 테이블에 기록
				orderdone = ordernumber(pid, nResult);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderdone;
	}

	public String ordernumber(String pid, int result) {
		String ordernum = "order_error";
		if(result == 1) {
			ordernum = pid;
		}
		
		return ordernum;
	}
	
	@Override
	public void get_paydata(String mid, Model model) {
		List<OrderDto> paydata = dao.get_pay(mid);
		model.addAttribute("list", paydata);
	}
	
	@Override
	public void get_payinfo(String pid, Model model) {
		List<OrderDto> orderdata = dao.order_payinfo(pid);
		PaidDto paydata = dao.pay_payinfo(pid);

		model.addAttribute("order", orderdata);
		model.addAttribute("pay", paydata);
		
	}
	
}
