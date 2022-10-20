<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.util.Date" %>
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
	
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" 
          integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" 
          crossorigin="anonymous">
	<link rel="stylesheet" href="/assets/css/10-11.css" />
	<script type="text/javascript" src="/assets/js/10-11.js"></script>  
	<script src="http://code.jquery.com/jquery.js"></script>
<title>장바구니 : KIDULT</title>

<script>
	$(document).ready(function () {
		basket.reCalc();
		basket.updateUI();
	});

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
	// 관리자 페이지
	function adminpage() {
		window.location.replace("/admin/adminmain");
	}
	// 상품 재고 관리
	function goods_stock() {
		window.location.replace("/admin/goods_stock");
	}

</script>

</head>
<body>

<script>
// 상품 개별 삭제
function cart_delete() {
	let = confirm("상품을 삭제하시겠습니까?");
	if(let == true) {
		cart_delete_ajax();
	} else { }	
}

function cart_delete_ajax() {
	var goods = event.target.parentElement.previousElementSibling.getAttribute('value');
	var delItem = event.target.parentElement.parentElement.parentElement;
	$.ajax({
		url: '/goods/cart_delete',
		type: 'POST',
		data: { cId : goods },
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				delItem.remove();
				basket.reCalc();
				basket.updateUI();
			} else {
				alert(result.desc);
			}
		}
	});
}

</script>
<script>

function cart_Alldelete() {
	let = confirm("상품을 삭제하시겠습니까?");
	if(let == true) {
		cart_Alldelete_ajax();
	} else {

	}	
}

function cart_Alldelete_ajax() {
	$.ajax({
		url: '/goods/cart_Alldelete',
		type: 'POST',
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				document.querySelectorAll('.row.data').forEach(function (item) {
		            item.remove();
		          });
				basket.reCalc();
				basket.updateUI();
			} else {
				alert(result.desc);
			}
		}
	});
}

</script>
<script>

function check () {
	$(function() {
		var box = $("input[name=buy]:checked");
		console.log(box);
	});
}
	
function go_pay () {
	window.location.replace("/pay_page");
}
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
    
    <div class="container">
    	<div class="row">
    		<div class="col-sm-1"></div>
    		<div class="col-sm-10" style="margin-top: 5%;">
    		
    			<form name="orderform" id="orderform" method="post" class="orderform" action="/Page" onsubmit="return false;">
		    
		            <input type="hidden" name="cmd" value="order">
		            <div class="basketdiv" id="basket">
		                <div class="row head">
		                    <div class="subdiv">
		                        <div class="check">선택</div>
		                        <div class="img">이미지</div>
		                        <div class="pname">상품명</div>
		                    </div>
		                    <div class="subdiv">
		                        <div class="basketprice">가격</div>
		                        <div class="num">수량</div>
		                        <div class="sum">합계</div>
		                    </div>
		                    <div class="subdiv">
		    
		                        <div class="basketcmd">삭제</div>
		                    </div>
		                    <div class="split"></div>
		                </div>
		                
		        		<c:forEach items="${list}" var="dto" varStatus="status">
		        		
		        		<div class="row data">
		                    <div class="subdiv">
		                        <div class="check">
		                        	<input type="checkbox" name="buy" value="260" checked="" onclick="javascript:basket.checkItem();">&nbsp;
		                        </div>
		                        <div class="img">
		                        	<img src="/upload/${dto.cPhoto1}" width="30">
		                        </div>
		                        <div class="pname">
		                            <span>${dto.cName}</span>
		                        </div>
		                    </div>
		                    <div class="subdiv">
		                        <div class="basketprice">
		                        	<input type="hidden" name="p_price" id="p_price${status.count}" class="p_price" value="${dto.cPrice}">
		                        		<fmt:formatNumber value="${dto.cPrice}" pattern="###,###,###" />원</div>
		                        <div class="num">
		                            <div class="updown">
		                                <span onclick="javascript:basket.changePNum(${status.count});"><i class="fas fa-arrow-alt-circle-up up"></i></span>
		                                <input type="text" name="p_num${status.count}" id="p_num${status.count}" style="margin-top:0;"
		                                	   size="1" maxlength="3" class="p_num" value="${dto.count}" onkeyup="javascript:basket.changePNum(${status.count});">
		                                <span onclick="javascript:basket.changePNum(${status.count});"><i class="fas fa-arrow-alt-circle-down down"></i></span>
		                            </div>
		                        </div>
		                        <div class="sum">
		                        	<c:choose>
					                <c:when test="${dto.cDown != '0'}"> 
					                	<strong><fmt:formatNumber value="${(dto.cPrice - ((dto.cPrice) * (dto.cDown / 100))) * dto.count}" pattern="###,###,###" /></strong> 원 <br>
					                </c:when>
					                <c:otherwise> 
										<fmt:formatNumber value="${dto.cPrice * dto.count}" pattern="###,###,###" />원
					                </c:otherwise>
				                </c:choose>
		                        	
		                        </div>
		                    </div>
		                    <div class="subdiv">
		                    	<input type="hidden" id="cId" name="cId" style="display: none;" value="${dto.cId}">
		                        <div class="basketcmd">
		                        	<a href="javascript:void(0)" class="abutton" onclick="cart_delete();">삭제</a>
		                        </div>
		                    </div>
		                </div>
		                
		                </c:forEach>
		        
		            </div>
		    
		            <div class="right-align basketrowcmd">
		            	<a href="javascript:void(0)" class="abutton" onclick="check();">선택상품삭제</a>
		                <!-- <a href="javascript:void(0)" class="abutton" onclick="javascript:basket.delCheckedItem();">선택상품삭제</a> -->
		                <!-- <a href="javascript:void(0)" class="abutton" onclick="javascript:basket.delAllItem();">장바구니비우기</a> -->
		                <a href="javascript:void(0)" class="abutton" onclick="cart_Alldelete();">장바구니비우기</a>
		            </div>
		    		
		            <div class="bigtext right-align sumcount" id="sum_p_num">상품갯수: 0개</div>
		            <div class="bigtext right-align box blue summoney" id="sum_p_price">합계금액: 0원</div>
		    		
		            <div id="goorder" class="">
		                <div class="clear"></div>
		                <div class="buttongroup center-align cmd">
		                    <a href="javascript:void(0);" class="abutton" onclick="go_pay();">선택한 상품 주문</a>
		                </div>
		            </div>
		            
		        </form>
    		
    		</div>
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