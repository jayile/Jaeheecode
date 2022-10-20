<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	if(session.getAttribute("ValidMem") != null && session.getAttribute("mId") == session.getAttribute("mId"))
	{
	} else {
		response.sendRedirect("login.jsp");
	}
	
	String mName = (String)session.getAttribute("mName");
	String mId = (String)session.getAttribute("mId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시 내용 수정</title>
<script src="http://code.jquery.com/jquery.js"></script>

<script type="text/javascript" 
		src="./naver-editor/js/service/HuskyEZCreator.js" 
		charset="utf-8">
</script>
<script>
        function form_check() {
        	oEditors.getById["bContent"].exec("UPDATE_CONTENTS_FIELD", []);
        	
        	submit_ajax();
        }
</script>

<script>
	function con_check() {
		if($('#bName').val().length == 0) {
			alert("이름은 필수 입력 사항입니다.");
			$('#bName').focus();
			return;
		}
		
		if($('#bTitle').val().length == 0) {
			alert("제목은 필수 입력 사항입니다.");
			$('#bTitle').focus();
			return;
		}
		
		form_check();
	}
	
	function submit_ajax() {
		var queryString = $('#modify_frm').serialize();
		$.ajax({
			url: 'modify.do',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "ok") {
					alert(result.desc)
					window.location.replace("content_view.do?bId=${content_view.bId}");
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
	<table width="820" cellpadding="0" cellspacing="0" border="1">
		<form id="modify_frm">
			<input type="hidden" name="bId" value="${content_view.bId}">
				<tr>
					<td> 번호 </td>
					<td> ${content_view.bId} </td>
				</tr>
				<tr>
					<td> 히트 </td>
					<td> ${content_view.bHit} </td>
				</tr>
				<tr>
					<td> 이름 </td>
					<td> <input type="text" id="bName" name="bName" value="${content_view.bName}"> </td>
				</tr>
				<tr>
					<td> 제목 </td>
					<td> <input type="text" id="bTitle" name="bTitle" value="${content_view.bTitle}"> </td>
				</tr>
				<tr>
					<td> 내용 </td>
					<td>
						<textarea name="bContent" id="bContent" rows="10" cols="100">
							${content_view.bContent}
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
					<td colspan="2">
						<input type="button" value="수정완료" onclick="con_check()"> &nbsp;&nbsp;
						<a href="content_view.do?bId=${content_view.bId}"> 취소 </a> &nbsp;&nbsp;
						<a href="llist.do?page=<%= session.getAttribute("cpage") %>"> 목록보기 </a> &nbsp;&nbsp;
					</td>
				</tr>
			</form>
	</table>
</body>
</html>