<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시 내용 수정</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript" 
		src="./naver-editor/js/service/HuskyEZCreator.js" 
		charset="utf-8">
</script>
<script>
	function con_check() {
		if($('#bName').val().length == 0) {
			alert("이름은 필수 입력 사항입니다.");
			$('#bName').focus();
			return;
		}
		
		if($('#bTitle').val().length == 0) {
			alert("제목은 필수 입력 사항입니다.");
			$('#bTitle').focus();
			return;
		}
		
		form_check();
	}

	function form_check() {
		oEditors.getById["bContent"].exec("UPDATE_CONTENTS_FIELD", []);
		console.log("modify");
		
		submit_ajax();
	}
	
	function submit_ajax() {
		var queryString = $('#modify_frm').serialize();
		$.ajax({
			url: 'modify',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "ok") {
					alert(result.desc)
					window.location.replace("view?bId=${dto.bId}");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>

</head>
<body>
	<table width="820" cellpadding="0" cellspacing="0" border="1">
		<form id="modify_frm">
			<input type="hidden" id="bId" name="bId" value="${dto.bId}">
				<tr>
					<td> 번호 </td>
					<td> ${dto.bId} </td>
				</tr>
				<tr>
					<td> 히트 </td>
					<td> ${dto.bHit} </td>
				</tr>
				<tr>
					<td> 이름 </td>
					<td> <input type="text" id="bName" name="bName" value="${dto.bName}"> </td>
				</tr>
				<tr>
					<td> 제목 </td>
					<td> <input type="text" id="bTitle" name="bTitle" value="${dto.bTitle}"> </td>
				</tr>
				<tr>
					<td> 내용 </td>
					<td>
						<textarea name="bContent" id="bContent" rows="10" cols="100">
							${dto.bContent}
						</textarea>
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
						<button type="button" onclick="con_check()">수정 완료</button> &nbsp;&nbsp;
						<a href="view?bId=${dto.bId}"> 취소 </a> &nbsp;&nbsp;
						<!-- <a href="list?page=<%= session.getAttribute("cpage") %>"> 목록보기 </a> &nbsp;&nbsp; -->
						<a href="list"> 목록보기 </a> &nbsp;&nbsp;
					</td>
				</tr>
			</form>
	</table>
</body>
</html>