package com.study.springboot.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.study.springboot.BPageInfo;
import com.study.springboot.dao.ICDao;
import com.study.springboot.dto.CDto;
import com.study.springboot.dto.GDto;

@Service
public class CustomerService  implements ICustomerService
{
	@Autowired
	ICDao cDao;
	
	int listCount = 8;		// 한 페이지당 보여줄 상품의 갯수
	int pageCount = 5;
	
	@Override
	public int customer_write(Map map) {
		
		int result = 0;
		int i = 0;
		
		for (Object key : map.values()) {
			  System.out.println("파라미터 : " + key);
			  i++;
			}
		System.out.println("i : " + i);
		
		if( i == 4 ) {
			result = cDao.customer_write3Dao(map);
		} else if ( i == 5 ) {
			result = cDao.customer_write2Dao(map);
		} else {
			result = cDao.customer_write1Dao(map);
		}
				
		return result;
	}
	
	@Override
	public void qna_list(String mid, HttpServletRequest request, Model model) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = article_qnaPage(mid, nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<CDto> dtos = cDao.qna_listDao(mid, nStart, nEnd);
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo article_qnaPage(String mid, int curPage) {

		// 총 게시물의 갯수
		int totalCount = cDao.selectCountDao(mid);

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
	public CDto qna_view(String qId, HttpServletRequest request, Model model) {
		
		return cDao.qna_viewDao(qId);
	}
	
	@Override
	public void all_qna_list(HttpServletRequest request, Model model) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {  }
		
		BPageInfo pinfo = all_qnaPage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		model.addAttribute("cpage", nPage);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		List<CDto> dtos = cDao.all_qna_listDao(nStart, nEnd);
		model.addAttribute("list", dtos);
	}
	
	public BPageInfo all_qnaPage(int curPage) {

		// 총 게시물의 갯수
		int totalCount = cDao.all_qnaCountDao();

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
	public int qna_reply_write(Map map) {
		int result = cDao.qna_reply_writeDao(map);
		if (result == 1) {
			cDao.qna_anwser_updateDao(map);
		}
		return result;
	}
}
