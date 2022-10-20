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
    <script src="http://code.jquery.com/jquery.js"></script>
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

	<title>상품 리스트 : KIDULT</title>

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
//전체 상품 목록
function allgoods_list() {
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
	window.location.replace("/member/getList");
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

    <style>
        li {
            display: inline-block;
            vertical-align: middle;
            margin-bottom: 0;
        }
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
		                <a class="dropdown-item" onclick="allgoods_list()">전체 보기</a>
		                <a class="dropdown-item" onclick="goods_list('전자게임')">전자게임</a>
		                <a class="dropdown-item" onclick="goods_list('교육완구')">교육완구</a>
	                </div>
	            </li>
            </ul>
        </div>
    </nav>
    <!-- 중간 메뉴바 끝 -->
    
    <p style="margin-left: 16%; font-size: 5pt;"> </p>
    <h3 style="text-align: center; margin-top: 2%;"><strong>검색 결과</strong></h3>

    <div class="container">
        <div class="row">

            <div class="col-sm-6">
                <ul style="list-style: none; margin-top: 5%;">
                	<li>
                        <label>
                            <input type="radio" name="sortProduct" checked>
                            <span>최신순</span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="sortProduct">
                            <span>인기순</span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="sortProduct">
                            <span>낮은 가격순</span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="sortProduct">
                            <span>높은 가격순</span>
                        </label>
                    </li>
                </ul>
            </div>
            
        </div>
    </div>

    <!-- Section-->
    <section style="padding-top: 0; margin: 0;">
        <div class="container px-4 px-lg-5">
            <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                
                <c:forEach items="${list}" var="dto">
                
                <div class="col mb-5">
                    <div class="card h-100">
                        <!-- Product image-->
                        <c:choose>
			                <c:when test="${dto.cDown != '0'}"> 
			                	<div class="mbox" style="position: relative">
			                		<a href="/goods/goods_view?cId=${dto.cId}">
			                			<img class="card-img-top" width="150px;" src="/upload/${dto.cPhoto1}" />
			                			<img style="position: absolute; left: 10px; top: 10px;" width="35%;" src="/assets/img/sale.png" />			                					                		
			                		</a>
			                	</div>
			                </c:when>
			                <c:otherwise>
								<a href="/goods/goods_view?cId=${dto.cId}"> <img class="card-img-top" width="150px;" src="/upload/${dto.cPhoto1}" /> </a>
			                </c:otherwise>
		                </c:choose>
                        <!-- Product details-->
                        <div class="card-body p-4">
                            <div class="text-center">
                                <!-- Product name-->
                                <c:set var="now" value="<%=new java.util.Date()%>" /><!-- 현재시간 -->
						        <fmt:formatDate value="${now}" pattern="yyMMdd" var="today" /><!-- 현재시간을 숫자로 -->
							    <fmt:formatDate  value="${dto.gDate}" pattern="yyMMdd" var="dtoDate" /><!-- 게시글 작성날짜를 숫자로 -->
						        <c:if test="${today - dtoDate le 30}"><!-- 30일동안은 new 표시 -->
						            <span class="badge bg-primary">신상품</span>
						        </c:if>
                                <h5 class="fw-bolder">${dto.cName}</h5>
                                <!-- Product price-->
                                <c:choose>
					                <c:when test="${dto.cDown != '0'}"> 
					                	<span style="color: red;"> SALE ${dto.cDown}% OFF</span> <br />
					                	<strong><fmt:formatNumber value="${dto.cPrice - ((dto.cPrice) * (dto.cDown / 100))}" pattern="###,###,###" /></strong> 원 <br>
					                	<s style="color: grey;"><strong><fmt:formatNumber value="${dto.cPrice}" pattern="###,###,###" /></strong></s> 원
					                </c:when>
					                <c:otherwise> 
										<strong><fmt:formatNumber value="${dto.cPrice}" pattern="###,###,###" /></strong> 원
					                </c:otherwise>
				                </c:choose>
				                
                            </div>
                        </div>
                        <!-- Product actions -->
                        <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                            <span style="font-size: 9pt; float: right;"> 리뷰 &nbsp; ${dto.rCount} 건</span>
                        </div> 
                    </div>                
                </div>
                
                </c:forEach>
            </div>
        </div>
    </section>
    
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
			
<nav aria-label="Page navigation example">
	<ul class="pagination justify-content-center">
		
		<!--처음-->
           <li class="page-item">
           <c:choose>
               <c:when test="${(page.curPage-1)<1}">
               	<c:if test="${searchword != null}">
                   <span class="page-link" href="goods_search?page=1&word=${searchword}">&laquo;</span>
                   </c:if>
                   <c:if test="${searchword == null}">
                   <span class="page-link" href="goods_search?page=1">&laquo;</span>
                   </c:if>
               </c:when>
               <c:otherwise>
               	<c:if test="${searchword != null}">
                   <a class="page-link" href="goods_search?page=1&word=${searchword}">&laquo;</a>
                   </c:if>
                   <c:if test="${searchword == null}">
                   <a class="page-link" href="goods_search?page=1">&laquo;</a>
                   </c:if>
               </c:otherwise>
           </c:choose>
           </li>
 
        	<!--이전-->
           <li class="page-item">
           <c:choose>
               <c:when test="${(page.curPage-1) < 1}">
               	<c:if test="${searchword != null}">
                   <span class="page-link" href="goods_search?page=${page.curPage-1}&word=${searchword}">Previous</span>
               	</c:if>
               	<c:if test="${searchword == null}">
               	<span class="page-link" href="goods_search?page=${page.curPage-1}">Previous</span>
                   </c:if>
               </c:when>
               <c:otherwise>
             		<c:if test="${searchword != null}">
                   <a class="page-link" href="goods_search?page=${page.curPage-1}&word=${searchword}">Previous</a>
                   </c:if>
                   <c:if test="${searchword == null}">
                   <a class="page-link" href="goods_search?page=${page.curPage-1}">Previous</a>
                   </c:if>
               </c:otherwise>
           </c:choose>
		</li>
           
           
           <!--개별 페이지-->
           <c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
               <c:choose>
                   <c:when test="${page.curPage ==fEach}">
           
                   	<li class="page-item active"><a class="page-link" href="#">${fEach}</a></li>

                   </c:when>

                   <c:otherwise>
                   	<c:if test="${searchword != null}">
                       <li class="page-item"><a class="page-link" href="goods_search?page=${fEach}&word=${searchword}">${fEach}</a></li>
                   	</c:if>
                   	<c:if test="${searchword == null}">
                   	<li class="page-item"><a class="page-link" href="goods_search?page=${fEach}">${fEach}</a></li>
                   	</c:if>
                   </c:otherwise>
               </c:choose>
           </c:forEach>

           <!--다음-->
            <li class="page-item">
            <c:choose>
                <c:when test="${(page.curPage +1) > page.totalPage}">
                	<c:if test="${searchword != null}">
                    <span class="page-link" href="goods_search?page=${page.curPage+1}&word=${searchword}">Next</span>
                    </c:if>
                    <c:if test="${searchword == null}">
                    <span class="page-link" href="goods_search?page=${page.curPage+1}">Next</span>
                    </c:if>
                </c:when>
                <c:otherwise>
                	<c:if test="${searchword != null}">
                    <a class="page-link" href="goods_search?page=${page.curPage+1}&word=${searchword}">Next</a>
                    </c:if>
                    <c:if test="${searchword == null}">
                    <a class="page-link" href="goods_search?page=${page.curPage+1}">Next</a>
                    </c:if>
                </c:otherwise>
            </c:choose>
			</li>
		
           <!--끝-->
            <li class="page-item">
            <c:choose>
                <c:when test="${page.curPage == page.totalPage}">
                	<c:if test="${searchword != null}">
                    <span class="page-link" href="goods_search?page=${page.totalPage}&word=${searchword}">&raquo;</span>
                    </c:if>
                    <c:if test="${searchword == null}">
                    <span class="page-link" href="goods_search?page=${page.totalPage}">&raquo;</span>
                    </c:if>
                </c:when>
                <c:otherwise>
                	<c:if test="${searchword != null}">
                    <a class="page-link" href="goods_search?page=${page.totalPage}&word=${searchword}">&raquo;</a>
                    </c:if>
                    <c:if test="${searchword == null}">
                    <a class="page-link" href="goods_search?page=${page.totalPage}">&raquo;</a>
                    </c:if>
                </c:otherwise>
            </c:choose>
            </li>
       </ul>
   </nav>
    
			</div>
		</div>
	</div>

	<!-- Footer-->
    <footer class="py-5" style="margin-top: 10%; height: 10%; background-color: #91d7cd; text-align: center;">
        <div class="container">
            <p class="m-0 text-center text-white" style="vertical-align: middle;">Copyright &copy; KIDULT 2022-07</p>
        </div>
    </footer>

    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
    <script src="js/scripts.js"></script>
    
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" 
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" 
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>