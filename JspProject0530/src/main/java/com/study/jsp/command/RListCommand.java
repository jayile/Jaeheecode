package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.RDao;
import com.study.jsp.dto.RDto;

public class RListCommand implements BCommand {
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String bId = request.getParameter("bId");
		
		RDao rDao = RDao.getInstance();
		ArrayList<RDto> rDtos = rDao.rList(bId);
		request.setAttribute("list", rDtos);
	}
}
