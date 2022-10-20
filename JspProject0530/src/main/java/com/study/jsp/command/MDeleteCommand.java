package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.MemberDao;

public class MDeleteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("Do out");
		
		
		MemberDao mDao = MemberDao.getInstance();
		
		HttpSession session = request.getSession();
		String mId = (String)session.getAttribute("mId");
		
		mDao.outMember(mId);
	}
}
