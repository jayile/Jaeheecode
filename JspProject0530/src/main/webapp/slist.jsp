<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        </div>
    </nav>
    
</head>
<body>
<script src="http://code.jquery.com/jquery.js"></script>

<script>

	function form_check1() {
		if($('#value').val().length == 0) {
			alert("검색어를 입력하세요.");
			$('#value').focus();
			return;
		}
		serch_ajax1();
	}
	
	function serch_ajax1() {
		var key = $('#key').val();
		var value = $('#value').val();
		var re = 'slist.do?key='+key+'&value='+value;
			window.location.replace(re);
	}	

</script>

<div class="container text-center">
	<table class="table table-hover">
	 	 <thead>
	  
	    <tr>
	      	<td scope="col">번호</td>
	      	<td scope="col">제목</td>
	      	<td scope="col">글쓴이</td>
	     	<td scope="col">등록일</td>
	      	<td scope="col">조회</td>
			<td scope="col">추천</td>
	    </tr>
	    
	 	 </thead>
	 	 <tbody>
	  		
		    <c:forEach items="${serchlist}" var="dto">
				<tr>
					<td scope="row">${dto.bId}</td>
					<td>
						<c:forEach begin="1" end="${dto.bIndent}">></c:forEach>
		                <a href="content_view.do?bId=${dto.bId }">${dto.bTitle} </a>
		                	<c:if test="${dto.rCount != 0 }">
								(${dto.rCount})
							</c:if>
				            <c:set var="now" value="<%=new java.util.Date()%>" /><!-- 현재시간 -->
						        <fmt:formatDate value="${now}" pattern="yyMMddhh" var="today" /><!-- 현재시간을 숫자로 -->
							        <fmt:formatDate  value="${dto.bDate}" pattern="yyMMddhh" var="dtoDate" /><!-- 게시글 작성날짜를 숫자로 -->
					        <c:if test="${today - dtoDate le 3}"><!-- 1시간동안은 new 표시 -->
					            <span class="badge badge-warning">new</span>
					        </c:if>
					</td>
					<td>${dto.bName}</td>
					<td>${dto.bDate}</td>
					<td>${dto.bHit}</td>
					<td>${dto.bReco}</td>
				</tr>
			</c:forEach>
			
		</tbody>

	</table>
	
		<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center">
			<!-- 처음 -->
        	<c:choose>
			<c:when test="${(page.curPage - 1) < 1}">
            	<li class="page-item">
                	<a class="page-link" aria-label="Previous">
                  		<span aria-hidden="true">&laquo;</span>
                	</a>
            	</li>
            </c:when>
            <c:otherwise>
            	<li class="page-item">
                	<a class="page-link" href="list.do?page=1" aria-label="Previous">
                  		<span aria-hidden="true">&laquo;</span>
                	</a>
            	</li>
			</c:otherwise>
			</c:choose>
  
         	<!-- 이전 -->
			<c:choose>
			<c:when test="${(page.curPage - 1) < 1}">
				<li class="page-item">
            		<a class="page-link">Previous</a>
            	</li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
            		<a class="page-link" href="list.do?page=${page.curPage - 1}">Previous</a>
            	</li>
			</c:otherwise>
			</c:choose>
            
            
            <!-- 개별페이지 -->
            <c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
				<c:choose>
				<c:when test="${page.curPage == fEach}">
					 <li class="page-item">
					  	<a class="page-link">${fEach}</a>
					 </li>
				</c:when>
					<c:otherwise>
            <li class="page-item">
            	<a class="page-link" href="list.do?&page=${fEach}">${fEach}</a>
            </li>
            </c:otherwise>
				</c:choose>
			</c:forEach>

            <!-- 다음 -->
			<c:choose>
			<c:when test="${(page.curPage + 1) > page.totalPage}">
				<li class="page-item">
                	<a class="page-link">Next</a>
            	</li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
                	<a class="page-link" href="list.do?page=${page.curPage + 1}">Next</a>
            	</li>
			</c:otherwise>
			</c:choose>
			
            <!-- 끝 -->
			<c:choose>
			<c:when test="${page.curPage == page.totalPage}">
				<li class="page-item">
                	<a class="page-link" aria-label="Next">
                  	<span aria-hidden="true">&raquo;</span>
                	</a>
            	</li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
                	<a class="page-link" href="list.do?page=${page.totalPage}" aria-label="Next">
                  	<span aria-hidden="true">&raquo;</span>
                	</a>
            	</li>
			</c:otherwise>
			</c:choose>

        </ul>
    </nav>
	
	<c:choose>	
		<c:when test="${not empty sessionScope.mId}">
			<div class="text-right">
				<button type="button" class="btn btn-primary btn-sm" onclick="javascript:window.location='write_view.do'">글쓰기</button>
			</div>
		</c:when>	
	</c:choose>
	
	<form id="serch_frm">
		<select id="key" name="key">
			<option value="bTitle">제목</option>
			<option value="bContent">내용</option>
			<option value="bName">작성자</option>
		</select>
		<input type="text" id="value" name="value" size="30">
		<button type="button" id="serch_btn" class="btn btn-success btn-sm" onclick="form_check1()">검색</button>
	</form>

</div>

	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" 
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
</html>