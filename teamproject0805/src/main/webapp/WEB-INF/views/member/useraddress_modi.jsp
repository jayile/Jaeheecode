<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수


            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("aZipcode").value = data.zonecode;
            document.getElementById("aMain").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("aSub").focus();
        }
    }).open();
}

$(document).ready(function() {
	var chk = $("#aPrimary").val();
	if(chk == '1') {
		$("input[name=aPrimary]").prop("checked", true);
	} else {
		$("input[name=aPrimary]").prop("checked", false);
	}
	
});

</script>
</head>
<body>
<form id="adr_frm">

	<input type="hidden" id="mId" name="mId" value="${ainfo.mId}"> <br/>
	<input type="hidden" id="oriName" name="oriName" value="${ainfo.aName}"> <br/>
	배송지 이름 : <input type="text" id="aName" name="aName" placeholder="배송지 이름" value="${ainfo.aName}">
	<input type="checkbox" id="aPrimary" name="aPrimary" value="${ainfo.aPrimary}"> 주 배송지<br>
	우편 번호 : <input type="text" id="aZipcode" name="aZipcode" onclick="sample6_execDaumPostcode()" placeholder="우편번호" value="${ainfo.aZipcode}">
	<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
	주소 : <input type="text" id="aMain" name="aMain" placeholder="주소" value="${ainfo.aMain}"><br>
	상세주소 : <input type="text" id="aSub" name="aSub" placeholder="상세주소" value="${ainfo.aSub}">
	<br>
	<button type=button onclick="add_address()">배송지 수정</button>
	<!-- 인혁님거랑 맞춰서 수정다시만질것 -->
</form>
</body>
</html>