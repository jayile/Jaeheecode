package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.BDao;


public class BWriteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("do write");
		HttpSession session = request.getSession();
		
		String bName = (String)session.getAttribute("mId");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String boardname = request.getParameter("wHead");
		
		System.out.println("aaa : " + bName);
		System.out.println("bbb : " + bTitle);
		System.out.println("ccc : " + bContent);
		System.out.println("ddd : " + boardname);
		
		BDao dao = BDao.getInstance();
		dao.write(bName, bTitle, bContent, boardname);
	}
}
