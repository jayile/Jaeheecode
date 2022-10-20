<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시 내용</title>
<script>
	//글 삭제
	function delete_check() {
	    if (!confirm("글을 삭제하시겠습니까?")) {
	        alert("글 삭제를 취소했습니다.");
	    } else {
	        alert("글을 삭제했습니다.");
	        window.location.replace('delete?bId=${dto.bId}');
	    }
	}
</script>

</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td> 번호 </td>
			<td> ${dto.bId}</td>
		</tr>
		<tr>
			<td> 히트 </td>
			<td> ${dto.bHit}</td>
		</tr>
		<tr>
			<td> 이름 </td>
			<td> ${dto.bName}</td>
		</tr>
		<tr>
			<td> 제목 </td>
			<td> ${dto.bTitle}</td>
		</tr>
		<tr>
			<td> 내용 </td>
			<td> ${dto.bContent}</td>
		</tr>
		<tr>
			<td colspan="2">
				<a href="modifyForm?bId=${dto.bId}"> 수정 </a> &nbsp;&nbsp;
				<!-- <a href="list?page=<%= session.getAttribute("cpage") %>"> 목록보기 </a> &nbsp;&nbsp; -->
				<a href="list"> 목록보기 </a> &nbsp;&nbsp;
				<button type="button" onclick="delete_check()">삭제</button> &nbsp;&nbsp;
				<a href="reply_view?bId=${dto.bId}"> 답변 </a>
		</tr>
	</table>
</body>
</html>