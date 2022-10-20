<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% if(session.getAttribute("validMem") != null) { %>
	<jsp:forward page="login.jsp"></jsp:forward>
<% } 
		String mName = (String)session.getAttribute("mName");
		String mId = (String)session.getAttribute("mId");
		String vm = (String)session.getAttribute("ValidMem");
		String grade = (String)session.getAttribute("grade");
%>

<!DOCTYPE html>
<html>
<head>


<!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" 
    integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    
<meta charset="UTF-8">
<title>자유게시판</title>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <a class="navbar-brand" href="main.jsp">Hello, world!</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
	        <ul class="navbar-nav mr-auto">
	          
	            <li class="nav-item active">
	              <a class="nav-link" href='main.jsp'> 메인 </a>
	            </li>
	            
	            <li class="nav-item">
	              <a class="nav-link" href="noticelist.do">공지사항</a>
	            </li>
	            
	            <li class="nav-item">
	              <a class="nav-link" href='list.do'> 자유게시판 </a>
	            </li>
	            
	            <li class="nav-item">
	              <a class="nav-link" href='list.do'> 자료실 </a>
	            </li>
            	
				<c:if test="${grade == 1}">
		            <li class="nav-item">
		              <a class="nav-link" href='manager.do'> 관리 </a>
		            </li>
            	</c:if>
            	
         	</ul>
         	<form class="form-inline my-2 my-lg-0">
		      	
		    </form>
        </div>
    </nav>
    
</head>
<body>
<script src="http://code.jquery.com/jquery.js"></script>

<script>
// 회원 강제 탈퇴
	function mdelete() {
		var userid = $("#checkBtn").val();
		if (!confirm("이 회원을 탈퇴 처리하시겠습니까?")) {
        } else {
            $.ajax({
    			url: 'mDelete.do',
    			type: 'POST',
    			data: { mId : userid },
    			dataType: 'text',
    			success: function() {
    				window.location.replace("memberlist.do");
    			}
    		});
			alert("회원이 탈퇴 처리되었습니다.");
       }
	}
</script>

<div class="container text-center">
	<table class="table table-hover">
	  <thead>
	    <tr>
	      	<td scope="col" >ID</td>
	      	<td scope="col">닉네임</td>
	      	<td scope="col">이메일</td>
	     	<td scope="col">가입일</td>
	      	<td scope="col">정지기간</td>
			<td scope="col">등급</td>
	    </tr>
	  </thead>
	  <tbody>
			<tr id="member">
				<td scope="row">${member_view.mId}</td>
				<td>${member_view.mName}</td>
				<td>${member_view.mEmail}</td>
				<td>${member_view.mDate}</td>
				<td>${member_view.mBlack}</td>
				<td>${member_view.grade}</td>
				<td>
					<button type="button" class="btn btn-primary btn-sm" onclick="javascript:window.location='list.do?page=<%= session.getAttribute("cpage") %>'">목록보기</button>
					<button id="checkBtn" value="${member_view.mId}" type="button" class="btn btn-danger btn-sm col-auto" onclick="mdelete()">강제탈퇴</button>
				</td>
			</tr>
		</tbody>
	</table>
	
</div>

	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" 
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
</html>