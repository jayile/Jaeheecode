<%@ page import="com.study.jsp.dto.MemberDto" %>
<%@ page import="com.study.jsp.dao.MemberDao" %>
<%@ page import="com.study.jsp.dto.BDto" %>
<%@ page import="com.study.jsp.dao.BDao" %>
<%@ page import="com.study.jsp.dto.RDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% if(session.getAttribute("validMem") != null) { 
	String mName = (String)session.getAttribute("mName");
	String mId = (String)session.getAttribute("mId");
	String vm = (String)session.getAttribute("ValidMem");
	String grade = (String)session.getAttribute("grade");
	
	MemberDao mDao = MemberDao.getInstance();
	MemberDto mDto = mDao.getMember(mId);
%>
	<jsp:forward page="main.jsp"></jsp:forward>
<% }
	String grade = (String)session.getAttribute("grade");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시 내용</title>

<!-- 부트스트랩 -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" 
    integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    
<script>
    function mReview(rContent) {
        var obj = document.getElementById("parent");
        var rDIV = document.createElement("textarea");
        var rcon = document.createTextNode(rContent);
        rDIV.cols="145";
        rDIV.onclick = function() {
            var p = this.parentElement; // 부모 HTML 태그 요소
            p.removeChild(this); // 자신을 부모로부터 제거
        };
        rDIV.appendChild(rcon);
        document.body.appendChild(rDIV);
        
    }
</script>
<script>
// 글 삭제
	function delete_check() {
        if (!confirm("글을 삭제하시겠습니까?")) {
            alert("글 삭제를 취소했습니다.");
        } else {
            alert("글을 삭제했습니다.");
            window.location.replace('delete.do?rId=${rDto.rId}');
        }
	}
</script>
<script>
// 댓글 삭제
	function rDelete_ajax(num) {
		if (!confirm("댓글을 삭제하시겠습니까?")) {
        } else {
            $.ajax({
    			url: 'rDelete.do',
    			type: 'POST',
    			data: { rId : num ,
    					bId : ${content_view.bId}},
    			dataType: 'text',
    			success: function(json) {
    				window.location.replace("content_view.do?bId=${content_view.bId}");
    			}
    		});
			alert("댓글을 삭제했습니다.");
        }
	}
</script>
<script>
// 글 추천
	function recommend_check() {
	    if (!confirm("이 글을 추천하시겠습니까?")) {
	    } else {
	    	reco_ajax();
	    }
	}
	
	function reco_ajax() {
		$.ajax({
			url: 'BReco.do',
			type: 'POST',
			data: { bId : ${content_view.bId} },
			dataType: 'text',
			success: function() {
				window.location.replace("content_view.do?bId=${content_view.bId}");
			}
		});
	}
</script>
<script>
//댓글 입력
	function rivew_check() {
        if ($('#rContent').val().length == 0) {
            alert("내용을 입력하세요.");
            $('#rContent').focus();
			return;
        } else {
        	Review_ajax();
        }
	}
	function Review_ajax() {
		var queryString = $('#review_form').serialize();
		$.ajax({
			url: 'review.do',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function() {
				window.location.replace("content_view.do?bId=${content_view.bId}");
			}
		});
	}
</script>
<script src="http://code.jquery.com/jquery.js"></script>

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
	자유게시판 >
	<div class="row align-items-start">	
		<div class="col">
			<table class="table">
				<tr>
					<td class="col-1">${content_view.bName}</td>
					<td class="col-3">${content_view.bTitle}</td>
					<td class="col-2">${content_view.bDate}</td>
					<td class="col-1"> 조회 : ${content_view.bHit}</td>
					<td class="col-1"> 추천 : ${content_view.bReco}</td>
				</tr>
			</table>
			<hr>
		</div>
	</div>
	
	<div class="col-6 col-sm-3">
		${content_view.bContent}
	</div>
	
<hr>
	<div>
		<button type="button" class="btn btn-primary btn-sm" onclick="javascript:window.location='list.do?page=<%= session.getAttribute("cpage") %>'">목록보기</button>
		<button type="button" class="btn btn-success btn-sm" onclick="javascript:window.location='reply_view.do?bId=${content_view.bId}'">답변</button>
		<button type="button" class="btn btn-primary btn-sm" onclick="recommend_check()">추천</button>
		<c:if test="${mId == rDto.rName}">
			<button type="button" class="btn btn-primary btn-sm" onclick="javascript:window.location='modify_view.do?bId=${content_view.bId}'">수정</button>
			<button type="button" class="btn btn-danger btn-sm" onclick="delete_check()">삭제</button>
		</c:if>
		<c:if test="${grade == 1}">
			<button type="button" class="btn btn-danger btn-sm" onclick="delete_check()">삭제</button>
		</c:if>
	</div>
<p>
	<c:if test="${not empty sessionScope.mId}">
		<div class="container">
			<div class="row align-items-start">
				<div>
					<form id="review_form">
						<div> ${mId} </div>
						<input type="hidden" name="bId" value="${content_view.bId}">
						<div> <textarea type="text" id="rContent" name="rContent" rows="2" cols="150"></textarea> </div>
					</form>
				</div>
			</div>
			<p>
			<div class="row">
				<button type="button" class="btn btn-primary btn-sm col-auto" onclick="rivew_check()">댓글입력</button>
			</div>
		</div>
		<br>
	</c:if>
</p>

	<div class="container">
		<table class="table">
			<thead>
				<c:forEach items="${list}" var="rDto">
						<input type="hidden" id="rId" name="rId" value="${rDto.rId}">
						<tr>
							<td>
								<font size="4em" color="blue"> ${rDto.rName} </font>
								<font size="2em" color="gray"> ${rDto.rDate} </font> <br>
								<div id="parent">
								${rDto.rContent}
								</div>
							</td>
							<td class="col-1">
								<div class="align-self-end">
									<c:if test="${mId == rDto.rName || grade == 1}">
										<c:if test="${mId == rDto.rName}">
											<button type="button" class="btn btn-primary btn-sm col-auto" onclick="mReview(toString(${rDto.rContent}))">수정</button>
										</c:if>
										<button type="button" class="btn btn-danger btn-sm col-auto" onclick="rDelete_ajax(${rDto.rId})">삭제</button>
									</c:if>
								</div>
							</td>
						</tr>
					<!--
					<div id="show">
						<font size="4em" color="blue"> ${rDto.rName} </font>
						<font size="2em" color="gray"> ${rDto.rDate} </font> <br>
						<div class="mReivew_text">
							<textarea id="rContent" name="rContent" rows="" cols="">${rDto.rContent}</textarea>
						</div>
						<div class="mReview-btn">
							<button type="button" class="btn btn-danger btn-sm col-auto" onclick="mReview()">수정하기</button>
							<button type="button" class="btn btn-danger btn-sm col-auto" onclick="mCancle()">취소</button>
						</div>
					</div>
					-->
				</c:forEach>
			</thead>
		</table>
	</div>
</div>

	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>