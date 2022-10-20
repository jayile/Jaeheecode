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
    <!-- Template Main CSS File -->
    <link href="/assets/css/style.css" rel="stylesheet">

    <title>마이페이지 : KIDULT</title>

	<script src="http://code.jquery.com/jquery.js"></script>

<script>
	// 매장 안내
	function map_info() {
		window.location.replace("/map_info");
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
	function goods_list() {
		window.location.replace("/goods/goods_list");
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
	// 주문정보
	function go_cart() {
		window.location.replace("/goods_cart");
	}
	function userAddress() {
		window.location.replace("/address/getList");
	}
</script>
</head>
<body>
    
		<!-- ======= Top Bar ======= -->
    <section id="topbar" class="d-flex align-items-center">
        <div class="container d-flex justify-content-center justify-content-md-between">
            <div class="contact-info d-flex align-items-center" id="topbarcon">
                <a onclick="map_info()" id="topbarcon">매장안내</a> &nbsp;&nbsp;
                <a onclick="join()" id="topbarcon">회원가입</a> &nbsp;&nbsp;
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
                
                <c:if test="${uinfo.authority == 'ROLE_ADMIN'}"> 
                	<a onclick="goods_form()" id="topbarcon">상품관리</a> &nbsp;
                </c:if>
            	
                <a onclick="notice_view()" id="topbarcon">고객센터</a>
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
                        <li><a onclick="wish()"><img src="/assets/img/like.png"><span>찜목록</span></a></li>
                        <li><a onclick="userpage()"><img src="/assets/img/account.png"><span>마이페이지</span></a></li>
                        <li><a onclick="cart()"><img src="/assets/img/cart.png"><span>장바구니</span></a></li>
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
		                <a class="dropdown-item" onclick="goods_list()">전자게임</a>
		                <a class="dropdown-item" href="#">교육완구</a>
		                <a class="dropdown-item" href="#">인형</a>
	                </div>
	            </li>
            </ul>
        </div>
    </nav>
    <!-- 중간 메뉴바 끝 -->

    
</body>
</html>