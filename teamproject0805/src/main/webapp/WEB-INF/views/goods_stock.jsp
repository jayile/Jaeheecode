<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    
    <title>상품 관리 : KIDULT</title>
    
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
function input_goods() {
	if($('#cName').val().length == 0) {
		alert("상품명은 필수 입력 사항입니다.");
		$('#cName').focus();
		return;
	}
	submit_ajax();
}

function submit_ajax() {
	var queryString = $('#goods_form').serialize();
	$.ajax({
		url: 'goods_write',
		type: 'POST',
		data: queryString,
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				alert(result.desc);
				window.location.replace("/");
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>

<script>
function mgoods_do(cId) {
	var cName = $('input[name=modi_cName]').val();
	var cPrice = $('input[name=modi_cPrice]').val();
	var cCount = $('input[name=modi_cCount]').val();
	var cDown = $('input[name=modi_cDown]').val();
	console.log(cId);
	console.log(cName);
	console.log(cPrice);
	console.log(cCount);
	console.log(cDown);
	if(cName.length == 0) {
		alert("내용을 입력해주세요.");
		cName.focus();
		return;
	} else if(cPrice.length == 0) {
		alert("내용을 입력해주세요.");
		cPrice.focus();
		return;
	} else if(cCount.length == 0) {
		alert("내용을 입력해주세요.");
		cCount.focus();
		return;
	}
	
	mgoods_ajax(cId, cName, cPrice, cCount, cDown);
}
function mgoods_ajax(cId, cName, cPrice, cCount, cDown) {
	$.ajax({
		url: '/admin/modify_goods',
		type: 'POST',
		data: { cId : cId,
				cName : cName,
				cPrice : cPrice,
				cCount : cCount,
				cDown : cDown },
		dataType: 'text',
		success: function(json) {
			console.log(json);
			var result = JSON.parse(json);
			if(result.code == "ok") {
				alert(result.desc);
				$('#goodsList').load(location.href+' #goodsList');
			} else {
				alert(result.desc);
			}
		}
	});
}
</script>
<script type="text/javascript">
	function del_goods(cId) {
		console.log("cId : " + cId)
		var let = confirm("상품을 삭제합니까?");
		if ( let == true ) {
			del_goods_ajax(cId);
		} else { }
	}
	
	function del_goods_ajax(cId) {
		$.ajax({
			url: '/admin/delete_goods',
			type: 'POST',
			data: { cId : cId, },
			dataType: 'text',
			success: function(json) {
				console.log(json);
				var result = JSON.parse(json);
				if(result.code == "ok") {
					alert(result.desc);
					$('#goodsList').load(location.href+' #goodsList');
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>
<script>
function modi_goods(cId, cPrice, cCount, cDown, cName) {
	var ret = confirm("상품정보를 수정하시겠습니까?");
	var trTag = event.target.parentElement.parentElement;
	if(ret == true) {
		modi_goods_do(cId, cPrice, cCount, cDown, cName);
	} else {  }
}

function modi_goods_do(cId, cPrice, cCount, cDown, cName) {
	console.log(cId);
	console.log(cName);
	console.log(cPrice);
	console.log(cCount);
	console.log(cDown);
	$('#row'+cId).remove();

	var mGoods_form ="";
	
	mGoods_form +='<div id="row"'+cId+'>';
	mGoods_form +='	<span>';
	mGoods_form +='		<input type=text id="cName" name="modi_cName" value="'+cName+'"></input>';
	mGoods_form +='	</span>';
	mGoods_form +='	<span>';
	mGoods_form +='		<input type=text id="cPrice" name="modi_cPrice" value="'+cPrice+'"></input>';
	mGoods_form +='	</span>';
	mGoods_form +='	<span>';
	mGoods_form +='		<input type=text style="width: 5%" id="cCount" name="modi_cCount" value="'+cCount+'"></input>';
	mGoods_form +='	</span>';
	mGoods_form +='	<span>';
	mGoods_form +='		<input type=text style="width: 5%" id="cDown" name="modi_cDown" value="'+cDown+'"></input>';
	mGoods_form +='	</span>';
	mGoods_form +='	<button type=button style="float:right;" class="btn btn-sm btn-info" onclick="get_goodsList()">취소</button>';
	mGoods_form +='	<button type=button style="float:right;" class="btn btn-sm btn-info" onclick="mgoods_do('+ cId + ')">수정완료</button>';
	mGoods_form +='	<hr style="width: 100%; color: gray;">';
	mGoods_form +='</div>';
	
	$("#mgoods" + cId).replaceWith(mGoods_form);
}

function get_goodsList() {
	$('#goodsList').load(location.href+' #goodsList');
}
</script>
<style>
th, td {
  text-align: center;
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
	            <h2 style="font-size: 18pt; margin-top: 5%;"> 재고 관리 </h2> 
	            <hr style="margin-bottom: 5%;">

			    <table id="tbTag" class="table table-Light">
			    	<thead>
				        <tr>
				            <td>상품번호</td>
				            <td colspan="2">이름</td>
				            <td>가격</td>
				            <td>수량</td>
				            <td>할인율(%)</td>
				            <td>할인가</td>
				        </tr>
				    </thead>
				    <tbody>
				    	<c:choose>
						<c:when test="${list != null}">
							<c:forEach items="${list}" var="dto" varStatus="st">
						    	<tr>
						    		<td>${dto.cId}</td>
						        	<td colspan="2">
						        		<a href="/admin/admin_goods_view?cId=${dto.cId}">${dto.cName}</a>
						        	</td>
						        	<td><fmt:formatNumber value="${dto.cPrice}" pattern="###,###,###" />원</td>
						        	<td>${dto.cCount}</td>
						        	<td>${dto.cDown} %</td>
						        	<td><fmt:formatNumber value="${dto.cSale}" pattern="###,###,###" />원</td>
						        </tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							상품의 재고가 없어요.<br/>
						</c:otherwise>
						</c:choose>  
				    </tbody>
			    </table>

				<!-- 
			    <div id="goodsList">
			        <c:choose>
						<c:when test="${list != null}">
							<c:forEach items="${list}" var="dto" varStatus="st">
							
							<div id="mgoods${dto.cId}">
								<div id="row${dto.cId}">
								
							      <span id="cName" style="margin-left: 0%;">${dto.cName}</span>
							      <span id="cPrice" style="margin-left: 10%; margin-right: 5%;">${dto.cPrice}</span>
							      <span id="cCount" style="margin-left: 10%; margin-right: 5%;">${dto.cCount}</span>
							      <span id="cCount" style="margin-left: 10%; margin-right: 5%;">${dto.cDown}</span>
							      <button type=button class="btn btn-sm btn-info" style="margin-right: 5%; float:right;" 
							      		  onclick="modi_goods(${dto.cId}, ${dto.cPrice}, ${dto.cCount}, ${dto.cDown}, '${dto.cName}')">수정</button>
							      <button type=button class="btn btn-sm btn-info" style="margin-right: 5%; float:right;" 
							      		  onclick="del_goods(${dto.cId})">삭제</button>
							      <hr style="width: 100%; color: gray;">
							    </div>
							</div>
							
							</c:forEach>
						</c:when>
						<c:otherwise>
							상품의 재고가 없어요.<br/>
						</c:otherwise>
					</c:choose>
				</div>
				 -->
				 
				 <div class="container" style="margin-top: 5%;">
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
					                	<a class="page-link" href="/admin/goods_stock?page=1" aria-label="Previous">
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
					            		<a class="page-link" href="/admin/goods_stock?page=${page.curPage - 1}">Previous</a>
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
					            	<a class="page-link" href="/admin/goods_stock?&page=${fEach}">${fEach}</a>
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
					                	<a class="page-link" href="/admin/goods_stock?page=${page.curPage + 1}">Next</a>
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
					                	<a class="page-link" href="/admin/goods_stock?page=${page.totalPage}" aria-label="Next">
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
            
            </div>
        </div>
    </div>

	<!-- Footer-->
    <footer class="py-5" style="margin-top: 30%; height: 10%; background-color: #91d7cd; text-align: center;">
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

