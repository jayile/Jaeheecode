package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BPageInfo;
import com.study.jsp.dao.BDao;
import com.study.jsp.dao.SDao;
import com.study.jsp.dto.BDto;

public class NoticelistCommand implements BCommand {
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("do NList");
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch(Exception e) {
		}
		
		BDao dao = BDao.getInstance();
		BPageInfo pinfo = dao.articlePage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = null;
		session = request.getSession();
		session.setAttribute("cpage", nPage);
		
		SDao sDao = SDao.getInstance();
		ArrayList<BDto> bDtos = sDao.nlist(nPage);
		request.setAttribute("list", bDtos);
		
		System.out.println("aaa : " + bDtos);
	}
}
