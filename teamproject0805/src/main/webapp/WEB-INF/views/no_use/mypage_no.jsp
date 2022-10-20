<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
</head>
<body>

<sec:authorize access="isAuthenticated()">
<p> Log-In </p>
</sec:authorize>

<c:choose>
		<c:when test="${uinfo.mid != null}">
			${uinfo.mid} 님 안녕하세요.<br/>
		</c:when>
		<c:otherwise>
			${user.name} 님 안녕하세요.<br/>
		</c:otherwise>
</c:choose>
<%-- USER ID : ${pageContext.request.userPrincipal.name}<br/> --%>

<c:url value="/logout" var="logoutUrl" />
<a href="${logoutUrl}">Log Out</a>
<br/>
<br/>

<h2>mypage</h2><br/>
<hr/>
<h3>회원 정보</h3>
<br/>
<a href="/pwhome">내정보 조회/수정</a><br/>
비밀번호 변경<br/>
<a href="/member/getWish">찜목록</a><br/>
나의 리뷰<br/>

<hr/>
<h3>주문 정보</h3>
<br/>
장바구니<br/>
주문내역<br/>
<a href="/address/getList">배송지 관리</a><br/>

<hr/>
<h3>고객센터</h3>
<br/>
문의하기<br/>
공지사항<br/>
</body>
</html>