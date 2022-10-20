<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    
    <title>결제 완료</title>
	
	<script src="http://code.jquery.com/jquery.js"></script>

<script>
	// 매장 안내
	function map_info() {
		window.location.replace("map_info");
	}
	// 공지사항
	function notice_view() {
		window.location.replace("/notice/notice_view");
	}
	// 상품 등록
	function goods_form() {
		window.location.replace("/admin/goods_form");
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
		window.location.replace("userlogin");
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
	// 주문정보
	function go_cart() {
		window.location.replace("/goods_cart");
	}
	function userAddress() {
		window.location.replace("/address/getList");
	}
	// 관리자 페이지
	function adminpage() {
		window.location.replace("/admin/adminmain");
	}
	// 상품 재고 관리
	function goods_stock() {
		window.location.replace("/admin/goods_stock");
	}

</script>
<style>
	.test2{ padding-top: 30px;
			padding-bottom: 30px;
			text-align : center; }
	.text1{ font-size: 13pt; }
</style>
</head>
<body>

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

	
	<!-- Section-->
    <section style="padding-top: 0; margin: 0;">
        <div class="container px-4 px-lg-5">
            <div class="justify-content-center">
                <div class="row">
						<div class="col test1"></div>
					
						<div class="col-10 test2">
							<hr style="color: white; padding-top: 30px; padding-bottom: 10px;">
							<h3 style="font-style:bold;">주문이 완료되었습니다!</h3><br/>
							
							<a href="/goods/goods_list">쇼핑 계속하기</a><br/>
                			<a href="/member/go_mypay">구매 내역 보러가기</a><br/>
                			<br/><br/>
                			<div class="row">
                			<hr style="color: white; padding-top: 20px; padding-bottom: 20px;">
                			<hr style="color: gray;">
                			<hr style="color: white; padding-top: 20px; padding-bottom: 20px;">
		        				<div class="col-sm-5" style='margin: auto; text-align:left;'>
		        					<h5 style="font-weight:bold; padding-bottom: 15px;">주문 상품</h5>
		        					<p class="text1">주문 상품명 1</p>
		        					<p class="text1">주문 상품명 2</p>
									<hr style="color: gray;" />
									<p class="text1">총 주문 금액 : 150000 원</p>
		        				</div>
		        				<div class="col-sm-5" style='margin: auto; text-align:left;'>
		        					<h5 style="font-weight:bold; padding-bottom: 15px;">배송지 정보</h5><br/>	<!-- 여기 유저정보 넣으면 안들어감 -->	
		        					<p class="text1">(08532) 서울특별시 금천구 시흥대로 145길 76-2</p>
		        					<p class="text1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(실크로드, 64) 202호</p>
		        				</div>
			        			<hr style="color: white; padding-top: 20px; padding-bottom: 20px;">
			        			<hr style="color: gray;">
			        			<hr style="color: white; padding-top: 20px; padding-bottom: 20px;">
		        			</div>
						</div>
						<div class="col test1"></div>
				</div> 
            </div>
           
        </div>
    </section>
    
    
	<!-- Footer-->
    <footer class="py-5" style="margin-top: 1%; height: 10%; background-color: #91d7cd; text-align: center;">
        <div class="container">
            <p class="m-0 text-center text-white" style="vertical-align: middle;">Copyright &copy; KIDULT 2022-07</p>
        </div>
    </footer>

    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
    <script src="js/scripts.js"></script>
</body>
</html>