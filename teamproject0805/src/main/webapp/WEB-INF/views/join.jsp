<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" /> -->
    <meta name="description" content="" />
    <meta name="author" content="" />

    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="/assets/css/styles.css" rel="stylesheet" />
    <!-- css Files -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous">
        </script>
    <!-- Vendor CSS Files -->
    <link href="/assets/vendor/animate.css/animate.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="/assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    
    <title>KIDULT</title>
	
	<script src="http://code.jquery.com/jquery.js"></script>
	
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" 
	  rel="stylesheet" 
	  integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" 
	  crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" 
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" 
		crossorigin="anonymous">
</script>

<meta charset="UTF-8">

<title>회원가입 : KIDULT</title>

<script src="http://code.jquery.com/jquery.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	codeReset();
});

// 캡챠 리셋
function codeReset() {
    $.ajax({
    	type: "GET",
        url : '/homejoin/captchaReset',
        dataType:'json',
        success : function(data) {
        	console.log(data);
        	console.log(data.key);
            $("#key").val(data.key);
            $("#captcha").html("<img src='/navercaptcha/" + data.image + ".jpg'>");
        }
    });
}
</script>

<script type="text/javascript">
function id_check() {
	var textid = $("#mid").val();
	$.ajax({
		url: '/homejoin/idcheck',
		type: 'GET',
		data: { id : textid },
		dataType: 'text',
		success: function(json){
			console.log(json); 
			var result = JSON.parse(json);
			if(result.code == "success"){
				alert(result.desc);
				$("#checkbtn").attr("value","Y");
			} else {
				alert(result.desc);
				$('#mid').focus();
			}
		}
	});
}

function form_check() {
	var user = $("input[name=value]").val();
	if($('#checkbtn').val() == "N"){
		alert("아이디 중복확인은 필수입니다.");
		$('#mid').focus();
		return;
	} else if (user == "") {
		alert("보안코드를 입력해주세요.");
		$("input[name=value]").focus();
	} else {
		submit_ajax();
	}
}

function submit_ajax(){
	var queryString = $("#reg_frm").serialize();
	var user = $("input[name=value]").val();
	$.ajax({
		url: '/homejoin/dojoin',
		type: 'POST',
		data: queryString,
		dataType: 'text',
		success: function(json){
			console.log(json); 
			var result = JSON.parse(json);
			if(result.code == "success"){
				alert(result.desc);
				window.location.replace("joinOk");
			} else {
				alert(result.desc);
				codeReset();
				user.value = null;
			}
		}
	});
}
</script>

<style>
	.test1{  }
	.test2{ padding-top: 80px; }
	.text2-1{ padding-top: 50px; }
	.text2-2{ padding-top: 20px; }
</style>
</head>
<body>
	<div class="container-fluid">
        <div class="row">
            <div class="col test1"></div>
            <div class="col-5 test2">
            	<a href="/"> <img src="/assets/img/로고.png" alt="KIDULT logo" style="margin-left: 35%; width: 30%;"></a>
            	<p class="text2-1"></p>
				<form id="reg_frm">
					<div class="form-group" style="padding-bottom:20px;">
						<p class="text2-2">ID<span style="color: orangered;">*</span></p>
                		<input type="text" class="form-control" id="mid" name="mid" placeholder="UserId" style="width:80%; float:left;">
                		<button type="button" class="btn btn-dark btn-danger mb-4" onclick="id_check()" id="checkbtn" value="N" style="float:right;">중복확인</button>
						<br/>
					</div>
                	<div class="form-group" style="padding-bottom:20px;">
						<p class="text2-2">Password<span style="color: orangered;">*</span></p>
              			<input type="password" class="form-control" id="mpw" name="mpw" placeholder="Password">
					</div>
                	<div class="form-group" style="padding-bottom:20px;">
						<p>이름<span style="color: orangered;">*</span></p>
	                	<input type="text" class="form-control" id="mname" name="mname" placeholder="UserName">
					</div>
					<div class="form-group" style="padding-bottom:20px;">
						<p>Email address</p>
	                	<input type="text" class="form-control" id="memail" name="memail" placeholder="Enter email">
					</div>
					<div class="form-group" style="padding-bottom:20px;">
	             		<p>PhoneNumber</p>
	                	<input type="text" class="form-control" id="mpnum" name="mpnum">
					</div>
	                
					<div id="captcha">
					</div>
					<input type="button" class="btn btn-outline-secondary" value="보안코드새로고침" onclick="codeReset()"><br>
					<input type="hidden" id="key" name="key"><br>
					<div class="col-md-4 mb-4">
					  <label for="user">보안코드입력</label>
					  <input type="text" class="form-control" name="value" id="user" placeholder="enter code" required>
					</div>
	                
	                <div class="text-center">
	                	<small id="joinhelp" class="form-text text-muted"><span style="color: orangered;">*</span>는 필수 사항입니다.</small>
		             	<p></p>
		             	<button type="button" class="btn btn-info btn-lg" onclick="form_check()">회원 가입</button>
					</div>	

	            	<p></p>
	             	
	        	</form>
            </div>
            <div class="col test1"></div>
        </div>   
    </div>
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" 
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" 
		crossorigin="anonymous">
</script>
</body>
</html>