<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container mt-3">
<%@ include file = "../common/header.jsp" %>
  <h2>게시글 수정</h2>
  <form action="update.do" method = "post">
    <div class="input-group mb-3">
      <span class="input-group-text">번호</span>
      <input type="text" class="form-control" name="board_no" readonly="readonly" value = "${board.board_no}">
    </div>
     <div class="input-group mb-3">
      <span class="input-group-text">등록일</span>
      <input type="text" class="form-control" name="regdate" readonly="readonly" value = "${board.regdate}">
    </div>
     <div class="input-group mb-3">
      <span class="input-group-text">제목</span>
      <input type="text" class="form-control" name="title" value = "${board.title}">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">내용</span>
      <input type="text" class="form-control" name="content" value = "${board.content}">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">작성자</span>
      <input type="text" class="form-control" name="writer" readonly="readonly" value = "${board.writer}">
    </div>
     <div class="input-group mb-3">
     <img alt="${board.title}이미지" width="150" height="100" src="${path}/resources/upload/${board.pic}">
    </div>
    <c:if test = "${loginMember.member_id == board.writer}">
    <div class="input-group mb-3">
      <input class="btn btn-primary" type="submit" value = "수정">
    </div>
    </c:if>
    </form>
    </div>
</body>
</html>