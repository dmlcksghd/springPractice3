<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="path" value="${pageContext.servletContext.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>first1.jsp열림</h1>

<h2>POst요청하기 연습</h2>
<form action="${path}/second3.do" method="post">
 id : <input type="text" name="userid" value="user1"><br>
 pw : <input type="text" name="userpw" value="1234"><br>
 <input type="submit" value="서버에 전송(post)"> 
</form>


<h2>요청 파라메터 체크 연습</h2>
<form action="${path}/second4.do" method="get">
 id : <input type="text" name="userid" value="user1"><br>
 pw : <input type="text" name="userpw" value="1234"><br>
 <input type="submit" value="서버에 전송(get)"> 
</form>






</body>
</html>