package com.study.jsp.frontcontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.command.BCommand;
import com.study.jsp.command.BContentCommand;
import com.study.jsp.command.BDeleteCommand;
import com.study.jsp.command.BListCommand;
import com.study.jsp.command.BModifyCommand;
import com.study.jsp.command.BRecommendCommand;
import com.study.jsp.command.BReplyCommand;
import com.study.jsp.command.BReplyViewCommand;
import com.study.jsp.command.BWriteCommand;
import com.study.jsp.command.MDeleteCommand;
import com.study.jsp.command.MJoinCommand;
import com.study.jsp.command.MListCommand;
import com.study.jsp.command.MLoginCommand;
import com.study.jsp.command.MLogoutCommand;
import com.study.jsp.command.MOutCommand;
import com.study.jsp.command.MUpdateCommand;
import com.study.jsp.command.MViewCommand;
import com.study.jsp.command.NContentCommand;
import com.study.jsp.command.NoticelistCommand;
import com.study.jsp.command.RDeleteCommand;
import com.study.jsp.command.RListCommand;
import com.study.jsp.command.ReviewCommand;
import com.study.jsp.command.SearchCommand;
import com.study.jsp.command.SearchMCommand;
import com.study.jsp.dao.MemberDao;

@WebServlet("*.do")
public class BFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BFrontController() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doGet");
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doPost");
		actionDo(request, response);
	}
	
	public void sendJson(HttpServletResponse response, String json_data)
			throws ServletException, IOException
	{
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println(json_data);
		writer.close();
	}
	
	public class Download extends HttpServlet
	{
		private static final long serialVersionUID = 1L;

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
		{
			doPost(request, response);
		}
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("actionDo");
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		BCommand command = null;
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		
		HttpSession session = null;
		session = request.getSession();
		
		int curPage = 1;
		if(session.getAttribute("cpage") != null) {
			curPage = (int)session.getAttribute("cpage");
		}
		// ????????? ??????
		if(com.equals("/write_view.do")) {// ?????? ??? ??????
			viewPage = "write_view.jsp";
		} else if (com.equals("/write.do")) {
			command = new BWriteCommand();
			command.execute(request, response);
			
			String json_data = "{\"code\":\"ok\", \"desc\":\"?????? ?????????????????????.\"}";
			sendJson(response, json_data);
			
			return;
		} else if (com.equals("/list.do")) {// ????????? ????????? ??????
			command = new BListCommand();
			command.execute(request, response);
			
			viewPage = "list.jsp";
		} else if (com.equals("/content_view.do")) {// ??????????????? ??? ??????
			command = new BContentCommand();
			command.execute(request, response);
			
			command = new RListCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if (com.equals("/notice_view.do")) {// ???????????? ??? ??????
			command = new NContentCommand();
			command.execute(request, response);

			viewPage = "notice_view.jsp";
		} else if (com.equals("/modify_view.do")) {// ????????? ??? ??????
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "modify_view.jsp";
		} else if (com.equals("/modify.do")) {// ????????? ?????? ??????
			command = new BModifyCommand();
			command.execute(request, response);
			
			String json_data = "{\"code\":\"ok\", \"desc\":\"?????? ?????????????????????.\"}";
			sendJson(response, json_data);
			return;
		} else if (com.equals("/delete.do")) {// ????????? ??????
			command = new BDeleteCommand();
			command.execute(request, response);
			
			viewPage = "list.do?page=" + curPage;
		} else if (com.equals("/reply_view.do")) {// ?????? ??????
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "reply_view.jsp";
		} else if (com.equals("/reply.do")) {// ?????? ?????? ??????
			command = new BReplyCommand();
			command.execute(request, response);
			
			String json_data = "{\"code\":\"ok\", \"desc\":\"????????? ?????????????????????.\"}";
			sendJson(response, json_data);
			
			return;
		} else if(com.equals("/BReco.do")) {// ??????
			command = new BRecommendCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if (com.equals("/review.do")) {// ?????? ??????
			command = new ReviewCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if (com.equals("/rDelete.do")) {// ?????? ??????
			command = new RDeleteCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if(com.equals("/login.do")) {// ?????? ????????? ??????			
			System.out.println("Call login");
			MemberDao mDao = MemberDao.getInstance();
			
			String mId = request.getParameter("mId");
			String mPw = request.getParameter("mPw");
			
			int checkNum = mDao.userCheck(mId, mPw);
			if(checkNum == 1) {
				command = new MLoginCommand();
				command.execute(request, response);
				
				System.out.println("Login ok");
				String json_data = "{\"code\":\"success\", \"desc\":\"????????? ???????????????.\"}";
				sendJson(response, json_data);
			} else if (checkNum == -1) {
				System.out.println("Login fail");
				String json_data = "{\"code\":\"fail\", \"desc\":\"???????????? ???????????? ????????????.\"}";
				sendJson(response, json_data);
			} else if (checkNum == 0) {
				System.out.println("Login fail");
				String json_data = "{\"code\":\"fail\", \"desc\":\"??????????????? ?????? ????????????.\"}";
				sendJson(response, json_data);
			}
			return;
		} else if(com.equals("/join.do")) {// ?????? ??????
			String mId = request.getParameter("mId");
			
			MemberDao mDao = MemberDao.getInstance();
			
			if(mDao.confirmId(mId) == MemberDao.MEMBER_EXISTENT) {// ????????? ?????????
				String json_data = "{\"code\":\"fail\", \"desc\":\"???????????? ?????? ???????????????.\"}";
				sendJson(response, json_data);
			} else {
				command = new MJoinCommand();
				command.execute(request, response);
				
				System.out.println("Join ok");
				String json_data = "{\"code\":\"success\", \"desc\":\"??????????????? ???????????????.\"}";
				sendJson(response, json_data);
				
				viewPage = "login.jsp";
			}
			return;
		} else if (com.equals("/outMember.do")) {// ?????? ??????
			command = new MDeleteCommand();
			command.execute(request, response);
			
			System.out.println("Out ok");
			String json_data = "{\"code\":\"success\", \"desc\":\"?????????????????????.\"}";
			sendJson(response, json_data);
			
			viewPage = "main.jsp";
			return;
		} else if(com.equals("/mModify.do")) {// ?????? ?????? ??????
			command = new MUpdateCommand();
			command.execute(request, response);
			
			System.out.println("mModify ok");
			String json_data = "{\"code\":\"success\", \"desc\":\"???????????? ????????? ?????????????????????.\"}";
			sendJson(response, json_data);
			
			return;
		} else if(com.equals("/logout.do")) {// ???????????????
			command = new MLogoutCommand();
			command.execute(request, response);
			
			System.out.println("Logout ok");
			String json_data = "{\"code\":\"success\", \"desc\":\"???????????? ???????????????.\"}";
			sendJson(response, json_data);
			
			viewPage = "login.jsp";
			return;
		} else if(com.equals("/manager.do")) {// ?????? ??????
			viewPage = "manager.jsp";
		} else if(com.equals("/memberlist.do")) {// ?????? ??????
			command = new MListCommand();
			command.execute(request, response);
			
			viewPage = "memberlist.jsp";
		} else if(com.equals("/member_view.do")) {// ?????? ?????? ??????
			command = new MViewCommand();
			command.execute(request, response);
			
			viewPage = "member_view.jsp";
		} else if (com.equals("/mDelete.do")) {// ?????? ?????? ??????
			command = new MOutCommand();
			command.execute(request, response);
			
			viewPage = "memberlist.jsp";
		} else if (com.equals("/noticelist.do")) {// ????????????
			command = new NoticelistCommand();
			command.execute(request, response);
			
			viewPage = "noticelist.jsp";
		} else if (com.equals("/memberlist2.do")) {// ?????? ??????
			command = new SearchMCommand();
			command.execute(request, response);
			
			viewPage = "memberlist2.jsp";
		} else if (com.equals("/sList.do")) {// ????????? ??????
			command = new SearchCommand();
			command.execute(request, response);
			
			//return;
			viewPage = "slist.jsp";
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}