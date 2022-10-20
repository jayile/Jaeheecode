<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팝업</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script>
function closePopup() {
	var queryString = $("#bform").serialize();
	$.ajax({
		url: '/admin/b_add',
		type: 'POST',
		data: queryString,
		dataType: 'text',
		success: function(json){
			var result = JSON.parse(json);
			if(result.code == "success"){
				window.opener.location.reload();
				self.close();
			}
		}
	});
	
}
</script>
</head>
<body>
	<h3>유저 블랙리스트 등록</h3>
	<br/>
	<hr/>

	<form id="bform">
	유저 아이디   ${ulist.mid}<input type="hidden" id="mid" name="mid" value="${ulist.mid}" /><br/>
	<br/>
	등록 타입     <input type="radio" id="btype" name="btype" value="1" checked/>로그인 정지 &nbsp;&nbsp;&nbsp;&nbsp;
	              <input type="radio" id="btype" name="btype" value="2"/>결제 정지<br/>
	<br/>
	등록 사유     <input type="text" id="bmemo" name="bmemo" /><br/>
	</form>
	<br/>
	<input type="button" value="확인" onclick="closePopup()">
</body>
</html>