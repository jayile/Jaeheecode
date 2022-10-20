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

    <title>회원 목록 : KIDULT</title>

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

</script>
<style>
	.info { font-size : 140%; font-weight: lighter; margin-top: 5px; }
	.tt{ font-weight: bold; }
	.test2{ padding-top: 30px; }
	.test3{ padding-top: 10px; }
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
	            <h2 style="font-size: 18pt; margin-top: 5%;"> 회원 목록
	            </h2> 
	            <hr style="margin-bottom: 20px;">
	            	<div class="row">
						<div class="col test1"></div>
						
						<div class="col-10 test2">
							<table class="table table table-hover">
								<thead class="thead-light">
									<tr>
										<th scope="col">ID</th>
										<th scope="col">회원 등급</th>
										<th scope="col">블랙 여부</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="dto">
									<tr>
										<td>
											<a href="/admin/userview?mid=${dto.mid}">${dto.mid}</a>
										</td>
										<td>
											<c:choose>
												<c:when test="${dto.authority == 'ROLE_ADMIN'}"> 
													관리자
												</c:when>
												<c:otherwise> 
													일반회원
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${dto.mblack == 0}"> 
													정상
												</c:when>
												<c:when test="${dto.mblack == 1}"> 
													로그인 정지
												</c:when>
												<c:when test="${dto.mblack == 2}"> 
													결제 정지
												</c:when>
											</c:choose>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="col test1"></div>
					</div>
					
					<!-- 페이징 -->
					<div class="container">
					<div class="row">
						<div class="col-sm-12">
			
						<nav aria-label="Page navigation example">
							<ul class="pagination justify-content-center">
								<!-- 처음 -->
					        	<c:choose>
								<c:when test="${(page.curPage - 1) < 1}">
					            	<li class="page-item">
					                	<a class="page-link" aria-label="Previous">
					                  		<span aria-hidden="true">&laquo;</span>
					                	</a>
					            	</li>
					            </c:when>
					            <c:otherwise>
					            	<li class="page-item">
					                	<a class="page-link" href="/admin/getlist?page=1" aria-label="Previous">
					                  		<span aria-hidden="true">&laquo;</span>
					                	</a>
					            	</li>
								</c:otherwise>
								</c:choose>
					  
					         	<!-- 이전 -->
								<c:choose>
								<c:when test="${(page.curPage - 1) < 1}">
									<li class="page-item">
					            		<a class="page-link">Previous</a>
					            	</li>
								</c:when>
								<c:otherwise>
									<li class="page-item">
					            		<a class="page-link" href="/admin/getlist?page=${page.curPage - 1}">Previous</a>
					            	</li>
								</c:otherwise>
								</c:choose>
					            
					            
					            <!-- 개별페이지 -->
					            <c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
									<c:choose>
									<c:when test="${page.curPage == fEach}">
										 <li class="page-item">
										  	<a class="page-link">${fEach}</a>
										 </li>
									</c:when>
										<c:otherwise>
					            <li class="page-item">
					            	<a class="page-link" href="/admin/getlist?&page=${fEach}">${fEach}</a>
					            </li>
					            </c:otherwise>
									</c:choose>
								</c:forEach>
					
					            <!-- 다음 -->
								<c:choose>
								<c:when test="${(page.curPage + 1) > page.totalPage}">
									<li class="page-item">
					                	<a class="page-link">Next</a>
					            	</li>
								</c:when>
								<c:otherwise>
									<li class="page-item">
					                	<a class="page-link" href="/admin/getlist?page=${page.curPage + 1}">Next</a>
					            	</li>
								</c:otherwise>
								</c:choose>
								
					            <!-- 끝 -->
								<c:choose>
								<c:when test="${page.curPage == page.totalPage}">
									<li class="page-item">
					                	<a class="page-link" aria-label="Next">
					                  	<span aria-hidden="true">&raquo;</span>
					                	</a>
					            	</li>
								</c:when>
								<c:otherwise>
									<li class="page-item">
					                	<a class="page-link" href="/admin/getlist?page=${page.totalPage}" aria-label="Next">
					                  	<span aria-hidden="true">&raquo;</span>
					                	</a>
					            	</li>
								</c:otherwise>
								</c:choose>
					
					        </ul>
					    </nav>
						
						</div>
					</div>
				</div>
				 <!-- 검색창 -->
				<div class="row">
					<div class="col test1"></div>
					
					<div class="col-10 test3">
						<form action="/admin/searchUser" method="post">
							<input class="search" type="text" id="key" name="key" placeholder="아이디로 검색">
							<button type="submit" class="btn btn-secondary" id="sbtn"> 검색 </button>
						</form> 
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