<%@ page import="com.study.jsp.dto.MemberDto" %>
<%@ page import="com.study.jsp.dao.MemberDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="http://code.jquery.com/jquery.js"></script>

<%
		String mId = (String)session.getAttribute("mId");
		String mName = (String)session.getAttribute("mName");
		
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
<meta charset="UTF-8">
<title>답변</title>

<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
	function submit_ajax() {
		var queryString = $('#reply_frm').serialize();
		$.ajax({
			url: 'reply.do',
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

</head>
<body>

<div class="container">
답변쓰기 >
	<br>
		<table width="1000" border="1" class="col-12 text-center">
			<form id="reply_frm">
				<input type="hidden" name="bId" value="${reply_view.bId}">
				<input type="hidden" name="bGroup" value="${reply_view.bGroup}">
				<input type="hidden" name="bStep" value="${reply_view.bStep}">
				<input type="hidden" name="bIndent" value="${reply_view.bIndent}">
				<input type="hidden" name="bName" value="<%= mId %>">
				<tr>
					<td> 제목 </td>
					<td> <input type="text" id="bTitle" name="bTitle" value="${reply_view.bTitle}"> </td>
				</tr>
				<tr>
					<td> 내용 </td>
					<td> <textarea name="bContent" id="bContent" value="${reply_view.bContent}" rows="10" cols="150" > </textarea>
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
					<input type="button" value="답변" onclick="form_check()"> &nbsp;&nbsp;
					<a href="list.do?page=<%= session.getAttribute("cpage") %>"> 목록 </a>
					</td>
				</tr>
			</form>
		</table>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
</html>