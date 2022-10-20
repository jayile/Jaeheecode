package com.study.springboot.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.study.springboot.BPageInfo;
import com.study.springboot.dao.IBDao;
import com.study.springboot.dto.BDto;

@Service
public class BbsService implements IBbsService
{
	@Autowired
	IBDao dao;
	
	int listCount = 10;		// 한 페이지당 보여줄 게시물의 갯수
	int pageCount = 5;
	
	@Override
	public void list(HttpServletRequest request, Model model) {
		
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
		
		List<BDto> dtos = dao.listDao(nStart, nEnd);
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
	public BDto view(String bId) {
		return dao.viewDao(bId);
	}
	
	@Override
	public int upHit(String bId) {
		int result = dao.upHitDao(bId);
		return result;
	}
	
	@Override
	public int write(Map<String, String> map) {
		int result = dao.writeDao(map);
		return result;
	}
	
	@Override
	public int modify(Map<String, String> map) {
		int result = dao.modifyDao(map);
		return result;
	}
	
	@Override
	public int delete(String bid) {
		return dao.deleteDao(bid);
	}
	
	@Override
	public void noticelist(HttpServletRequest request, Model model) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = notice_articlePage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<BDto> dtos = dao.noticelistDao(nStart, nEnd);
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo notice_articlePage(int curPage) {

		// 총 게시물의 갯수
		int totalCount = 0;
		totalCount = dao.noticeCountDao();

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
	public int notice_write(Map<String, String> map) {
		int result = dao.notice_writeDao(map);
		return result;
	}
	
	@Override
	public BDto notice_modify_view(String bId) {
		return dao.notice_modify_viewDao(bId);
	}
	
	@Override
	public int notice_modify_do(Map<String, String> map) {
		int result = dao.notice_modify_doDao(map);
		return result;
	}
	
	@Override
	public int notice_delete(String bId) {
		int result = dao.notice_deleteDao(bId);
		return result;
	}
}
