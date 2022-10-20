package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.BDao;
import com.study.jsp.dto.BDto;


public class BRecommendCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("Do recommend");
		
		String bId = request.getParameter("bId");
		
		BDao dao = BDao.getInstance();
		BDto dto = new BDto();
		dao.BRecommend(bId);
		
		request.setAttribute("content_view", dto);
	}
}
