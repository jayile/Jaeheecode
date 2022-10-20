package com.study.jsp.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.frontcontroller.BFrontController;

public class MLogoutCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("Do logout");
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		BFrontController bf = new BFrontController();
		
		System.out.println("Logout ok");
		String json_data = "{\"code\":\"success\", \"desc\":\"로그아웃 되었습니다.\"}";
		try
		{
			bf.sendJson(response, json_data);
		} catch (ServletException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
