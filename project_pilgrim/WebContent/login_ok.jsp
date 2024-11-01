<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lec.member.dao.MemberDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="robots" content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/sub.css">
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%
	String member_id = request.getParameter("member_id");
    String pwd = request.getParameter("pwd");

    if (member_id != null && pwd != null) {
        // 데이터베이스에서 사용자 확인
        MemberDAO memberDao = MemberDAO.getInstance();
        String name = memberDao.isMember(member_id, pwd);

        if (name != null) {  // 로그인 성공
            session.setAttribute("member", "ok");
            session.setAttribute("member_id", member_id);
            session.setAttribute("pwd", pwd);
            session.setAttribute("name", name);
           
            // 이전 페이지로 리디렉트
            String previousPage = (String) session.getAttribute("previousPage");
            if (previousPage != null) {
                response.sendRedirect(previousPage);
            } else {
                response.sendRedirect("my_page.jsp");
            }
            return;
        } else {
            // 로그인 실패 시 에러 메시지 표시
            out.println("<p>로그인 정보가 올바르지 않습니다.</p>");
            response.sendRedirect("login.jsp");
        }
    }
	%>
</body>
</html>