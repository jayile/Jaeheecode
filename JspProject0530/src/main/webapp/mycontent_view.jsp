<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% if(session.getAttribute("validMem") != null) { %>
	<jsp:forward page="login.jsp"></jsp:forward>
<% } 
		String mName = (String)session.getAttribute("mName");
		String mId = (String)session.getAttribute("mId");
		String vm = (String)session.getAttribute("ValidMem");
%>

<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery.js"></script>

<!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" 
    integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    
<meta charset="UTF-8">
<title>게시판 리스트</title>
	<nav class="navbar navbar-dark bg-primary navbar-expand-lg">
        <a class="navbar-brand" href="main.jsp">Hello, world!</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
	        <ul class="navbar-nav mr-auto">
	            <li class="nav-item active">
	              <a class="nav-link" href='main.jsp'> 메인 <span class="sr-only">(current)</span></a>
	            </li>
	            
	            <li class="nav-item">
	              <a class="nav-link" href='list.do'> 자유게시판 </a>
	            </li>
	            
	            <li class="nav-item">
	              <a class="nav-link" href="#">실시간 채팅</a>
	            </li>
         	</ul>
        </div>
    </nav>
</head>
<body>
<div class="container text-center">
<table class="table table-hover">
  <thead>
    <tr>
      	<td scope="col">제목</td>
      	<td scope="col">글쓴이</td>
     	<td scope="col">등록일</td>
      	<td scope="col">조회</td>
		<td scope="col">추천</td>
    </tr>
  </thead>
  <tbody>
  	<c:if test=" mId == ${dto.bName}">
	    <c:forEach items="${list}" var="dto">
			<tr>
				<td scope="row">${dto.bId}</td>
				<td>
					<c:forEach begin="1" end="${dto.bIndent}">></c:forEach>
					<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a>
				</td>
				<td>${dto.bName}</td>
				<td>${dto.bDate}</td>
				<td>${dto.bHit}</td>
				<td>${dto.bReco}</td>
			</tr>
		</c:forEach>
	</c:if>
  </tbody>
					<td colspan="6">
						<!-- 처음 -->
						<c:choose>
							<c:when test="${(page.curPage - 1) < 1}">
								[ &lt;&lt; ]
							</c:when>
							<c:otherwise>
								<a href="list.do?page=1">[ &lt;&lt; ]</a>
							</c:otherwise>
						</c:choose>
						<!-- 이전 -->
						<c:choose>
							<c:when test="${(page.curPage - 1) < 1}">
								[ &lt; ]
							</c:when>
							<c:otherwise>
								<a href="list.do?page=${page.curPage - 1}">[ &lt; ]</a>
							</c:otherwise>
						</c:choose>
						
						<!-- 개별 페이지 -->
						<!-- ${page.startPage} -->
						<!-- ${page.endPage} -->
						<c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
							<c:choose>
							<c:when test="${page.curPage == fEach}">
								[ ${fEach} ] &nbsp;
							</c:when>
							<c:otherwise>
								<a href="list.do?page=${fEach}">[ ${fEach} ]</a> &nbsp;
							</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<!-- 다음 -->
						<c:choose>
							<c:when test="${(page.curPage + 1) > page.totalPage}">
								[ &gt; ]
							</c:when>
							<c:otherwise>
								<a href="list.do?page=${page.curPage + 1}">[ &gt; ]</a>
							</c:otherwise>
						</c:choose>
						<!-- 끝 -->
						<c:choose>
							<c:when test="${page.curPage == page.totalPage }">
								[ &gt;&gt; ]
							</c:when>
							<c:otherwise>
								<a href="list.do?page=${page.totalPage}">[ &gt;&gt; ]</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
	</table>
			<button type="button" class="btn btn-primary btn-sm" onclick="javascript:window.location='write_view.do'">글쓰기</button>

</div>

	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" 
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
</html>