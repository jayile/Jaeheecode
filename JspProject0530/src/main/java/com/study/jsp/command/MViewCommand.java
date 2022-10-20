package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.MemberDao;
import com.study.jsp.dto.MemberDto;

public class MViewCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{		
		String mId = request.getParameter("mId");
		
		MemberDao mDao = MemberDao.getInstance();
		MemberDto mDto = mDao.memberView(mId);
		
		request.setAttribute("member_view", mDto);
	}
}