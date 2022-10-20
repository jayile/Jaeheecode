<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>


<!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" 
    integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    


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
	function form_check() {
		if($('#mId').val().length == 0) {
			alert("아이디는 필수사항입니다.");
			$('#mId').focus();
			return;
		}
		
		if($('#mId').val().length < 0) {
			alert("아이디는 4글자 이상이어야 합니다.");
			$('#mId').focus();
			return;
		}
		
		if($('#mPw').val().length == 0) {
			alert("비밀번호는 필수사항입니다.");
			$('#mPw').focus();
			return;
		}
		
		if($('#mPw').val() != $('#pw_check').val()) {
			alert("비밀번호가 일치하지 않습니다.");
			$('#pw_check').focus();
			return;
		}
		
		if($('#mName').val().length == 0) {
			alert("이름은 필수사항입니다.");
			$('#mName').focus();
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
		var queryString = $('#join_frm').serialize();
		$.ajax({
			url: 'join.do',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "success") {
					alert(result.desc)
					window.location.replace("login.jsp");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>

<div class="container-fluid">
        <div class="row">
            <div class="col test1"></div>
            <div class="col-5 test2">
				<form id="join_frm">
	            	<div class="form-group">
	                	<label for="mId">ID<span style="color: orangered;">*</span></label>
	                	<input type="text" class="form-control" id="mId" name="mId" placeholder="UserId">
	              	</div>
	            	<div class="form-group">
	              		<label for="mPw">Password<span style="color: orangered;">*</span></label>
	              		<input type="password" class="form-control" id="mPw" name="mPw" placeholder="Password">
	            	</div>
	            	<div class="form-group">
	              		<label for="mPw_check">Password_Check<span style="color: orangered;">*</span></label>
	              		<input type="password" class="form-control" id="pw_check" name="pw_check" placeholder="Password">
	            	</div>
	            	<div class="form-group">
	                	<label for="mName">이름<span style="color: orangered;">*</span></label>
	                	<input type="text" class="form-control" id="mName" name="mName" placeholder="UserName">
	              	</div>
	            	<div class="form-group">
	                	<label for="mAddress">Email address<span style="color: orangered;">*</span></label>
	                	<input type="text" class="form-control" id="mEmail" name="mEmail" placeholder="Enter email">
	             	</div>
	             	<div class="form-group">
	                	<label for="mAddress">Address</label>
	                	<input type="text" class="form-control" id="mAddress" name="mAddress">
	             	</div>
	             	<small id="joinhelp" class="form-text text-muted"><span style="color: orangered;">*</span>는 필수 사항입니다.</small>
	             	<p></p>
	  				<button type="button" class="btn btn-success btn-block mb-4" onclick="form_check()">회원 가입</button>
	        	</form>
            </div>
            <div class="col test1"></div>
        </div>   
    </div>
    
<!-- 
	<div class="container">
		<div class="row">
		    <div class="col-sm">
				<form id="join_frm" class="form-signin">
					<div class="sm-2">
					<br>
						아이디 : <input type="text" id="mId" name="mId" size="20"><br>
				        비밀번호 : <input type="password" id="mPw" name="mPw" size="20"><br>
				        비밀번호 확인 : <input type="password" id="pw_check" name="pw_check" size="20"><br>
				        이름 : <input type="text" id="mName" name="mName" size="20"><br>
				        메일 : <input type="text" id="mEmail" name="mEmail" size="20"><br>
				        주소 : <input type="text" id="mAddress" name="mAddress" size="50"><br><p>
				    </div>
				    <div class="sm-4">
				    <button class="btn btn-success btn-sm btn-block col-1" type="button" value="회원가입" onclick="form_check()">회원가입</button>
				    </div>
				</form>
			</div>
		</div>
	</div>
 -->
 
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>