<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
<script src="http://code.jquery.com/jquery.js"></script>

<script>
	function writeForm() {
		window.location.replace("writeForm");
	}
	
	function notice() {
		window.location.replace("notice");
	}
</script>

</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>번호</td>
			<td>이름</td>
			<td>제목</td>
			<td>날짜</td>
			<td>히트</td>
		</tr>
		<c:forEach items="${list}" var="dto">
			<tr>
				<td>${dto.bId}</td>
				<td>${dto.bName}</td>
				<td>
					<c:forEach begin="1" end="${dto.bIndent}">-</c:forEach>
					<a href="view?bId=${dto.bId}">${dto.bTitle}</a>
				</td>
				<td>${dto.bDate}</td>
				<td>${dto.bHit}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5">
				<button type="button" onclick="writeForm()">글작성</button>
			 	<button type="button" onclick="notice()">공지사항</button>
			</td>
		</tr>

		<tr>
			<td colspan="5">
				<!-- 처음 -->
				<c:choose>
					<c:when test="${(page.curPage - 1) < 1}">
						[ &lt;&lt; ]
					</c:when>
					<c:otherwise>
						<a href="list?page=1">[ &lt;&lt; ]</a>
					</c:otherwise>
				</c:choose>
				<!-- 이전 -->
				<c:choose>
					<c:when test="${(page.curPage - 1) < 1}">
						[ &lt; ]
					</c:when>
					<c:otherwise>
						<a href="list?page=${page.curPage - 1}">[ &lt; ]</a>
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
						<a href="list?page=${fEach}">[ ${fEach} ]</a> &nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<!-- 다음 -->
				<c:choose>
					<c:when test="${(page.curPage + 1) > page.totalPage}">
						[ &gt; ]
					</c:when>
					<c:otherwise>
						<a href="list?page=${page.curPage + 1}">[ &gt; ]</a>
					</c:otherwise>
				</c:choose>
				<!-- 끝 -->
				<c:choose>
					<c:when test="${page.curPage == page.totalPage }">
						[ &gt;&gt; ]
					</c:when>
					<c:otherwise>
						<a href="list?page=${page.totalPage}">[ &gt;&gt; ]</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
				
	totalCount : ${page.totalCount} <br>
	listCount : ${page.listCount} <br>
	totalPage : ${page.totalPage} <br>
	curPage : ${page.curPage} <br>
	pageCount : ${page.pageCount} <br>
	startPage : ${page.startPage} <br>
	endPage :  ${page.endPage} <br>

</body>
</html>