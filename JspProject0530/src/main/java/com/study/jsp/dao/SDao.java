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

public class SDao {
	
	private static SDao instance = new SDao();
	DataSource dataSource;
	
	int listCount = 10;		// 한 페이지당 보여줄 게시물의 갯수
	int pageCount = 10;		// 하단에 보여줄 페이지 리스트의 갯수
	
	private SDao() {
		try {
			// lookup 함수의 파라미터에는 context.xml에 설정된
			// name(jdbc/Oracle11g)과 동일해야 한다.
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SDao getInstance() {
		return instance;
	}
	
	public ArrayList<BDto> slist(int curPage, String key, String value) {
		ArrayList<BDto> sDtos = new ArrayList<BDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
//		System.out.println("현재페이지 : " + curPage);
//		System.out.println("slist key : " + key);
//		System.out.println("slist value : " + value);
//		System.out.println("slist nStart : " + nStart);
//		System.out.println("slist nEnd : " + nEnd);
		
		try {
			con = dataSource.getConnection();
			
			if(key.equals("bName")) {
				String query = "select * " +
                        "  from ( " +
                        "    select rownum num, A.* " +
                        "      from ( " +
                        "        select * " +
                        "          from board where bName like ?" +
                        "         order by bgroup desc, bstep asc ) A " +
                        "     where rownum <= ? ) B " +
                        " where B.num >= ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "%" + value + "%");
				pstmt.setInt(2, nEnd);
				pstmt.setInt(3, nStart);
				
				resultSet = pstmt.executeQuery();
				} else if(key.equals("bTitle")) {
				String query = "select * " +
                        "  from ( " +
                        "    select rownum num, A.* " +
                        "      from ( " +
                        "        select * " +
                        "          from board where bTitle like ?" +
                        "         order by bgroup desc, bstep asc ) A " +
                        "     where rownum <= ? ) B " +
                        " where B.num >= ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "%" + value + "%");
				pstmt.setInt(2, nEnd);
				pstmt.setInt(3, nStart);
					
				resultSet = pstmt.executeQuery();
			} else if(key.equals("bContent")) {
				String query = "select * " +
                        "  from ( " +
                        "    select rownum num, A.* " +
                        "      from ( " +
                        "        select * " +
                        "          from board where bContent like ?" +
                        "         order by bgroup desc, bstep asc ) A " +
                        "     where rownum <= ? ) B " +
                        " where B.num >= ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "%" + value + "%");
				pstmt.setInt(2, nEnd);
				pstmt.setInt(3, nStart);
					
				resultSet = pstmt.executeQuery();
			}
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate  = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				int bReco = resultSet.getInt("bReco");
				int rCount = resultSet.getInt("rCount");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, 
						bGroup, bStep, bIndent, bReco, rCount);
				
				sDtos.add(dto);
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
		return sDtos;
	}

	public ArrayList<BDto> nlist(int curPage) {
		ArrayList<BDto> sDtos = new ArrayList<BDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		System.out.println("현재페이지 : " + curPage);
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * " +
					   "  from ( " +
					   "    select rownum num, A. * " +
					   "      from ( " +
					   "        select * " +
					   "          from notice " +
					   "         order by bGroup desc, bStep asc ) A " +
					   "     where rownum <= ? ) B " +
					   " where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
				
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate  = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				int bReco = resultSet.getInt("bReco");
				int rCount = resultSet.getInt("rCount");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup,
						bStep, bIndent, bReco, rCount);
				
				sDtos.add(dto);
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
		return sDtos;
	}
	
	public BDto contentView(String strID) {
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				int bReco = resultSet.getInt("bReco");
				int rCount = resultSet.getInt("rCount");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate,
								bHit, bGroup, bStep, bIndent, bReco, rCount);
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
		return dto;
	}

	public BPageInfo serchPage(int curPage, String key, String value)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		System.out.println("curPage : " + curPage);
		System.out.println("key : " + key);
		System.out.println("value : " + value);
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		try {
			con = dataSource.getConnection();
			
			if(key.equals("bName")) {
				String query = "select count(*) as total from board where bName like ?";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "%" + value + "%");
				
				resultSet = pstmt.executeQuery();
			} else if(key.equals("bTitle")) {
				String query = "select count(*) as total from board where bTitle like ?";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "%" + value + "%");
				
				resultSet = pstmt.executeQuery();
			} else if(key.equals("bContent")) {
				String query = "select count(*) as total from board where bContent like ?";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "%" + value + "%");
				
				resultSet = pstmt.executeQuery();
			} else {
				String query = "select count(*) as total from board";
				pstmt = con.prepareStatement(query);
				
				resultSet = pstmt.executeQuery();
			}
			
			
			if(resultSet.next()) {
				totalCount = resultSet.getInt("total");
			}
			
			System.out.println("토탈페이지 : " + totalCount);
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
}
