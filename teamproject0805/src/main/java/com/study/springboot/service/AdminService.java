package com.study.springboot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.study.springboot.BPageInfo;
import com.study.springboot.dao.IADao;
import com.study.springboot.dao.IGDao;
import com.study.springboot.dto.BlackDto;
import com.study.springboot.dto.GDto;
import com.study.springboot.dto.MDto;
import com.study.springboot.dto.OrderDto;
import com.study.springboot.dto.PaidDto;

@Service
public class AdminService implements IAdminService
{
	@Autowired
	IADao adao;
	
	
	int listCount = 5;		// 한 페이지당 보여줄 멤버 갯수
	int pageCount = 5;
	
	
	@Override
	public void userlist(HttpServletRequest request, Model model) {
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
		
		List<MDto> dtos = adao.memberlist(nStart, nEnd);
		model.addAttribute("list", dtos);
		
	}
	
	public BPageInfo articlePage(int curPage) {

		// 총 게시물의 갯수
		int totalCount = adao.countUser();

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
	public void searchUserlist(String word, HttpServletRequest request, Model model) {
		String word_frm = "%" + word + "%";
		System.out.println("검색어 변환 : "+word_frm);
		
		int nPage = 1;
		
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = search_articlePage(word_frm, nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("searchword", word);
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;

		
		List<MDto> dtos = adao.searchMemberList(word_frm, nStart, nEnd);
		
//		for(Object ob: dtos) {
//		System.out.println(ob.toString());
//	}	// 데이터 확인용
		
		model.addAttribute("list", dtos);
		
		
	}
	
	public BPageInfo search_articlePage(String word, int curPage) {

		int totalCount = 0;

		if(word == null || word.equals("")) {
			totalCount = adao.countUser();
		} else {
			totalCount = adao.searchCountUser(word);
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
	public void userinfo(String mid, Model model) {
		MDto dtos = adao.memberinfo(mid);
		model.addAttribute("ulist", dtos);
		
		BlackDto bdto = adao.blackinfo(mid);
		if (bdto != null) {
			model.addAttribute("blist", bdto);
		}
		// 해당유저 구매목록
		userinfo_pay(mid, model);
		// 해당 유저 리뷰 목록
		userinfo_review(mid, model);
	}
	
	@Override
	public void userinfo_pay(String mid, Model model) {
		List<OrderDto> userpay = adao.user_paylist(mid);
		model.addAttribute("plist", userpay);
	}
	
	@Override
	public void userinfo_review(String mid, Model model) {
		List<GDto> userreivew = adao.user_reviewlist(mid);
		model.addAttribute("rlist", userreivew);
	}
	
	@Override
	public int addBList(String mid, String btype, String bmemo) {
		int typenum = Integer.parseInt(btype);
		int step2 = 0;
		int nResult = 0;
		
		int step1 = adao.addBList(mid, typenum, bmemo);
		if (step1 == 1) {
			if(typenum == 1) {
				step2 = adao.modiEnabledA(typenum, mid);
			} else if(typenum == 2) {
				step2 = adao.modiEnabledB(typenum, mid);
			}
			
			if(step2 == 1) {
				nResult = 1;
			}	
		} 
		return nResult;
	}
	
	@Override
	public void blacklist(HttpServletRequest request, Model model) {
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = blackPage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<BlackDto> dtos = adao.blacklist(nStart, nEnd);
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo blackPage(int curPage) {

		// 총 게시물의 갯수
		int totalCount = adao.countBlack();

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
	public int delBList(String mid) {
		int nResult = 0;
		
		try {
			int step1 = adao.delBlack(mid);
			int step2 = adao.returnEnabled(mid);
			if(step1 == step2) {
				nResult = 1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return nResult;
	}
	
	@Override
	public void paylist(HttpServletRequest request, Model model) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = payPage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<PaidDto> dtos = adao.payinfo(nStart, nEnd);
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo payPage(int curPage) {

		// 총 게시물의 갯수
		int totalCount = adao.countPay();

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
	public void paylist_info(String pid, Model model) {
		List<OrderDto> orderdata = adao.order_payinfo(pid);
		PaidDto paydata = adao.pay_payinfo(pid);

		model.addAttribute("order", orderdata);
		model.addAttribute("pay", paydata);
	}
	
	@Override
	public int deliveryStart(String pid) {
		int change = adao.delivery_start(pid);
		
		return change;
	}
}
