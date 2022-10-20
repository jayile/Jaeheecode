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

public class BDao {
	
	private static BDao instance = new BDao();
	DataSource dataSource;
	
	int listCount = 10;		// 한 페이지당 보여줄 게시물의 갯수
	int pageCount = 10;		// 하단에 보여줄 페이지 리스트의 갯수
	
	private BDao() {
		try {
			// lookup 함수의 파라미터에는 context.xml에 설정된
			// name(jdbc/Oracle11g)과 동일해야 한다.
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BDao getInstance() {
		return instance;
	}
	
	public void write(String bName, String bTitle, String bContent, String boardname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			if(boardname.equals("free")) {
				
				String query = "insert into board " +
							   " (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent, bReco, rCount) " +
							   " values " +
							   " (board_seq.nextval, ?, ?, ?, 0, board_seq.currval, 0, 0, 0, 0)";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				
				int rn = pstmt.executeUpdate();
			} else if (boardname.equals("notice")) {
				String query = "insert into notice " +
						   " (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent, bReco, rCount) " +
						   " values " +
							   " (notice_seq.nextval, ?, ?, ?, 0, notice_seq.currval, 0, 0, 0, 0)";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				
				int rn = pstmt.executeUpdate();
			}
			
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
	}
	
	public ArrayList<BDto> list(int curPage) {
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * " +
						   "  from ( " +
						   "    select rownum num, A. * " +
						   "      from ( " +
						   "        select * " +
						   "          from board " +
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
				dtos.add(dto);
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
		return dtos;
	}
	
	public ArrayList<BDto> slist(int curPage, String key, String value) {
		ArrayList<BDto> sDtos = new ArrayList<BDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		System.out.println("현재페이지 : " + curPage);
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {
			con = dataSource.getConnection();
			
			if(key.equals("bName")) {
				String query = "select * " +
							   "  from ( " +
							   "    select rownum num, A. * " +
							   "      from ( " +
							   "        select * " +
							   "          from board " +
							   "          where bName like ? order by bGroup desc, bStep asc ) A " +
							   "     where rownum <= ? ) B " +
							   " where B.num >= ?";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "%" + value + "%");
				pstmt.setInt(2, nEnd);
				pstmt.setInt(3, nStart);
				
				resultSet = pstmt.executeQuery();
			} else if(key.equals("bTitle")) {
				String query = "select * " +
						   "  from ( " +
						   "    select rownum num, A. * " +
						   "      from ( " +
						   "        select * " +
						   "          from board " +
						   "         where bTitle like ? order by bGroup desc, bStep asc ) A " +
						   "     where rownum <= ? ) B " +
						   " where B.num >= ?";
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
		upHit(strID);
		
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
	
	public BDto nContentView(String strID) {
		upHit(strID);
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from notice where bId = ?";
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
	
	public void modify(String bId, String bName, String bTitle, String bContent) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			con = dataSource.getConnection();
			
			String query1 = "update board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, bName);
			pstmt1.setString(2, bTitle);
			pstmt1.setString(3, bContent);
			pstmt1.setInt(4, Integer.parseInt(bId));
			
			int r1n = pstmt1.executeUpdate();
			
			String query2 = "update board set bHit = bHit - 2 where bId = ?";
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			
			int rn = pstmt2.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt1 != null) pstmt1.close();
				if(pstmt2 != null) pstmt2.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void upHit(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "update board set bHit = bHit + 1 where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			int rn = pstmt.executeUpdate();
			
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
	}
	
	public void delete(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "delete from board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			
			int rn = pstmt.executeUpdate();
			
			System.out.println("delete Ok");
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
	}
	
	public BDto reply_view(String str) {
		BDto dto = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(str));
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
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit,
							   bGroup, bStep, bIndent, bReco, rCount);
			}
			System.out.println("Reply Ok");
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
		return dto;
	}
	
	public void reply(String bId, String mId, String bTitle, String bContent,
					  String bGroup, String bStep, String bIndent)
	{
		replyShape(bGroup, bStep);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "insert into board " +
						   " (bId, bName, bTitle, bContent, bGroup, bStep, bIndent, bReco) " +
						   " values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?, 0)";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, mId);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
			pstmt.setInt(6, Integer.parseInt(bIndent) + 1);
			
			int rn = pstmt.executeUpdate();
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
	}
	
	private void replyShape(String strGroup, String strStep) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "update board " +
						   "   set bStep = bStep + 1 " +
						   " where bGroup = ? and bStep > ?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));
			
			int rn = pstmt.executeUpdate();
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
	}
	
	public BPageInfo articlePage(int curPage)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from board";
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
	
	public BPageInfo serchPage(int curPage, String key, String value)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
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
			}
			
			
			if(resultSet.next()) {
				totalCount = resultSet.getInt("total");
			}
			System.out.println("토탈 : " + totalCount);
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
	
	public void BRecommend(String bId) {		
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			con = dataSource.getConnection();
			
			String query1 = "update board set bReco = bReco + 1 where bId = ?";
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, bId);
			
			int rn1 = pstmt1.executeUpdate();
			
			String query2 = "update board set bHit = bHit - 1 where bId = ?";
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			
			int rn2 = pstmt2.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt1 != null) pstmt1.close();
				if(pstmt2 != null) pstmt2.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void downHit(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "update board set bHit = bHit - 1 where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			int rn = pstmt.executeUpdate();
			
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
	}

	
//	public void myContent(String mId) {
//		ArrayList<BDto> bDtos = new ArrayList<BDto>();
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		
//		try {
//			con = dataSource.getConnection();
//			
//			String query = "select * from board where bName = ? order by bId asc";
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, Integer.parseInt(mId));
//			resultSet = pstmt.executeQuery();
//			
//			while(resultSet.next()) {
//				String rId = resultSet.getString("rId");
//				String rName = resultSet.getString("rName");
//				Timestamp rDate  = resultSet.getTimestamp("rDate");
//				String rContent = resultSet.getString("rContent");
//				BDto bDto = new BDto(rId, bId, rName, rContent, rDate);
//				
//				bDtos.add(bDto);
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//				if(con != null) con.close();
//			} catch(Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		return dtos;
//	}
}
