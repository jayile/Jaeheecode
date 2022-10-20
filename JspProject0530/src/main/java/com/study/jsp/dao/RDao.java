package com.study.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.study.jsp.dto.RDto;


public class RDao {
	
	private static RDao instance = new RDao();
	DataSource dataSource;
	
	private RDao() {
		try {
			// lookup 함수의 파라미터에는 context.xml에 설정된
			// name(jdbc/Oracle11g)과 동일해야 한다.
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static RDao getInstance() {
		return instance;
	}
    
    public void review(String bId, String rName, String rContent) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		try {
			con = dataSource.getConnection();
			
			System.out.println(bId);
			
			String query1 = "insert into review (rId, bId, rName, rContent) values (review_seq.nextval, ?, ?, ?)";
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, bId);// 글 번호
			pstmt1.setString(2, rName);// 댓글 작성자 아이디
			pstmt1.setString(3, rContent);// 댓글 내용
			
			int rn1 = pstmt1.executeUpdate();
			
			String query2 = "update board set bHit = bHit - 1 where bId = ?";
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			
			int rn2 = pstmt2.executeUpdate();
			
			String query3 = "update board set rCount = rCount + 1 where bId = ?";
			pstmt3 = con.prepareStatement(query3);
			pstmt3.setString(1, bId);
			
			int rn3 = pstmt3.executeUpdate();
			
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

    public ArrayList<RDto> rList(String bId) {
		ArrayList<RDto> rDtos = new ArrayList<RDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from review where bId = ? order by rId asc";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				String rId = resultSet.getString("rId");
				String rName = resultSet.getString("rName");
				Timestamp rDate  = resultSet.getTimestamp("rDate");
				String rContent = resultSet.getString("rContent");
				RDto rDto = new RDto(rId, bId, rName, rContent, rDate);
				
				rDtos.add(rDto);
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
		return rDtos;
	}
    
    public void rDelete(String rId, String bId) {
    	Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "delete from review where rId = ?";
			pstmt1 = con.prepareStatement(query);
			pstmt1.setInt(1, Integer.parseInt(rId));
			
			int rn1 = pstmt1.executeUpdate();
			
			String query2 = "update board set bHit = bHit - 1 where bId = ?";
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			
			int rn2 = pstmt2.executeUpdate();
			
			String query3 = "update board set rCount = rCount - 1 where bId = ?";
			pstmt3 = con.prepareStatement(query3);
			pstmt3.setString(1, bId);
			
			int rn3 = pstmt3.executeUpdate();
			
			System.out.println("delete Ok");
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

    public void rModify(String rId, String rContent, String bId) {
    	Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		System.out.println("댓글수정 필요 정보" + rId + rContent + bId);
		
		try {
			con = dataSource.getConnection();
			
			String query = "update review set rContent = ?, where rId = ?";
			pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, rContent);
			pstmt1.setInt(2, Integer.parseInt(rId));
			
			int rn1 = pstmt1.executeUpdate();
			
			String query2 = "update board set bHit = bHit - 1 where bId = ?";
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			
			int rn2 = pstmt2.executeUpdate();
			
			System.out.println("Review modify Ok");
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
}
