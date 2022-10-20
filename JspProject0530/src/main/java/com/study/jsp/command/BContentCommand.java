package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.BDao;
import com.study.jsp.dto.BDto;

public class BContentCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{		
		String bId = request.getParameter("bId");
		
		BDao dao = BDao.getInstance();
		BDto dto = dao.contentView(bId);
		
		request.setAttribute("content_view", dto);
	}
}
