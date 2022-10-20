<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	if(session.getAttribute("ValidMem") == null) {
		response.sendRedirect("login.jsp");
	}
	
	String mName = (String)session.getAttribute("mName");
	String mId = (String)session.getAttribute("mId");
	String vm = (String)session.getAttribute("ValidMem");
	String grade = (String)session.getAttribute("grade");
	
%>

<!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" 
    integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>

<!DOCTYPE html>
<html>
<head>
<title>메인</title>
<script src="http://code.jquery.com/jquery.js"></script>

<script>
	function mDelete_check() {
        if (!confirm("회원을 탈퇴하시겠습니까?")) {
            alert("회원 탈퇴를 취소했습니다.");
        } else {
        	md_ajax();
        }
	}
	
	function md_ajax() {
		var queryString = $('#main_frm').serialize();
		console.log(queryString);
		$.ajax({
			url: 'outMember.do',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "success") {
					alert(result.desc);
					window.location.replace("login.jsp");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>

<script>
	function submit_ajax() {
		var queryString = $('#main_frm').serialize();
		console.log(queryString);
		$.ajax({
			url: 'logout.do',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "success") {
					alert(result.desc);
					window.location.replace("login.jsp");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>

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

        </div>
    </nav>
    
<meta charset="UTF-8">

</head>
<body>

	<div class="container">
  <div class="row">
    <div class="col">

    </div>
    <div class="col">

    </div>
  </div>
  <div class="row">
    <div class="col">

    </div>
    <div class="col text-center">
     			<br>
		    	<h4> <%= mName %>님 환영합니다.</h4> <br>
		    	<form id="main_frm">
					<button type="button" class="btn btn-sm btn-primary" onclick="javascript:window.location='write_view.jsp'">글쓰기</button>
					<!-- <button type="button" class="btn btn-sm btn-success" onclick="javascript:window.location='mycontent_view.jsp'">내가 쓴 글</button> -->
					<button type="button" class="btn btn-sm btn-light" onclick="javascript:window.location='mmodify.jsp'">정보수정</button>
					<button type="button" class="btn btn-sm btn-danger" onclick="submit_ajax()">로그아웃</button>
					<button type="button" class="btn btn-sm btn-danger" onclick="mDelete_check()">회원탈퇴</button>
				</form>
    </div>
    <div class="col">

    </div>
  </div>
</div>
	
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
</html>