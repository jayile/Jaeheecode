<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	if(session.getAttribute("ValidMem") == null) {
		response.sendRedirect("login.jsp");
	}
	
		String mName = (String)session.getAttribute("mName");
		String mId = (String)session.getAttribute("mId");
		String grade = (String)session.getAttribute("grade");
%>

<!-- 부트스트랩 -->
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
<title>게시판</title>
<script src="http://code.jquery.com/jquery.js"></script>

<script type="text/javascript" 
		src="./naver-editor/js/service/HuskyEZCreator.js" 
		charset="utf-8">
</script>

<script>
    function form_check() {
       	oEditors.getById["bContent"].exec("UPDATE_CONTENTS_FIELD", []);
       	console.log("write");
       	
       	submit_ajax();
    }
</script>

<script>
	function con_check() {
		if($('#bTitle').val().length == 0) {
			alert("제목은 필수 입력 사항입니다.");
			$('#bTitle').focus();
			return;
		}
		if($('#wHead').val().length == 0) {
			alert("말머리를 선택해주세요.");
			$('#wHead').focus();
			return;
		}
		console.log("mName");
		form_check();
	}
	
	function submit_ajax() {
		var queryString = $('#write_frm').serialize();
		$.ajax({
			url: 'write.do',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "ok") {
					alert(result.desc)
					window.location.replace("list.do");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>

<script>
	var selectBoxChange = function(value){
		console.log("변경 값 : " + value);
		$("#changeInput").val(value);
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

	<div class="container">
	글쓰기 >
	<br>
		<table width="1000" border="1" class="col-12 text-center">
			<form id="write_frm">
				<tr>
					<td class="text-center" colspan="2"> 
						<select id="wHead" name="wHead" onchange="selectBoxChange(this.value);">
						<c:if test="${grade == 1}">
							<option value="notice">공지사항</option>
						</c:if>
							<option value="free">자유게시판</option>
							<option value="data">자료실</option>
						</select>
						<input type="text" id="changeInput" />
					</td>
				</tr>
				<tr class="col-1">
					<td> <input type="text" id="bTitle" name="bTitle" size="100" placeholder="제목을 입력하세요."> </td>
				</tr>
				<tr>
					<td> <textarea name="bContent" id="bContent" rows="10" cols="150" PlaceHolder="내용을 입력하세요.">
						 </textarea>
						<script type="text/javascript">
							var oEditors = [];
							nhn.husky.EZCreator.createInIFrame({
							    oAppRef: oEditors,
							    elPlaceHolder: "bContent",
							    sSkinURI: "./naver-editor/SmartEditor2Skin.html",
							    fCreator: "createSEditor2",
								htParams: { fOnBeforeUnload : function(){} }
							});
						</script>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-right">
					<button type="button" class="btn btn-primary btn-sm" onclick="con_check()">글 올리기</button>
					<button type="button" class="btn btn-danger btn-sm" onclick="javascript:window.location='list.do?page=<%= session.getAttribute("cpage") %>'">취소</button>
					</td>
				</tr>
			</form>
		</table>
	</div>
	<div class="container text-center">
		<form action="uploadAction.jsp" method="post" enctype="multipart/form-data">
			파일 : 	<input type="file" name="file" >
			<button type="submit" id="fileName" name="fileName" class="btn btn-primary btn-sm" >파일 업로드</button>
		</form>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>