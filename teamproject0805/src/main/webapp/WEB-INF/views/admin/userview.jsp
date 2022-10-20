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

    <title>회원 상세정보 : KIDULT</title>

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


//팝업 호출
function black_window() {
	var userid = $("#mid").val();
	var urldata = "/admin/b_popup?mid="+ userid;
	var url = "/admin/b_popup";
    var name = "popup test";
    var option = "width = 800, height = 500, top = 100, left = 200, location = no"
    window.open(urldata, name, option);
}
</script>
<style>
	.info { font-size : 140%; font-weight: lighter; margin-top: 5px; }
	.tt{ font-weight: bold; }
	.test2{ padding-top: 80px; }
	.text1{ font-size: 15pt;
			margin-top: 10%; }
	.hr1 { margin-top: 10%;
		   margin-bottom: 10%; }
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
		                <a class="dropdown-item" onclick="goods_list()">전자게임</a>
		                <a class="dropdown-item" href="#">교육완구</a>
		                <a class="dropdown-item" href="#">인형</a>
	                </div>
	            </li>
            </ul>
        </div>
    </nav>
    <!-- 중간 메뉴바 끝 -->

    <div class="container">
        <div class="row">
            <div class="col-sm-3">
            	<h4 style="margin-top: 10%;">회원 관리</h4>
				<hr style="width: 20%; height: 2px; color: black;">
				<a onclick="userlist()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 25px; font-size: 13pt; text-decoration:none;"> 회원 목록 </h5> 
				</a>
				<hr style="width: 70%; color: gray;">
				<a onclick="blacklist()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 블랙리스트 관리 </h5> 
				</a>
				<hr style="width: 70%; color: gray;">
				<a onclick="paylist()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 주문내역 관리 </h5> 
				</a>
				<hr style="width: 70%; color: gray;">
				<h4 style="margin-top: 10%;">상품 관리</h4>
				<hr style="width: 20%; height: 2px; color: black;">                
				<a onclick="goods_form()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 25px; font-size: 13pt; text-decoration:none;"> 상품 추가 </h5> 
				</a>
				<hr style="width: 70%; color: gray;">
				<a onclick="goods_stock()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 상품 내용 관리 </h5> 
				</a>
				<hr style="width: 70%; color: gray;">
				<h4 style="margin-top: 10%;">고객 안내</h4>
				<hr style="width: 20%; height: 2px; color: black;">
				<a onclick="notice_view()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 25px; font-size: 13pt; text-decoration:none;"> 공지사항 </h5> 
				</a>
				<hr style="width: 70%; color: gray;">
				<a onclick="qna_list()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt;"> 문의 확인 및 답변 </h5> 
				</a>
				<hr style="width: 70%;">
				<h6 style="margin-top: 5%; font-size: 10pt;">KIDULT 고객센터</h6>
				<h3 style="margin-top: 1%; font-weight: bolder;">1234-5678</h3>
            </div>
            <div class="col-sm-7">
	            <h2 style="font-size: 18pt; margin-top: 5%;"> 회원 상세정보
	            </h2> 
	            <hr style="margin-bottom: 20px;">
	            	<div class="row">
						<div class="col test1"></div>
					
						<div class="col-10 test2">
							<h4 class="text1">계정 정보</h4>
							<hr />
							<form id="modi_frm">
								<div class="form-group">
									<label for="mid" class="tt">회원 ID</label>
									<p class="info">${ulist.mid}<input type="hidden" id="mid" name="mid" value="${ulist.mid}"></p>
								</div>
								<hr>
								<div class="form-group">
									<label for="mname" class="tt">회원 이름</label>
									<p class="info">${ulist.mname}</p>
								</div>
								<hr>
								<div class="form-group">
									<label for="memail" class="tt">Email address</label>
									<p class="info">
										${ulist.memail}
										<c:if test="${ulist.memail == null}"> 
						                	입력된 내용이 없습니다.
						                </c:if>
									</p>
								</div>
								<hr>
								<div class="form-group">
									<label for="mpnum" class="tt">전화번호</label>
									<p class="info">
										${ulist.mpnum}
										<c:if test="${ulist.mpnum == null}"> 
						                	입력된 내용이 없습니다.
						                </c:if>
									</p>
								</div>
								<hr>
								<div class="form-group">
									<label for="mpnum" class="tt">제재 여부</label>
									<p class="info">
										<c:choose>
							                <c:when test="${blist.mid != null}"> 
							                	<span style="color:red;font-style:italic;">
							                		<c:choose>
														<c:when test="${blist.btype == 1}"> 
														로그인 정지 상태
														</c:when>
														<c:when test="${blist.btype == 2}"> 
														결제 정지 상태
														</c:when>
													</c:choose>
							                	</span>
							                </c:when>
							                <c:otherwise> 
							                	정상 회원
							                </c:otherwise>
						                </c:choose>
									</p>
								</div>
								<c:choose>
									<c:when test="${blist.bmemo != null}"> 
									<hr>
									<div class="form-group">
										<label for="mpnum" class="tt">제재 사유</label>
										<p class="info">
											${blist.bmemo}
										</p>
									</div>
									</c:when>
								</c:choose>
								<p></p>
								<hr style="margin-bottom: 30px;">
								<button type="button" class="btn btn-danger mb-4" onclick="black_window()">블랙리스트 설정</button><br/>
							</form>
							
							<!-- 유저 구매 목록 -->
							<h4 class="text1">구매 목록</h4>
							<hr/>
							<table class="table table table-hover">
								<thead class="thead-light">
									<tr>
										<th scope="col"></th>
										<th scope="col">상품 이름</th>
										<th scope="col">구매 날짜</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${plist}" var="odto">
									<tr>
										
											<td><img width="100px;" src="/upload/${odto.cPhoto1}" /></td>
											<td><a href="goods_view?cId=${odto.cId}">${odto.cName}</a></td>
											<td>${fn:substring(odto.pDate, 0, 10)}</td>
										
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 유저 구매 목록 -->
							<h4 class="text1">리뷰 목록</h4>
							<hr />
							<c:choose>
								<c:when test="${rlist != null}">
									<table class="table table table-hover">
										<thead class="thead-light">
											<tr>
												<th scope="col">남긴 리뷰</th>
												<th scope="col">일시</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${rlist}" var="rdto" varStatus="st">
											<tr>
												<td><a href="/goods/goods_view?cId=${rdto.cId}">${fn:substring(rdto.rcontent, 0, 20)} ... </a></td>
												<td>${fn:substring(rdto.rdate, 0, 10)}</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:when>
								<c:otherwise>
								고객님이 남기신 리뷰가 없어요.<br/>
								</c:otherwise>
							</c:choose>
							
						</div>
						<div class="col test1"></div>
					</div>
					 
            </div>
        </div>
    </div>
    
	<!-- Footer-->
    <footer class="py-5" style="margin-top: 8%; height: 10%; background-color: #91d7cd; text-align: center;">
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