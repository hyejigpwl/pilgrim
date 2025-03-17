<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Header -->
<header id="header" class="alt">
	<h1>
		<strong><a href="index.jsp"><img alt="필그림하우스"
				src="assets/images/pilgrim_logo_w.png"></a></strong>
	</h1>
	<nav id="nav">
		<ul>
			<!-- <li><a href="#none">소개</a>
					<ul class="submenu">
						<li><a>인사말</a></li>
						<li><a>이용안내</a></li>
						<li><a>오시는길</a></li>
					</ul>
				</li>
						<li><a href="#none">시설</a>
							<ul class="submenu">
								<li><a>예배실</a></li>
								<li><a>세미나실</a></li>
								<li><a>기도실</a></li>
								<li><a>객실</a></li>
								<li><a>부대시설</a></li>
							</ul>
						</li>
						<li><a href="#none">사역</a>
							<ul class="submenu">
								<li><a>세미나</a></li>
								<li><a>예배와 기도</a></li>
								<li><a>사역</a></li>
							</ul>
						</li>
						<li><a href="#none" class="point">예약</a>
							<ul class="submenu">
								<li><a>시설 및 사용요금</a></li>
								<li><a href="rvn_rqt.jsp">예약신청</a></li>
								<li><a href="qnaList.qa">예약문의 및 취소</a></li>
								<li><a href="my_page.jsp">나의 예약</a></li>
							</ul>
						</li>
						<li><a href="#none">천로역정</a>
							<ul class="submenu">
								<li><a>순례길 소개</a></li>
								<li><a>천로역정 작품소개</a></li>
								<li><a>순례길 이용안내</a></li>
							</ul>
						</li>
						<li><a href="#none">커뮤니티</a>
							<ul class="submenu">
								<li><a>공지사항</a></li>
								<li><a>이용후기</a></li>
								<li><a>포토갤러리</a></li>
							</ul>
						</li> -->
			<li><a>시설</a></li>
			<li><a href="rvn_rqt.jsp">예약신청</a></li>
			<li><a href="qnaList.qa">이용후기</a></li>
			

			<%
			//세션에서 로그인된 사용자 정보 가져오기
			String member_id = (String) session.getAttribute("member_id");
			if (member_id == null) {
			%>
			<li><a href="my_page.jsp">나의 예약</a></li>
			<li><a href="login.jsp">로그인</a></li>
			<%
			}else if("관리자".equals(member_id)){
			%>
			<li><a href="my_page.jsp">문의 채팅</a></li>
			<li><a href="logout.mb">로그아웃</a></li>
			<%
			}else{
			%>
			<li><a href="my_page.jsp">나의 예약</a></li>
			<li><a href="logout.mb">로그아웃</a></li>
			<%
			}
			%>
			
		</ul>
	</nav>
</header>
<a href="#menu" class="navPanelToggle"><span class="fa fa-bars"></span></a>
