<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% if(session.getAttribute("validMem") != null) { %>
	<jsp:forward page="main.jsp"></jsp:forward>
<% } %>

<script src="http://code.jquery.com/jquery.js"></script>

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
<title>로그인</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script>

	function form_check() {
		if($('#mId').val().length == 0) {
			alert("아이디를 입력해주세요.");
			$('#mId').focus();
			return;
		}
		
		if($('#mPw').val().length == 0) {
			alert("비밀번호를 입력해주세요.");
			$('#mPw').focus();
			return;
		}
		submit_ajax();
	}

	function submit_ajax() {
		var queryString = $('#login_frm').serialize();
		console.log(queryString);
		$.ajax({
			url: 'login.do',
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

    <div class="text-center">
      	<div class="row">
      	<div class="col-sm">
      	</div>
      		<div class="mb-3 font-weight-normal">
      		<br>
      			<h4 class="h3 mb-3 font-weight-normal">로그인</h4>
				<form id="login_frm" class="form-signin" novalidate>
					<div class="mb-2">
					    <label for="mId">User ID</label>
					    <input type="text" class="form-control" id="mId" name="mId" placeholder="아이디" 
					    	   value="<% if(session.getAttribute("mId") != null) out.println(session.getAttribute("mId"));%>" required>
					    <div class="invalid-feedback">
			           	아이디를 입력하세요.
				    	</div>
					</div>
					<div class="mb-2">
					    <label for="mPw">Password</label>
					    <input type="Password" class="form-control" id="mPw" name="mPw" placeholder="비밀번호" required>
					    <div class="invalid-feedback">
			           	비밀번호를 입력하세요.
					   	</div>
					</div>
					<hr class="mb-2">
					    <button class="btn btn-primary btn-lg btn-block" type="button" value="로그인" onclick="form_check()">로그인</button>
					    <button class="btn btn-success btn-lg btn-block" type="button" value="회원가입" onclick="javascript:window.location='join.jsp'">회원가입</button>
				</form>
				
			      	<script src="https://accounts.google.com/gsi/client" async defer></script>
			      	<div id="g_id_onload"
				         data-client_id="601923316052-t9as23ia39das137a02du3aoftiprdgq.apps.googleusercontent.com"
				         data-login_uri="https://localhost:8081/GoogleLogin/login3.html"
				         data-auto_prompt="false">
			      	</div>
			      	<div class="g_id_signin"
			        	data-type="standard"
				        data-size="large"
				        data-theme="outline"
				        data-text="sign_in_with"
				        data-shape="rectangular"
				        data-logo_alignment="left">
			    	</div>
			    	
			</div>
			<div class="col-sm">
      	</div>
    	</div>
	</div>
	
<script>
(function() {
 	'use strict';
 	window.addEventListener('load', function() {
	    var forms = document.getElementsByClassName('needs-validation');
	    var validation = Array.prototype.filter.call(forms, function(form) {
	    	form.addEventListener('button', function(event) {
	        	if (form.checkValidity() === false) {
	          	event.preventDefault();
	          	event.stopPropagation();
	        	}
	        form.classList.add('was-validated');
	      	}, false);
	    });
  	}, false);
})();
</script>

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>