<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소선택</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script>


function closePopup(aname, adrname, zip, main, sub) {
	window.opener.document.getElementById("aName").value = aname;
	window.opener.document.getElementById("adrName").value = adrname;
	window.opener.document.getElementById("aZipcode").value = zip;
	window.opener.document.getElementById("aMain").value = main;
	window.opener.document.getElementById("aSub").value = sub;
	self.close();
}
function add_adr() {
	window.location.replace("/member/adrForm");
}
</script>
</head>
<body>
	<form id="pop_frm">
		<c:choose>
		<c:when test="${adr != null}">
			<c:forEach items="${adr}" var="dto" varStatus="st">
				<c:if test="${dto.aPrimary != '0'}">
					<h4>주배송지</h4>
				</c:if>
				배송지명 : ${dto.aName}<input type="hidden" id="aName" name="aName"value="${dto.aName}" /> <br/>
				수령자 이름 : ${dto.adrName}<input type="hidden" id="adrName" name="adrName"value="${dto.adrName}" /> <br/>
				우편번호 : ${dto.aZipcode}<input type="hidden" id="aZipcode" name="aZipcode"value="${dto.aZipcode}" /> <br/>
				주소 : ${dto.aMain}<input type="hidden" id="aMain" name="aMain"value="${dto.aMain}" /> <br/>
				상세주소 : ${dto.aSub}<input type="hidden" id="aSub" name="aSub"value="${dto.aSub}" /> <br/>
				<br/>
				<button type=button onclick="closePopup('${dto.aName}','${dto.adrName}','${dto.aZipcode}','${dto.aMain}','${dto.aSub}')">배송지 선택</button>
				<hr/>
			</c:forEach>
		</c:when>
		<c:otherwise>
			저장된 배송지가 없어요.<br/>
		</c:otherwise>
	</c:choose>
	</form>
	<button type=button onclick="add_adr()">배송지 추가</button><br/>
	
	
	
</body>
</html>