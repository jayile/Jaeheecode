package com.study.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.study.jsp.BPageInfo;
import com.study.jsp.dto.BDto;
import com.study.jsp.dto.MemberDto;

public class MemberDao
{
	public static final int MEMBER_NONEXISTEMT = 0;
	public static final int MEMBER_EXISTENT = 1;
	public static final int MEMBER_JOIN_FAIL = 0;
	public static final int MEMBER_JOIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD = 0;
	public static final int MEMBER_LOGIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_IS_NOT = -1;
	
	private static MemberDao instance = new MemberDao();
	DataSource dataSource;
	
	int listCount = 5;		// 한 페이지당 보여줄 게시물의 갯수
	int pageCount = 5;		// 하단에 보여줄 페이지 리스트의 갯수
	
	private MemberDao() {
		try {
			// lookup 함수의 파라미터에는 context.xml에 설정된
			// name(jdbc/Oracle11g)과 동일해야 한다.
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MemberDao getInstance() {
		return instance;
	}
	
	public int insertMember(MemberDto mDto) {
		int ri = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "insert into member values (?, ?, ?, ?, ?, ?, null, 0)";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mDto.getmId());
			pstmt.setString(2, mDto.getmPw());
			pstmt.setString(3, mDto.getmName());
			pstmt.setString(4, mDto.getmEmail());
			pstmt.setString(5, mDto.getmAddress());
			pstmt.setTimestamp(6, mDto.getmDate());
			
			pstmt.executeUpdate();
			
			ri = MemberDao.MEMBER_JOIN_SUCCESS;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public int confirmId(String mId) {
		int ri = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet set = null;
		
		String query = "select mId from member where mId = ?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mId);
			set = pstmt.executeQuery();
			if(set.next()) {
				ri = MemberDao.MEMBER_EXISTENT;
			} else {
				ri = MemberDao.MEMBER_NONEXISTEMT;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}

	public int userCheck(String mId, String mPw) {
		int ri = 0;
		String dbPw;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet set = null;
		
		String query = "select mPw from member where mId = ?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mId);
			set = pstmt.executeQuery();
			
			if(set.next()) {
				dbPw = set.getString("mPw");
				if(dbPw.equals(mPw)) {
					ri = MemberDao.MEMBER_LOGIN_SUCCESS;		// 로그인 Ok
				} else {
					ri = MemberDao.MEMBER_LOGIN_PW_NO_GOOD; // 비밀번호 X
				}
			} else {
				ri = MemberDao.MEMBER_LOGIN_IS_NOT;			// 아이디 X
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public MemberDto getMember(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet set = null;
		
		MemberDto dto = null;
		
		String query = "select * from member where mId = ?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			set = pstmt.executeQuery();
			
			if(set.next()) {
				dto = new MemberDto();
				dto.setmId(set.getString("mId"));
				dto.setmPw(set.getString("mPw"));
				dto.setmName(set.getString("mName"));
				dto.setmEmail(set.getString("mEmail"));
				dto.setmDate(set.getTimestamp("mDate"));
				dto.setmAddress(set.getString("mAddress"));
				dto.setGrade(set.getString("grade"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}
	
	public int updateMember(MemberDto mDto) {
		int ri = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "update member set mPw=?, mEmail=?, mAddress=? where mId=?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mDto.getmPw());
			pstmt.setString(2, mDto.getmEmail());
			pstmt.setString(3, mDto.getmAddress());
			pstmt.setString(4, mDto.getmId());
			
			ri = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public int outMember(String mId) {
		int ri = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "delete from member where mId=?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mId);
			
			ri = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public ArrayList<MemberDto> mList(int curPage) {
		ArrayList<MemberDto> mDtos = new ArrayList<MemberDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from "
					+ "(select rownum num, A. * from (select * from member order by mDate desc )A "
					+ " where rownum <= ? ) B where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				String mId = resultSet.getString("mId");
				String mPw = resultSet.getString("mPw");
				String mName = resultSet.getString("mName");
				String mEmail = resultSet.getString("mEmail");
				String mAddress = resultSet.getString("mAddress");
				Timestamp mDate  = resultSet.getTimestamp("mDate");
				Timestamp mBlack = resultSet.getTimestamp("mBlack");
				String grade = resultSet.getString("grade");
				MemberDto mDto = new MemberDto(mId, mPw, mName, mEmail, mAddress, mDate, mBlack, grade);
				mDtos.add(mDto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return mDtos;
	}
	
	public ArrayList<MemberDto> serchmList(int curPage, String strId) {
		ArrayList<MemberDto> mDtos = new ArrayList<MemberDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from "
					+ "(select rownum num, A. * from (select * from member where mId like ? order by mDate desc )A "
					+ " where rownum <= ? ) B where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + strId + "%");
			pstmt.setInt(2, nEnd);
			pstmt.setInt(3, nStart);

			resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				String mId = resultSet.getString("mId");
				String mPw = resultSet.getString("mPw");
				String mName = resultSet.getString("mName");
				String mEmail = resultSet.getString("mEmail");
				String mAddress = resultSet.getString("mAddress");
				Timestamp mDate  = resultSet.getTimestamp("mDate");
				Timestamp mBlack = resultSet.getTimestamp("mBlack");
				String grade = resultSet.getString("grade");
				MemberDto mDto = new MemberDto(mId, mPw, mName, mEmail, mAddress, mDate, mBlack, grade);
				mDtos.add(mDto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return mDtos;
	}
	
	public BPageInfo memberPage(int curPage)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from member";
			pstmt = con.prepareStatement(query);
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {
				totalCount = resultSet.getInt("total");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		// 총 페이지 수
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0)
			totalPage++;
		
		// 현재 페이지
		int myCurPage = curPage;
		if(myCurPage > totalPage)
		   myCurPage = totalPage;
		if(myCurPage < 1)
		   myCurPage = 1;
		
		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		// 끝 페이지 
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
		   endPage = totalPage;
		
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	public BPageInfo mSerchPage(int curPage, String strId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from member where mId like ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + strId + "%");
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {
				totalCount = resultSet.getInt("total");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		// 총 페이지 수
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0)
			totalPage++;
		
		// 현재 페이지
		int myCurPage = curPage;
		if(myCurPage > totalPage)
		   myCurPage = totalPage;
		if(myCurPage < 1)
		   myCurPage = 1;
		
		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		// 끝 페이지 
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
		   endPage = totalPage;
		
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	public MemberDto memberView(String strID) {
		MemberDto mDto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		System.out.println(strID);
		try {
			con = dataSource.getConnection();
			
			String query = "select * from member where mId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, strID);
			
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {
				String mId = resultSet.getString("mId");
				String mPw = resultSet.getString("mPw");
				String mName = resultSet.getString("mName");
				String mEmail = resultSet.getString("mEmail");
				String mAddress = resultSet.getString("mAddress");
				Timestamp mDate  = resultSet.getTimestamp("mDate");
				Timestamp mBlack = resultSet.getTimestamp("mBlack");
				String grade = resultSet.getString("grade");
				
				mDto = new MemberDto(mId, mPw, mName, mEmail, mAddress, mDate, mBlack, grade);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return mDto;
	}
	
	private Connection getConnection() {
		Context context = null;
		DataSource dataSource = null;
		Connection con = null;
		try {
			// lookup 함수의 파라미터는 context.xml에 설정된
			// name(jdbc/Oracle11g)와 동일해야 한다.
			context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			con = dataSource.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
