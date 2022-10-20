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

    <title>마이페이지 : KIDULT</title>

	<script src="http://code.jquery.com/jquery.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수


            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("aZipcode").value = data.zonecode;
            document.getElementById("aMain").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("aSub").focus();
        }
    }).open();
}


function add_address(){
	var queryString = $("#adr_frm").serialize();
	console.log(queryString);
	$.ajax({
		url: '/member/addAdr',
		type: 'POST',
		data: queryString,
		dataType: 'text',
		success: function(json){
			console.log(json); 
			var result = JSON.parse(json);
			if(result.code == "success"){
				alert(result.desc);
				window.location.replace("/member/getList");
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>
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
//문의 내역
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

    <div class="container">
        <div class="row">
            <div class="col-sm-3">
            	<h4 style="margin-top: 10%;">회원 정보</h4>
                <hr style="width: 20%; height: 2px; color: black;">
                <a onclick="pwcheck()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 25px; font-size: 13pt; text-decoration:none;"> 내정보 조회/수정 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <a onclick="change_check()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 비밀번호 변경 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <a onclick="wish()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 찜목록 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <a onclick="myreview()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 나의 리뷰 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <h4 style="margin-top: 10%;">주문 정보</h4>
                <hr style="width: 20%; height: 2px; color: black;">                
                <a onclick="go_cart()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 25px; font-size: 13pt; text-decoration:none;"> 장바구니 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <a onclick="go_mypay()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 주문내역 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <a onclick="userAddress()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt; text-decoration:none;"> 배송지 관리 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <h4 style="margin-top: 10%;">고객센터</h4>
                <hr style="width: 20%; height: 2px; color: black;">
                <a onclick="notice_view()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 25px; font-size: 13pt; text-decoration:none;"> 공지사항 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
                <a onclick="customer_page()" style="text-decoration-line: none; color: inherit;"> 
                    <h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt;"> 1:1 문의 </h5> 
                </a>
                <hr style="width: 70%; color: gray;">
				<a onclick="qna_list()" style="text-decoration-line: none; color: inherit;"> 
					<h5 style="margin-left: 5px; margin-top: 16px; font-size: 13pt;"> 문의 내역 </h5> 
				</a>
                <hr style="width: 70%;">
                <h6 style="margin-top: 5%; font-size: 10pt;">KIDULT 고객센터</h6>
                <h3 style="margin-top: 1%; font-weight: bolder;">1234-5678</h3>
            </div>
            <div class="col-sm-7">
	            <h2 style="font-size: 18pt; margin-top: 5%;"> 배송지 추가
	            </h2> 
	            <hr style="margin-bottom: 30px;">
	            	<form id="adr_frm">
						<input type="hidden" id="mId" name="mId" value="${uinfo.mid}"> <br/>
						배송지 이름 : <input type="text" id="aName" name="aName" placeholder="배송지 이름">&nbsp;&nbsp;&nbsp;
						<input type="radio" id="aPrimary" name="aPrimary" value="1"> 주 배송지<br/>
						수령인 이름 : <input type="text" id="adrName" name="adrName" placeholder="수령인 이름"><br>
						우편 번호 : <input type="text" id="aZipcode" name="aZipcode" onclick="sample6_execDaumPostcode()" placeholder="우편번호">
						<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
						주소 : <input type="text" id="aMain" name="aMain" placeholder="주소"><br>
						상세주소 : <input type="text" id="aSub" name="aSub" placeholder="상세주소">
						<br>
						<button type=button onclick="add_address()">배송지 추가하기</button>
					</form>
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