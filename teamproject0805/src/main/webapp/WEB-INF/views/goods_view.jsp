<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
    
    <script type="text/javascript" src="/assets/js/cart.js"></script>  
    
    <title>상품상세 : KIDULT</title>
    
	<script src="http://code.jquery.com/jquery.js"></script>
<script>
//매장 안내
function map_info() {
	window.location.replace("/map_info");
}
// 상품 목록
function goods_list(key) {
	console.log(key);
	window.location.replace("/goods/goods_kind?kind=" + key);
}
// 장바구니
function cart() {
	window.location.replace("/goods_cart");
}
//로그인
function login() {
	window.location.replace("/userlogin");
}
// 로그아웃
function logout() {
	window.location.replace("/logout");
}
// 마이페이지
function userpage() {
	window.location.replace("/member/mypage");
}
// 회원정보
function pwcheck() {
	window.location.replace("/pwhome");
}

function wish() {
	window.location.replace("/member/getWish");
}

function myreview() {
	window.location.replace("/member/myreview");
}

function go_cart() {
	window.location.replace("/goods_cart");
}

function go_mypay() {
	window.location.replace("/member/go_mypay");
}

function userAddress() {
	window.location.replace("/address/getList");
}
// 공지사항
function notice_view() {
	window.location.replace("/notice/notice_view");
}
// 1:1 문의
function customer_page() {
	window.location.replace("/member/customerPage")
}
// ===================================================
function adminpage() {
	window.location.replace("/admin/adminmain");
}
// 회원정보
function userlist() {
	window.location.replace("/admin/getlist");
}
function blacklist() {
	window.location.replace("/admin/blacklist");
}
function paylist() {
	window.location.replace("/admin/pay_list");
}
// 상품 등록
function goods_form() {
	window.location.replace("/admin/goods_form");
}
// 상품 재고 관리
function goods_stock() {
	window.location.replace("/admin/goods_stock");
}
// 공지사항
function notice_view() {
	window.location.replace("/notice/notice_view");
}
// 문의 내역(관리자)
function qna_list() {
	window.location.replace("/member/qna_list");
}
function change_check() {
	$.ajax({
		url: '/modi_check',
		type: 'POST',
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "success") {
				// alert("홈유저");
				window.location.replace("/member/change_pw");
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>
<script>
function changelike() {
	var like = document.getElementById('like');
	if( like.src.match('/assets/img/like.png') ) {
		add_like();
		like.src = '/assets/img/likeok.png';
		
	} else {
		del_like();
		like.src = '/assets/img/like.png';
	}
}

function add_like() {
	var goodsNum = $("#cId").val();

	$.ajax({
		url: '/goods/goods_wish',
		type: 'POST',
		data: { cId : goodsNum },
		dataType: 'text',
		success: function(json){
			console.log(json); 
			var result = JSON.parse(json);
			if(result.code == "success"){
				alert(result.desc);
			} else {
				alert(result.desc);
			}
		}
	});
}

function del_like() {
	var goodsNum = $("#cId").val();

	$.ajax({
		url: '/goods/goods_delwish',
		type: 'POST',
		data: { cId : goodsNum },
		dataType: 'text',
		success: function(json){
			console.log(json); 
			var result = JSON.parse(json);
			if(result.code == "success"){
				alert(result.desc);
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>
<script>
function cart_ajax(cDown) {
	console.log("cDown : " + cDown);
	var queryString = $('#gPut_frm').serialize();
	$.ajax({
		url: '/goods/goods_put',
		type: 'POST',
		data: queryString,
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				var ret = confirm("장바구니로 이동할까요?");
				if(ret == true) {
					window.location.replace("/goods_cart");
				} else {
					
				}
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>

<script>
function buy_ajax() {
	var queryString = $('#gPut_frm').serialize();
	$.ajax({
		url: '/goods/direct_buy',
		type: 'POST',
		data: queryString,
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				window.location.replace("/goods_cart");
			}
		}
	});
}
</script>

<script>
function write_review() {
	if($('#rContent').val().length == 0) {
		alert("리뷰내용을 입력해주세요.");
		$('#rContent').focus();
		return;
	} else if ($('input[type=radio]:checked').val() == null) {
		alert("평점을 입력해주세요.");
		return;
	}
	review_ajax();
}

function review_ajax() {
	var rContent = $('input[name=rContent]').val();
	var cId = $('input[name=cId]').val();
	var star = $('input[type=radio]:checked').val();
	$.ajax({
		url: '/goods/goods_review',
		type: 'POST',
		data: { review : rContent,
				reviewcId : cId, 
				score : star },
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				$('#rcount').load(location.href+' #rcount');
				$('#reviewList').load(location.href+' #reviewList');
				var rc = document.getElementById("rContent").value = null;
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>

<script>
function delete_review() {
	var ret = confirm("리뷰를 삭제하시겠습니까?");
	var rc = event.target.previousElementSibling.textContent;
	var rd = event.target.parentElement.parentElement;
	if(ret == true) {
		rd.remove();
		rDelete_ajax(rc);
	} else { }
}

function rDelete_ajax(rc) {
	var cId = $('input[name=cId]').val();
	$.ajax({
		url: '/goods/delete_review',
		type: 'POST',
		data: { cId : cId,
				rContent : rc },
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				$('#rcount').load(location.href+' #rcount');
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>

<style>
#test_btn1{
	border-top-left-radius: 5px;
	border-bottom-left-radius: 5px;
	margin-right:-4px;
}
#test_btn2{
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;    
    margin-left:-3px;
}
#btn_group button{
    border: 1px solid #ddd;
    background-color: #ffffff;
    color: skyblue;
    padding: 5px;
}
#btn_group button:hover{
    color:white;
    background-color: skyblue;
}
/* star component */
.star-rating {
  display:flex;
  flex-direction: row-reverse;
  font-size:1em;
  justify-content:space-around;
  padding:0 .2em;
  text-align: right;
  width:5em;
}

.star-rating input {
  display:none;
}

.star-rating label {
  color:#ccc;
  cursor:pointer;
}

.star-rating :checked ~ label {
  color:#f90;
}

.star-rating label:hover,
.star-rating label:hover ~ label {
  color:#fc0;
}
/* 작성된 리뷰용 별점 */
.star_rating2 {
	font-size:1em; 
	letter-spacing:-20px;
}
.star_rating2 a {
    font-size:1em;
	letter-spacing:-1px;
	display:inline-block;
	margin-left:0;
	color: #ccc;
	text-decoration:none;
}
.star_rating2 a:first-child {
	margin-left:0;
}
.star_rating2 a.on {
	color:#f90;
}
/* 리뷰 수정용 */
.star-rating3 {
  display:flex;
  flex-direction: row-reverse;
  font-size:1em;
  justify-content:space-around;
  padding:0 .2em;
  text-align: right;
  width:5em;
}

.star-rating3 input {
  display:none;
}

.star-rating3 label {
  color:#ccc;
  cursor:pointer;
}

.star-rating3 :checked ~ label {
  color:#f90;
}

.star-rating3 label:hover,
.star-rating3 label:hover ~ label {
  color:#fc0;
}
/* 리뷰 수정용 */
#star { 
	font-size:0; 
	letter-spacing:-4px;
}
#star a {
	font-size:22px;
	letter-spacing:-5px;
	display:inline-block; 
	margin-left:5px; 
	color:#ccc; 
	text-decoration:none;
}
#star a:first-child {
	margin-left:0;
}
#star a.on {
	color:#777;
}
/* modal */
.modal {
    display: none;
    z-index: 500;
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.8);
}
.modalBox {
    position: relative;
    text-align: center;
    top : 15%;
    left : 50%;
    position : sticky;
}
p img:hover{
    cursor: -webkit-zoom-in;
}
</style>

</head>
<body>

<script>
function mReview_do(rId) {
	var cId = $('input[name=cId]').val();
	var star = $('input[type=radio]:checked').val();
	var rContent = document.getElementById('modi').value;
	console.log(cId);
	console.log(rId);
	console.log(star);
	console.log(rContent);
	if(rContent.length == 0) {
		alert("내용을 입력해주세요.");
		rContent.focus();
		return;
	}
	mReview_ajax(cId, star, rContent, rId);
}
function mReview_ajax(cId, star, rContent, rId) {
	$.ajax({
		url: '/modify_review',
		type: 'POST',
		data: { cId : cId,
				score : star,
				rContent : rContent,
				rId : rId },
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				alert(result.desc);
				$('#reviewList').load(location.href+' #reviewList');
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>
<script>
function modify_review(rId, score) {
	var ret = confirm("리뷰를 수정하시겠습니까?");
	console.log("rId : " + rId);
	var rCon = event.target.previousElementSibling.previousElementSibling.textContent;
	if(ret == true) {
		modify_review_do(rCon, rId, score);
	} else {  }
}

function modify_review_do(rCon, rId, score) {
	$('#review'+ rId).remove();

	var mReview_form = "";
	$('input[type=radio]:checked').val() == score
	mReview_form +='<form id="mReview">';
	mReview_form +='	<span style="margin-bottom: 0%; margin-right: 2%; font-size: 12pt; float: left;">${uinfo.mname}님</span>';
	mReview_form +='	<div class="star-rating3 space-x-4 mx-auto" style="float: left;">';
	if (score == 5) {
		mReview_form +='		<input type="radio" id="5-stars3" name="rating" value="5" checked />';
	}  else {
		mReview_form +='		<input type="radio" id="5-stars3" name="rating" value="5" />';
	}
	mReview_form +='		<label for="5-stars3" class="star">&#9733;</label>';
	if (score == 4) {
		mReview_form +='		<input type="radio" id="4-stars3" name="rating" value="4" checked />';
	}  else {
		mReview_form +='		<input type="radio" id="4-stars3" name="rating" value="4" />';
	}
	mReview_form +='		<label for="4-stars3" class="star">&#9733;</label>';
	if (score == 3) {
		mReview_form +='		<input type="radio" id="3-stars3" name="rating" value="3" checked />';
	}  else {
		mReview_form +='		<input type="radio" id="3-stars3" name="rating" value="3" />';
	}
	mReview_form +='		<label for="3-stars3" class="star">&#9733;</label>';
	if (score == 2) {
		mReview_form +='		<input type="radio" id="2-stars3" name="rating" value="2" checked />';
	}  else {
		mReview_form +='		<input type="radio" id="2-stars3" name="rating" value="2" />';
	}
	mReview_form +='		<label for="2-stars3" class="star">&#9733;</label>';
	if (score == 1) {
		mReview_form +='		<input type="radio" id="1-star3" name="rating" value="1" checked />';
	}  else {
		mReview_form +='		<input type="radio" id="1-star3" name="rating" value="1" />';
	}
	mReview_form +='		<label for="1-star3" class="star">&#9733;</label>';
	mReview_form +='	</div>';
	mReview_form +='	<br />';
	mReview_form +='	<input type="text" id="modi" name="modi" style="margin-top: 1%; width: 85%;" value="' + rCon + '"></input>';
	mReview_form +='	<button class="btn btn-sm btn-primary" type="button" style="margin-top: 0%;" onclick="mReview_do('+rId+')">수정 완료</button> &nbsp;';
	mReview_form +='	<button class="btn btn-sm btn-primary" type="button" style="margin-top: 0%;" onclick="get_reviewlist()">취소</button>';
	mReview_form +='</form>';
	
	$('#mReview' + rId).replaceWith(mReview_form);
	$('#modi').focus();
}

function get_reviewlist() {
	$('#reviewList').load(location.href+' #reviewList');
}
</script>

<script>
$(function(){
	//  이미지 클릭시 해당 이미지 모달
	$("#photo1").click(function(){
	    let img = new Image();
	    img.src = $(this).attr("src")
	    $('.modalBox').html(img);
	    $(".modal").show();
	});
	//모달 클릭할때 이미지 닫음
	 $(".modal").click(function (e) {
	 $(".modal").toggle();
	});
});
</script>

<!-- ======= Top Bar ======= -->
<section id="topbar" class="d-flex align-items-center">
	<div class="container d-flex justify-content-center justify-content-md-between">
		<div class="contact-info d-flex align-items-center" id="topbarcon">
			<a onclick="map_info()" id="topbarcon">매장안내</a> &nbsp;&nbsp;
		</div>
		<div class="social-links d-none d-md-block">
			<c:choose>
				<c:when test="${uinfo.mname != null}"> 
					<span id="topbarcon"> ${uinfo.mname}님 </span> &nbsp;
				</c:when>
				<c:otherwise> 
					<a onclick="login()" id="topbarcon">로그인</a> &nbsp;
				</c:otherwise>
			</c:choose>

			<c:if test="${uinfo.mname != null}"> 
				<span id="topbarcon" > <a onclick="logout()" id="topbarcon"> 로그아웃 </a> </span> &nbsp;
			</c:if>
			<c:if test="${uinfo.authority != 'ROLE_ADMIN'}"> 
				<a onclick="notice_view()" id="topbarcon">고객센터</a>
			</c:if>
		</div>
	</div>
</section>

<!-- Navigation-->
<header id="header" class="container">
	<div class="row">
		<div class="col-sm-3">
			<a href="/"> <img src="/assets/img/로고.png" alt="KIDULT logo" id="logo"></a>
		</div>
		<div class="col-sm-6">
			<form action="/goods/goods_search" method="post">
				<input class="search" type="text" id="word" name="word" placeholder="검색어를 입력해주세요.">
				<button type="submit" class="btn btn-secondary" id="sbtn"> 검색 </button>
			</form>
		</div>
		<div class="col-sm-3">
			<nav class="navbar">
				<ul id="header_navi">
					<c:choose>
						<c:when test="${uinfo.authority != 'ROLE_ADMIN'}"> 
							<li>
								<a onclick="wish()"><img src="/assets/img/like.png"><span>찜목록</span></a>
							</li>
						</c:when>
					</c:choose>
					<li>
						<c:choose>
							<c:when test="${uinfo.authority == 'ROLE_ADMIN'}">
								<a onclick="adminpage()"><img src="/assets/img/account.png"><span>회원 관리</span></a>
							</c:when>
							<c:otherwise>
								<a onclick="userpage()"><img src="/assets/img/account.png"><span>마이페이지</span></a>
							</c:otherwise>
						</c:choose>
						
					</li>
					<li>
						<c:choose>
							<c:when test="${uinfo.authority == 'ROLE_ADMIN'}"> 
								<a onclick="goods_stock()"><img src="/assets/img/cart.png"><span>상품 관리</span></a>
							</c:when>
							<c:otherwise>
								<a onclick="cart()"><img src="/assets/img/cart.png"><span>장바구니</span></a>
							</c:otherwise>
						</c:choose>
					</li>
					<c:choose>
						<c:when test="${uinfo.authority == 'ROLE_ADMIN'}"> 
							<li>
								<a onclick="notice_view()"><img src="/assets/img/bell.png"><span>고객센터</span></a>
							</li>
						</c:when>
					</c:choose>
				</ul>
				<i class="bi bi-list mobile-nav-toggle"></i>
			</nav> <!-- .navbar -->
		</div>
	</div>
</header>
<!-- End Header -->

    <!-- 중간 메뉴바 -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light d-flex">      
        <div class="collapse navbar-collapse" id="navbarColor03">
            <ul class="navbar-nav me-auto" style="margin-left: 300px;">
	            <li class="nav-item dropdown">
	                <a class="nav-link dropdown-toggle" 
	                    data-bs-toggle="dropdown" 
	                    href="#" role="button" 
	                    aria-haspopup="true" 
	                    aria-expanded="false"> 카테고리 </a>
	                <div class="dropdown-menu">
		                <a class="dropdown-item" onclick="goods_list('전자게임')">전자게임</a>
		                <a class="dropdown-item" onclick="goods_list('교육완구')">교육완구</a>
		                <a class="dropdown-item" href="#">인형</a>
	                </div>
	            </li>
            </ul>
        </div>
    </nav>
    <!-- 중간 메뉴바 끝 -->

	<div class="modal">
	    <div class="modalBox">
	    </div>
	</div>

	<div class="container" style="margin-top: 5%; padding: 0;">
		<div class="row">
			<div id="top" class="col-sm-4" style="margin-left: 20%; float: right; padding: 0;">
				<img id="photo1" width="400px;" src="/upload/${dto.cPhoto1}"/>
			</div>
			
			<div class="col-sm-4" style="margin-left: 0; float: right; padding: 0;" >
				
				<!-- 찜목록에 있을시 꽉찬하트 -->
				<c:choose>
					<c:when test="${wish.cId != null}"> 
						<img id="like" style="margin-left: 77%" src="/assets/img/likeok.png" onclick="changelike()">
	                </c:when>
	                <c:otherwise> 
	                	<img id="like" style="margin-left: 77%" src="/assets/img/like.png" onclick="changelike()">
	                </c:otherwise>
				</c:choose>
				
				<h4 style="letter-spacing: -3px; margin-bottom: 3%;"> ${dto.cName} </h4>
				<hr style="margin-top: 0; margin-bottom: 0%; width: 380px; height : 2px; background-color : #008080; border : 0;">
				<input type="hidden" name="p_price" id="p_price1" class="p_price" value="${dto.cPrice}">
					<c:choose>
		                <c:when test="${dto.cDown != '0'}"> 
		                	<span style="color: red;"> SALE ${dto.cDown}% OFF</span> <br />
		                	<span style="margin-bottom: 1%; font-size: 2em; font-weight: bolder;">
		                		<fmt:formatNumber value="${dto.cPrice - ((dto.cPrice) * (dto.cDown / 100))}" pattern="###,###,###" />
		                	</span> 원 <br>
		                	<s style="color: grey;"><strong><fmt:formatNumber value="${dto.cPrice}" pattern="###,###,###" /></strong></s> 원
		                </c:when>
		                <c:otherwise> 
							<strong><fmt:formatNumber value="${dto.cPrice}" pattern="###,###,###" /></strong> 원
		                </c:otherwise>
	                </c:choose>
				<hr style="margin-top: 0; width: 380px; height : 1px; background-color : #808080; border : 0; margin-top: 1%;">
	
					<form id="gPut_frm" method="post">
						<div id="btn_group" style="width: 80%; padding: 20px; border-radius: 6px; background-color: #f8f8f8;">
							<input type="hidden" id="cId" name="cId" style="display: none;" value="${dto.cId}">
							<input type="hidden" id="cDown" name="cDown" style="display: none;" value="${dto.cDown}">

							<c:choose>
				                <c:when test="${uinfo.mid != null}"> 
				                	<input type="hidden" id="mId" name="mId" style="display: none;" value="${uinfo.mid}">
				                </c:when>
				                <c:otherwise> 
				                	<input type="hidden" id="mId" name="mId" style="display: none;" value="guest">
				                </c:otherwise>
			                </c:choose>

							<button type=button id="minus"  class="btn btn-sm btn-light down" onclick="javascript:basket.changePNum(1);"> - </button> 
							<input type="text" id="gCount" name="gCount" size="1" maxlength="3" value="1" onkeyup="javascript:basket.changePNum(1);"> 
							<button id="plus" type=button class="btn btn-sm btn-light up" onclick="javascript:basket.changePNum(1);"> + </button>
							<span class="sum" id="" style="float: right; font-size: 1.5em; font-weight: bolder;">
							
							<c:choose>
				                <c:when test="${dto.cDown != '0'}"> 
			                		<fmt:formatNumber value="${dto.cSale}" pattern="###,###,###" /> 원
				                </c:when>
				                <c:otherwise> 
									<fmt:formatNumber value="${dto.cPrice}" pattern="###,###,###" /> 원
				                </c:otherwise>
			                </c:choose>
			                
			                </span>
						</div>	
						
						<button type=button style="margin-top: 2%; margin-left: 5%;" class="btn btn-lg btn-outline-secondary" onclick="cart_ajax(${dto.cDown})"> 장바구니 담기 </button>
						<button type=button style="margin-top: 2%;" class="btn btn-lg btn-primary" onclick="buy_ajax()"> 바로 구매하기 </button>
					</form>
					    
			</div>
		</div>
	</div>
	
	<div class="container" style="margin-top: 5%; padding: 0;">
		<hr id="info" style="margin-left: 10%; margin-bottom: 2%; width: 1000px; height : 2px; background-color : #008080; border : 0;">
		<div class="row">
			<div class="col-sm-8" style="margin-left: 15%;">
				<h4> 
					<a style="text-decoration: none; color: #008080; margin-left: 15%; margin-bottom: 4%; float: left;" href="#info">상세정보</a> 
					<a style="text-decoration: none; color: #008080; margin-right: 20%; margin-bottom: 4%; float: right;" href="#review">리뷰</a>
				</h4>
				<img style="" width="900px" src="/upload/${dto.cPhoto2}"/>
				<c:if test="${dto.cPhoto3 ne 'null'}">
					<img style="" width="900px" src="/upload/${dto.cPhoto3}"/>
				</c:if>
			</div>
		</div>
	</div>
	
	<div class="container" style="margin-top: 5%; padding: 0;">
		<hr id="review" style="margin-left: 10%; margin-bottom: 2%; width: 1000px; height : 2px; background-color : #008080; border : 0;">
		<div class="row">
			<div class="col-sm-8" id="rcount" style="margin-left: 10%;">
				<p style="font-size: 12pt;"> <strong style="font-size: 1.5em; color : #008080; margin-left: 55%">리뷰</strong> (${dto.rCount}) </p> 
			</div>
		</div>
		<hr style="margin-left: 10%; margin-bottom: 0%; width: 1000px; height : 2px; background-color : #008080; border : 0;">
	</div>
	
	<div class="container" style="margin-top: 0%; padding: 0;">
		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-9" style="margin-top: 1%; margin-left: 2%; padding: 0;">
			
			<form id="review">
			
				<c:choose>
	                <c:when test="${uinfo.mid != null}"> 
						<!--  로그인했을 시  -->
						<span style="margin-bottom: 0%; margin-right: 2%; font-size: 12pt; float: left;">${uinfo.mname}님</span>
						<div class="star-rating space-x-4 mx-auto" style="float: left;">
							<input type="radio" id="5-stars" name="rating" value="5" />
					    	<label for="5-stars" class="star">&#9733;</label>
						    <input type="radio" id="4-stars" name="rating" value="4" />
						    <label for="4-stars" class="star">&#9733;</label>
						    <input type="radio" id="3-stars" name="rating" value="3" />
						    <label for="3-stars" class="star">&#9733;</label>
						    <input type="radio" id="2-stars" name="rating" value="2" />
						    <label for="2-stars" class="star">&#9733;</label>
						    <input type="radio" id="1-star" name="rating" value="1" />
						    <label for="1-star" class="star">&#9733;</label>
						</div>
						<br />
						<input type="text" id="rContent" name="rContent" style="margin-top: 1%; width: 90%;"></input>
						<button class="btn btn-sm btn-primary" type="button" style="float: right; margin-top: 0%;" onclick="write_review()">리뷰 작성</button>
						
	                </c:when>
	                <c:otherwise>
	                	<span style="font-size: 9pt; color: grey; margin-left: 41%" > 로그인후 리뷰작성이 가능합니다. </span>
	                </c:otherwise>
	                
                </c:choose>
            </form>
			<hr style="margin-left: 0.5%; margin-top: 2%; margin-bottom: 2%; width: 1000px; height : 2px; background-color : #008080; border : 0;">

				<div id="reviewList">
					<c:forEach items="${review}" var="rDto" varStatus="status">
					<div id="mReview${rDto.rId}">
						<div id="review${rDto.rId}">
							<span style="margin-bottom: 0%; margin-right: 1%; font-size: 12pt; float: left;">${fn:substring(rDto.mId, 0, 4)}****</span>
							
							<c:if test="${rDto.rModi > 0}">
								<span style="margin-bottom: 0%; margin-right: 1%; font-size: 12pt; float: left;"> (수정됨) </span>
							</c:if>
							
							<c:choose>
								<c:when test="${rDto.rScore == 5}">
									<span class="star_rating2" value="${rDto.rScore}">    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a class="on">★</a>
									</span>
								</c:when>
								<c:when test="${rDto.rScore == 4}">
									<span class="star_rating2" value="${rDto.rScore}">    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a style="color: #ccc;">★</a>
									</span>
								</c:when>
								<c:when test="${rDto.rScore == 3}">
									<span class="star_rating2" value="${rDto.rScore}">    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a style="color: #ccc;">★</a>    
										<a style="color: #ccc;">★</a>
									</span>
								</c:when>
								<c:when test="${rDto.rScore == 2}">
									<span class="star_rating2" value="${rDto.rScore}">    
										<a class="on">★</a>    
										<a class="on">★</a>    
										<a style="color: #ccc;">★</a>    
										<a style="color: #ccc;">★</a>    
										<a style="color: #ccc;">★</a>
									</span>
								</c:when>
								<c:when test="${rDto.rScore == 1}">
									<span class="star_rating2" value="${rDto.rScore}">    
										<a class="on">★</a>    
										<a style="color: #ccc;">★</a>    
										<a style="color: #ccc;">★</a>    
										<a style="color: #ccc;">★</a>    
										<a style="color: #ccc;">★</a>
									</span>
								</c:when>
							</c:choose>
							<span style="float: right; font-size: 0.7em; color: grey;"> ${fn:substring(rDto.rDate, 0, 10)}</span>
							<p />
							<span type="text" id="rContent" name="rContent" style="margin-top: 0%; width: 90%;" value="${rDto.rContent}">${rDto.rContent}</span> 
							
							<c:choose>
								<c:when test="${uinfo.mid == rDto.mId}">
									<button class="btn btn-sm btn-outline-danger" type="button" 
											style="float: right; margin-left:1%; margin-top: 0%;" value="${rDto.mId}" onclick="delete_review()">리뷰 삭제</button>
									<button class="btn btn-sm btn-outline-primary" type="button" style="float: right; margin-top: 0%;" onclick="modify_review(${rDto.rId}, ${rDto.rScore})">리뷰 수정</button>	
								</c:when>
							</c:choose>
							 
							<hr />
						</div>
					</div>
					</c:forEach>
				</div>
			
			</div>
			<a style="text-decoration: none; color: #008080; margin-left: 0%; margin-bottom: 4%;" href="#topbar"> ▲ top </a>
			
		</div>
	</div>
	
	
	<!-- Footer-->
    <footer class="py-5" style="margin-top: 15%; height: 10%; background-color: #91d7cd; text-align: center;">
        <div class="container">
            <p class="m-0 text-center text-white" style="vertical-align: middle;">Copyright &copy; KIDULT 2022-07</p>
        </div>
    </footer>

    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>