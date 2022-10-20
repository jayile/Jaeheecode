package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.BDao;
import com.study.jsp.dto.BDto;

public class NContentCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{		
		String bId = request.getParameter("bId");
		
		BDao dao = BDao.getInstance();
		BDto dto = dao.nContentView(bId);
		
		request.setAttribute("notice_view", dto);
	}
}
