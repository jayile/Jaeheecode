<%@ page import="com.study.jsp.dto.MemberDto" %>
<%@ page import="com.study.jsp.dao.MemberDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String mName = (String)session.getAttribute("mName");
	String mId = (String)session.getAttribute("mId");
	String vm = (String)session.getAttribute("ValidMem");
	String grade = (String)session.getAttribute("grade");
	
	MemberDao mDao = MemberDao.getInstance();
	MemberDto mDto = mDao.getMember(mId);

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
<meta charset="UTF-8">
<title>정보 수정</title>

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
    
<script src="http://code.jquery.com/jquery.js"></script>
<script>
	
	function form_check() {
		if($('#mPw').val().length == 0) {
			alert("비밀번호를 입력하세요.");
			$('#mPw').focus();
			return;
		}
		
		if($('#mPw').val() != $('#pw_check').val()) {
			alert("비밀번호가 일치하지 않습니다.");
			$('#mPw').focus();
			return;
		}
		
		if($('#mEmail').val().length == 0) {
			alert("메일은 필수사항입니다.");
			$('#mEmail').focus();
			return;
		}
		submit_ajax();
	}
	
	function submit_ajax() {
		var queryString = $('#mModify_frm').serialize();
		console.log(queryString);
		$.ajax({
			url: 'mModify.do',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "success") {
					alert(result.desc);
					window.location.replace("main.jsp");
				} else {
					alert(result.desc);
				}
			}
		});
	}
	
</script>
</head>
<body>
	<form id="mModify_frm">
		아이디 : <%= mDto.getmId() %><br>
        비밀번호 : <input type="password" id="mPw" name="mPw" size="20"><br>
        비밀번호 확인 : <input type="password" id="pw_check" name="pw_check" size="20"><br>
        이름 : <%= mDto.getmName() %><br>
        메일 : <input type="text" id="mEmail" name="mEmail" size="20" value="<%= mDto.getmEmail() %>"><br>
        주소 : <input type="text" id="mAddress" name="mAddress" size="50" value="<%= mDto.getmAddress() %>"><br><p>
        <input type="button" value="수정" onclick="form_check()">&nbsp;&nbsp;
        <input type="reset" value="취소" onclick="javascript:window.location='main.jsp'">
	</form>
</body>
</html>