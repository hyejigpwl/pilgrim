<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="robots"
	content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/sub.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">
<!-- Swiper.js 라이브러리 추가 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<script
	src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<script src="assets/js/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title>시설</title>
<style>
	.slider-container {
	max-width:400px;
    height: 250px;
}
.swiper {
    width: 100%;
    height: 100%;
}

.swiper-slide img {
    width: 100%;
    height: 100%;
    object-fit: cover; /* 이미지 비율 유지 */
    border-radius: 10px;
}
.flex_wrap{
	display:flex;
	justify-content:space-between;
}

.flex_wrap:first-of-type{
	padding-bottom:50px;
	border-bottom:1px solid #ddd;
}
@media screen and (max-width:768px){
	.flex_wrap{
		display:flex;
		flex-direction:column-reverse;
		gap:30px;
	}
}
	
</style>
</head>
<%@include file="header.jsp"%>

<div class="sub_top">
	<h3>객실 및 시설</h3>
</div>

<div class="container">
	<h3>객실 정보</h3>
	<div class="flex_wrap">
	<ul class="room-list">
		<li>❖ 2인온돌 (70,000원) - <span class="available">총 20실</span></li>
		<li>❖ 2인침대 (80,000원) - <span class="available">총 20실</span></li>
		<li>❖ 4인침대 (150,000원) - <span class="available">총 40실</span></li>
		<li>❖ VIP룸 (150,000원) - <span class="available">총 10실</span></li>
		<li>❖ 빌리지 가족실 (150,000원) - <span class="available">총 30실</span></li>
	</ul>

	<div class="slider-container">
		<div class="swiper mySwiper1">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="assets/images/room_1.png" alt="객실 이미지 1">
				</div>
				<div class="swiper-slide">
					<img src="assets/images/room_2.png" alt="객실 이미지 2">
				</div>
				<div class="swiper-slide">
					<img src="assets/images/room_3.png" alt="객실 이미지 3">
				</div>
				<div class="swiper-slide">
					<img src="assets/images/room_4.jpg" alt="객실 이미지 4">
				</div>
			</div>
			<!-- 좌우 네비게이션 버튼 -->
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
			<!-- 페이지네이션 -->
			<div class="swiper-pagination"></div>
		</div>
	</div>
	</div>

	<h3 style="margin-top: 50px;">시설 정보</h3>
	<div class="flex_wrap">
	<ul class="facility-list">
		<li>❖ 빌리지 채플 (200,000원) - <span class="available">총 2실</span></li>
		<li>❖ 소그룹 세미나실 (50,000원) - <span class="available">총 10실</span></li>
		<li>❖ 카리타스 채플 (500,000원) - <span class="available">총 1실</span></li>
	</ul>
		<div class="slider-container">
		<div class="swiper mySwiper2">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="assets/images/fac1.jpg" alt="객실 이미지 1">
				</div>
				<div class="swiper-slide">
					<img src="assets/images/fac2.jpg" alt="객실 이미지 2">
				</div>
				<div class="swiper-slide">
					<img src="assets/images/fac3.jpg" alt="객실 이미지 3">
				</div>
				<div class="swiper-slide">
					<img src="assets/images/fac4.jpg" alt="객실 이미지 4">
				</div>
			</div>
			<!-- 좌우 네비게이션 버튼 -->
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
			<!-- 페이지네이션 -->
			<div class="swiper-pagination"></div>
		</div>
	</div>
	</div>
</div>

<%@include file="footer.jsp"%>
<script>
var swiper = new Swiper(".mySwiper1", {
  loop: true, // 무한 루프
  autoplay: {
    delay: 3000, // 3초마다 자동 슬라이드
    disableOnInteraction: false, // 사용자 터치 후에도 자동 슬라이드 유지
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
});

var swiper = new Swiper(".mySwiper2", {
	  loop: true, // 무한 루프
	  autoplay: {
	    delay: 3000, // 3초마다 자동 슬라이드
	    disableOnInteraction: false, // 사용자 터치 후에도 자동 슬라이드 유지
	  },
	  navigation: {
	    nextEl: ".swiper-button-next",
	    prevEl: ".swiper-button-prev",
	  },
	  pagination: {
	    el: ".swiper-pagination",
	    clickable: true,
	  },
	});
</script>

</html>
