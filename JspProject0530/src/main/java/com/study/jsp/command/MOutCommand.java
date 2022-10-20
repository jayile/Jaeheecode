package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.MemberDao;

public class MOutCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("Do out");
		
		String mId = request.getParameter("mId");
		//System.out.println("aaa : " + mId);
		
		MemberDao mDao = MemberDao.getInstance();
		mDao.outMember(mId);
	}
}
