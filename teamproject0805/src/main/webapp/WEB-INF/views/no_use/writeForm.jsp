<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>

<script src="http://code.jquery.com/jquery.js"></script>

<script type="text/javascript" 
		src="./naver-editor/js/service/HuskyEZCreator.js" 
		charset="utf-8">
</script>

<script>
	function con_check() {
		if($('#bTitle').val().length == 0) {
			alert("제목은 필수 입력 사항입니다.");
			$('#bTitle').focus();
			return;
		}
		form_check();
	}
	
    function form_check() {
       	oEditors.getById["bContent"].exec("UPDATE_CONTENTS_FIELD", []);
       	console.log("write");
       	
       	submit_ajax();
    }
	
	function submit_ajax() {
		var queryString = $('#write_frm').serialize();
		$.ajax({
			url: 'write',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "ok") {
					alert(result.desc);
					window.location.replace("/list");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>

</head>
<body>

	<table width="800" cellpadding="0" cellspacing="0" border="1">
		<form id="write_frm">
			<tr>
				<td> 이름 </td>
				<td> <input type="text" id="bName" name="bName" size="50"> </td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" id="bTitle" name="bTitle" size="50"> </td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td><textarea id="bContent" name="bContent" rows="10" cols="110"></textarea>
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
					<button type="button" onclick="con_check()">글 올리기</button> &nbsp;&nbsp;
					<a href="list?page=${cpage}"> 목록보기</a>
			</tr>
		</form>
	</table>
</body>
</html>