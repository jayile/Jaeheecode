package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.MemberDao;
import com.study.jsp.dto.MemberDto;


public class MLoginCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("Do login");
		
		String mId = request.getParameter("mId");
		String mPw = request.getParameter("mPw");
		
		MemberDao mDao = MemberDao.getInstance();
		mDao.userCheck(mId, mPw);
		
		MemberDto mDto = mDao.getMember(mId);
		String mName = mDto.getmName();
		String grade = mDto.getGrade();
		
		HttpSession session = request.getSession();
		session.setAttribute("mId", mId);
		session.setAttribute("mName", mName);
		session.setAttribute("grade", grade);
		session.setAttribute("ValidMem", "yes");
	}
}
