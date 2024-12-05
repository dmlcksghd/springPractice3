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
		<!-- include 디렉티브태그는 jsp를 합쳐서 컴파일한다.  -->
		<%@ include file="../common/header.jsp"%>
		<h2>게시글 등록</h2>
		<form action="insert.do" method="post" enctype="multipart/form-data">
			
			<div class="input-group mb-3">
				<span class="input-group-text">제목</span> 
				<input type="text" class="form-control" name="title" value="${board.title}">
			</div>
			
			<div class="input-group mb-3">
				<span class="input-group-text">내용</span> 
				<input type="text" class="form-control" name="content" value="${board.content}">
			</div>
			
			<div class="input-group mb-3">
				<span class="input-group-text">이미지선택</span> 
				<input type="file" class="form-control" name="pic">
			</div>
			
			<div class="input-group mb-3">
					<input class="btn btn-primary" type="submit" value="입력">
			</div>
		</form>
	</div>
</body>
</html>