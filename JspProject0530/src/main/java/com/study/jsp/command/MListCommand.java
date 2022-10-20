package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BPageInfo;
import com.study.jsp.dao.MemberDao;
import com.study.jsp.dto.MemberDto;


public class MListCommand implements BCommand {
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("do MList");
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch(Exception e) {
		}
		
		MemberDao mDao = MemberDao.getInstance();
		BPageInfo pinfo = mDao.memberPage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = null;
		session = request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<MemberDto> mDtos = mDao.mList(nPage);
		request.setAttribute("mlist", mDtos);
	}
}
