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
		// 게시글 관련
		if(com.equals("/write_view.do")) {// 신규 글 쓰기
			viewPage = "write_view.jsp";
		} else if (com.equals("/write.do")) {
			command = new BWriteCommand();
			command.execute(request, response);
			
			String json_data = "{\"code\":\"ok\", \"desc\":\"글이 게시되었습니다.\"}";
			sendJson(response, json_data);
			
			return;
		} else if (com.equals("/list.do")) {// 게시판 리스트 보기
			command = new BListCommand();
			command.execute(request, response);
			
			viewPage = "list.jsp";
		} else if (com.equals("/content_view.do")) {// 자유게시판 글 보기
			command = new BContentCommand();
			command.execute(request, response);
			
			command = new RListCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if (com.equals("/notice_view.do")) {// 공지사항 글 보기
			command = new NContentCommand();
			command.execute(request, response);

			viewPage = "notice_view.jsp";
		} else if (com.equals("/modify_view.do")) {// 게시된 글 수정
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "modify_view.jsp";
		} else if (com.equals("/modify.do")) {// 게시글 수정 완료
			command = new BModifyCommand();
			command.execute(request, response);
			
			String json_data = "{\"code\":\"ok\", \"desc\":\"글이 수정되었습니다.\"}";
			sendJson(response, json_data);
			return;
		} else if (com.equals("/delete.do")) {// 게시글 삭제
			command = new BDeleteCommand();
			command.execute(request, response);
			
			viewPage = "list.do?page=" + curPage;
		} else if (com.equals("/reply_view.do")) {// 답변 달기
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "reply_view.jsp";
		} else if (com.equals("/reply.do")) {// 답변 달기 완료
			command = new BReplyCommand();
			command.execute(request, response);
			
			String json_data = "{\"code\":\"ok\", \"desc\":\"답변이 등록되었습니다.\"}";
			sendJson(response, json_data);
			
			return;
		} else if(com.equals("/BReco.do")) {// 추천
			command = new BRecommendCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if (com.equals("/review.do")) {// 댓글 달기
			command = new ReviewCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if (com.equals("/rDelete.do")) {// 댓글 삭제
			command = new RDeleteCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
		} else if(com.equals("/login.do")) {// 메인 로그인 화면			
			System.out.println("Call login");
			MemberDao mDao = MemberDao.getInstance();
			
			String mId = request.getParameter("mId");
			String mPw = request.getParameter("mPw");
			
			int checkNum = mDao.userCheck(mId, mPw);
			if(checkNum == 1) {
				command = new MLoginCommand();
				command.execute(request, response);
				
				System.out.println("Login ok");
				String json_data = "{\"code\":\"success\", \"desc\":\"로그인 되었습니다.\"}";
				sendJson(response, json_data);
			} else if (checkNum == -1) {
				System.out.println("Login fail");
				String json_data = "{\"code\":\"fail\", \"desc\":\"아이디가 존재하지 않습니다.\"}";
				sendJson(response, json_data);
			} else if (checkNum == 0) {
				System.out.println("Login fail");
				String json_data = "{\"code\":\"fail\", \"desc\":\"비밀번호가 맞지 않습니다.\"}";
				sendJson(response, json_data);
			}
			return;
		} else if(com.equals("/join.do")) {// 회원 가입
			String mId = request.getParameter("mId");
			
			MemberDao mDao = MemberDao.getInstance();
			
			if(mDao.confirmId(mId) == MemberDao.MEMBER_EXISTENT) {// 아이디 중복시
				String json_data = "{\"code\":\"fail\", \"desc\":\"아이디가 이미 존재합니다.\"}";
				sendJson(response, json_data);
			} else {
				command = new MJoinCommand();
				command.execute(request, response);
				
				System.out.println("Join ok");
				String json_data = "{\"code\":\"success\", \"desc\":\"회원가입이 되었습니다.\"}";
				sendJson(response, json_data);
				
				viewPage = "login.jsp";
			}
			return;
		} else if (com.equals("/outMember.do")) {// 회원 탈퇴
			command = new MDeleteCommand();
			command.execute(request, response);
			
			System.out.println("Out ok");
			String json_data = "{\"code\":\"success\", \"desc\":\"탈퇴되었습니다.\"}";
			sendJson(response, json_data);
			
			viewPage = "main.jsp";
			return;
		} else if(com.equals("/mModify.do")) {// 회원 정보 수정
			command = new MUpdateCommand();
			command.execute(request, response);
			
			System.out.println("mModify ok");
			String json_data = "{\"code\":\"success\", \"desc\":\"회원정보 수정이 완료되었습니다.\"}";
			sendJson(response, json_data);
			
			return;
		} else if(com.equals("/logout.do")) {// 로그아웃시
			command = new MLogoutCommand();
			command.execute(request, response);
			
			System.out.println("Logout ok");
			String json_data = "{\"code\":\"success\", \"desc\":\"로그아웃 되었습니다.\"}";
			sendJson(response, json_data);
			
			viewPage = "login.jsp";
			return;
		} else if(com.equals("/manager.do")) {// 관리 메뉴
			viewPage = "manager.jsp";
		} else if(com.equals("/memberlist.do")) {// 회원 목록
			command = new MListCommand();
			command.execute(request, response);
			
			viewPage = "memberlist.jsp";
		} else if(com.equals("/member_view.do")) {// 회원 정보 조회
			command = new MViewCommand();
			command.execute(request, response);
			
			viewPage = "member_view.jsp";
		} else if (com.equals("/mDelete.do")) {// 회원 강제 탈퇴
			command = new MOutCommand();
			command.execute(request, response);
			
			viewPage = "memberlist.jsp";
		} else if (com.equals("/noticelist.do")) {// 공지사항
			command = new NoticelistCommand();
			command.execute(request, response);
			
			viewPage = "noticelist.jsp";
		} else if (com.equals("/memberlist2.do")) {// 회원 검색
			command = new SearchMCommand();
			command.execute(request, response);
			
			viewPage = "memberlist2.jsp";
		} else if (com.equals("/sList.do")) {// 게시판 검색
			command = new SearchCommand();
			command.execute(request, response);
			
			//return;
			viewPage = "slist.jsp";
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}