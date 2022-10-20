<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script> <!-- 다음 주소찾기 -->
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

	// 우편번호 찾기
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
	
	// 주소 팝업 호출
	function adr_window() {
		var userid = $("#mid").val();
		var urldata = "/adr_popup?mid="+ userid;
		var url = "/adr_popup";
	    var name = "popup";
	    var option = "width = 500, height = 800, top = 100, left = 200, location = no"
	    window.open(urldata, name, option);
	}
</script>
<script src="https://js.bootpay.co.kr/bootpay-4.2.0.min.js" type="application/javascript"></script>
<script>
async function go_pay() {
	var user_id = $("#mid").val();
	var user_name = $("#mname").val();
	var user_pnum = $("#mpnum").val();
	var user_email = $("#memail").val();
	
	var total = $("#total").val();
	
	
	try {
		const response = await Bootpay.requestPayment({
				"application_id": "62ce2babe38c3000235af958",
				"price": total,
				"order_name": "KIDULT",
				"order_id": "ADMIN",
				"pg": "nicepay",
				"method": "card",
				"tax_free": 0,
				"user": {
				  "id": user_id,
				  "username": user_name,
				  "phone": user_pnum,
				  "email": user_email
				},
				"items": [
				  {
				    "id": "item_id",
				    "name": "총합",
				    "qty": 1,
				    "price": total
				  }
				],
				"extra": {
				  "open_type": "iframe",
				  "card_quota": "0,2,3",
				  "escrow": false,
				  "separately_confirmed": true		// 결제 분리 승인 기능
				}
			})
		    switch (response.event) {
		        case 'issued':
		            // 가상계좌 입금 완료 처리
		            break
		        case 'done':
                    console.log(response);
                    // 결제 완료 처리
                    break
		        case 'confirm':
		            console.log(response.receipt_id)
		            // 재고처리등 하는곳
		            const confirmedData = await Bootpay.confirm(); //결제를 승인한다
		            if(confirmedData.event === 'done') {
		            	go_paydb();
		            	//window.location="/paydone?pid="+ordernum;
		            } else if(confirmedData.event === 'error') {
		            	alert("결제 승인 실패");
		            	//결제 승인 실패
		            }
		
		            /**
		             * 2. 서버 승인을 하고자 할때
		             * // requestServerConfirm(); //예시) 서버 승인을 할 수 있도록  API를 호출한다. 서버에서는 재고확인과 로직 검증 후 서버승인을 요청한다.
		             * Bootpay.destroy(); //결제창을 닫는다.
		             */
		            break

	    }
	} catch (e) {
	    // 결제 진행중 오류 발생
	    // e.error_code - 부트페이 오류 코드
	    // e.pg_error_code - PG 오류 코드
	    // e.message - 오류 내용
	    console.log(e.message)
	    switch (e.event) {
            case 'cancel':
                // 사용자가 결제창을 닫을때 호출
                console.log(e.message);
                break
            case 'error':
                // 결제 승인 중 오류 발생시 호출
                console.log(e.error_code);
                break
		}

	}
}
function go_paydb() {
	var name = $("#adrName").val();
	var zipcode = $("#aZipcode").val();
	var main = $("#aMain").val();
	var sub = $("#aSub").val();
	var memo = $("#pdeliverymemo").val();
	
	$.ajax({
		url: '/pay_db',
		type: 'POST',
		data: { adrName : name,
				aZipcode : zipcode,
				aMain : main, 
				aSub : sub,
				pdeliverymemo : memo },
		dataType: 'text',
		success: function(text) {
			console.log(text);
			if(text != "order_error") {
				console.log("최종값:" + text);
				window.location.replace("/paydone?pid="+text);
			} else {
				console.log("결제 실패");
				return;
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
    		<div class="col-sm-1"></div>
    		<div class="col-sm-10" style="margin-top: 5%;">
    		
    			<form name="orderform" id="orderform" method="post" class="orderform" action="/Page" onsubmit="return false;">
		    
		            <input type="hidden" name="cmd" value="order">
		            <div class="basketdiv" id="basket">
		                <div class="row head">
		                    <div class="subdiv">
		                        <div class="img">이미지</div>
		                        <div class="pname">상품명</div>
		                    </div>
		                    <div class="subdiv">
		                        <div class="basketprice">가격</div>
		                        <div class="num">수량</div>
		                        <div class="sum">합계</div>
		                    </div>
		                    <div class="split"></div>
		                </div>
		                
		        		<c:forEach items="${list}" var="dto" varStatus="status">
		        		
		        		<div class="row data">
		                    <div class="subdiv">
		                        <div class="img">
		                        	<img src="/upload/${dto.cPhoto1}" width="30">
		                        </div>
		                        <div class="pname">
		                            <span>${dto.cName}</span>
		                        </div>
		                    </div>
		                    <div class="subdiv">
		                        <div class="basketprice"><input type="hidden" name="p_price" id="p_price${status.count}" class="p_price" value="${dto.cPrice}">${dto.cPrice}원</div>
		                        <div class="num">
		                            <div class="updown">
		                            	${dto.count}
		                            </div>
		                        </div>
		                        <div class="sum">${dto.cPrice * dto.count}원</div>
		                    </div>
		                </div>
		                
		                </c:forEach>
		        
		            </div>
		            
		        
			        <div>
			        	<input type="hidden" id="mid" name="mid" value="${uinfo.mid}">
			        	<input type="hidden" id="mname" name="mname" value="${uinfo.mname}">
			        	<input type="hidden" id="mpnum" name="mpnum" value="${uinfo.mpnum}"> 
			        	<input type="hidden" id="memail" name="memail" value="${uinfo.memail}"> 
			        	<hr/>
			        	주문자 정보 <br/>
			        	
			        	이름 : ${uinfo.mname}<br/>
			        	전화번호 : 
			        	<c:choose>
				        	<c:when test="${uinfo.mpnum != null}"> 
			                	${uinfo.mpnum}<br/>
			                </c:when>
			                <c:otherwise> 
			                	휴대폰 번호를 등록해주세요. <br/>
			                </c:otherwise>
		                </c:choose>
			        </div>
			        <hr/>
			        <div>
			        	배송지 정보<button type="button" class="btn btn-success btn-sm mb-4" onclick="adr_window()">배송지 선택</button><br/><br/>
			        	<form id="adr_frm"> <!-- form 못찾는중 -->
		                	배송지 이름 : <input type="text" id="aName" name="aName" placeholder="배송지 이름" value="${adr.aName}" /> <br/>
		                	수령인 이름 : <input type="text" id="adrName" name="adrName" placeholder="배송지 이름" value="${adr.adrName}" /> <br/>
							우편 번호 : <input type="text" id="aZipcode" name="aZipcode" placeholder="우편번호" value="${adr.aZipcode}" /> <br/>
							주소 : <input type="text" id="aMain" name="aMain" placeholder="주소" value="${adr.aMain}" /><br>
							상세주소 : <input type="text" id="aSub" name="aSub" placeholder="상세주소" value="${adr.aSub}" /><br/>
							<input type="text" id="pdeliverymemo" name="pdeliverymemo" placeholder="요청사항을 입력해주세요.">
							<br>
						</form>
		                <hr/>
			        </div>
	    			<div>
	    				총 결제 금액 <br/>
	    				<input type="hidden" id="total" name="total" value="${total}"> 
	    				상품 가격 : ${total} 원<br/>
		                <hr/>
	    			</div>
	    			<div>
	    				<!-- 임시임 결제로 다시 연결해둘것 -->
	    				<button type=button onclick="go_pay()">결제하기</button>
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
