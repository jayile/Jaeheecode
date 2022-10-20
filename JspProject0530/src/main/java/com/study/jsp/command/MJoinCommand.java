package com.study.jsp.command;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.MemberDao;
import com.study.jsp.dto.MemberDto;


public class MJoinCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("Do join");
		
		String mId = request.getParameter("mId");
		String mPw = request.getParameter("mPw");
		String mName = request.getParameter("mName");
		String mEmail = request.getParameter("mEmail");
		String mAddress = request.getParameter("mAddress");
		
		MemberDao mDao = MemberDao.getInstance();
		MemberDto mDto = new MemberDto();
		mDto.setmId(mId);
		mDto.setmPw(mPw);
		mDto.setmName(mName);
		mDto.setmEmail(mEmail);
		mDto.setmAddress(mAddress);
		mDto.setmDate(new Timestamp(System.currentTimeMillis()));
		
		HttpSession session = request.getSession();
		session.setAttribute("mId", mDto.getmId());
		
		mDao.insertMember(mDto);
	}
}
