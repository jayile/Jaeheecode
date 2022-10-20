package com.study.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class FileDao
{
	private static FileDao instance = new FileDao();
	DataSource dataSource;
	
	
	public FileDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static FileDao getInstance() {
		return instance;
	}
	
	
	
	public int upload(String fileName, String fileRealName) {
		Connection con =null;
		PreparedStatement pstmt = null;
			System.out.println("fileName : " + fileName);
			System.out.println("fileRealName : " + fileRealName);
		try {
			con = dataSource.getConnection();
			String query = "insert into filebox values(?, ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileRealName);
			return pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}
		
}
