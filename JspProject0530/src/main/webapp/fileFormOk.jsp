<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getRealPath("fileFolder"); //파일 저장 경로

	out.println(path + "<br>");
	
	int size = 1024 * 1024 *10; // 10M 파일 크기 제한
	String file = "";
	String orifile = "";
	
	try {
		MultipartRequest multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy()); // 파일명 바꿔주는
		String name = multi.getParameter("bName");
		out.println(name + "<br>");
		
		//파일을 여러개 업로드할 경우 타입이 file인 파라미터 이름들을 Enumeration 타입으로 반환
		//Enumeration 타입은 Iterator 타입과 같은 컬렉션 인터페이스
		Enumeration files = multi.getFileNames();
		String str = (String)files.nextElement();
		
		// 전송받은 데이터가 파일일 경우 getFilesystemName()으로 파일 이름을 받아올 수 있다.
		file = multi.getFilesystemName(str);
		orifile = multi.getOriginalFileName(str);				
	} catch(Exception e) {
		e.printStackTrace();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP/Servlet 22-2</title>
</head>
<body>
	file upload success!
</body>
</html>