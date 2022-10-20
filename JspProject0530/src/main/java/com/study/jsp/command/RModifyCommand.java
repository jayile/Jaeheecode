package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.RDao;


public class RModifyCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session = request.getSession();
		
		String bId = request.getParameter("bId");
		String rId = (String)session.getAttribute("mId");
		String rContent = request.getParameter("rContent");
		
		RDao rDao = RDao.getInstance();
		rDao.review(bId, rId, rContent);
	}
}