package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.RDao;

public class RDeleteCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("Do delete");
		
		String rId = request.getParameter("rId");
		String bId = request.getParameter("bId");
		
		System.out.println(rId);
		RDao rDao = RDao.getInstance();
		rDao.rDelete(rId, bId);
	}
}
