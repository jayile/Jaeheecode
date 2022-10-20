package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BPageInfo;
import com.study.jsp.dao.MemberDao;
import com.study.jsp.dto.MemberDto;


public class SearchMCommand implements BCommand {
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("do serchMList");
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch(Exception e) {
		}
		
		String value = request.getParameter("value");
		
		MemberDao mDao = MemberDao.getInstance();
		BPageInfo pinfo = mDao.mSerchPage(nPage, value);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = null;
		session = request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<MemberDto> mDtos = mDao.serchmList(nPage, value);
		request.setAttribute("mlist", mDtos);
		
		System.out.println("value : " + value);
		System.out.println("pinfo : " + pinfo);
		System.out.println("nPage : " + nPage);
		System.out.println("mDtos : " + mDtos);
	}
}
