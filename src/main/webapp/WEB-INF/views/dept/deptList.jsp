<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
var result = "${result}";

if(result!=""){
	alert(result);
}
</script>
</head>
<body>

   <div class="container">
    <%@ include file="../common/header.jsp" %>
	<a class="btn btn-success" href="insert.do">신규등록(이동)</a>
	<button id="btnInsert" type="button" class="btn btn-primary" data-bs-toggle="modal"
		data-bs-target="#myModal">신규등록(모달button)</button>
		
		<a href="#myModal" type="button" class="btn btn-primary" data-bs-toggle="modal"
		 >신규등록(모달a)</a>
		
	<hr>
	<h1>부서목록</h1>
	<table class="table table-bordered border-secondary table-striped align-middle">
		<tr class="table-info p-5">
			<th>부서번호</th>
			<th>부서이름</th>
			<th>메니저번호</th>
			<th>지역번호</th>
			<th></th>
			<th></th>
		</tr>
		 
		<c:forEach items="${deptlist}" var="dept">
			<tr class="table">
				<td>
				<a href="detail.do?deptid=${dept.department_id}">${dept.department_id}
				</a>
				<button  id="btnUpdate"
				class="badge badge-pill badge-danger" 
				style="color:red;padding:10px;background-color:orange"
				data-bs-toggle="modal"
				data-bs-target="#myModal"
				data-deptid="${dept.department_id}" 
				data-deptname="${dept.department_name}"
				data-locid="${dept.location_id}"
				data-mid="${dept.manager_id}"
				>상세</button>
						
				</td>
				<td>${dept.department_name}</td>
				<td>${dept.manager_id}</td>
				<td>${dept.location_id}</td>
				<td>
					<button class="btn btn-secondary"
						onclick="location.href='delete.do?deptid=${dept.department_id}'">삭제(get)</button>
				</td>
				<td>
					<form action="delete.do" method="post" >
						<input type="hidden" name="deptid" value="${dept.department_id}">
						<input type="submit" value="삭제(post)" 
						class="btn btn-secondary" style="background:lightgray;color:blue;"
						       >
					</form>
				</td>
			</tr>
		</c:forEach>

	</table>
</div>

	<!-- The Modal -->
	<div class="modal fade" id="myModal"   >
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">insert/update</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action="detail.do" method="post">
						<div class="input-group mb-3">
							<span class="input-group-text">부서번호</span> 
							<input type="number"
								required="required" class="form-control" placeholder="숫자입력"
								name="department_id">
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">부서이름</span> <input type="text"
								required="required" class="form-control" name="department_name">
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">부서장</span> <input type="number"
								required="required" class="form-control"  
								name="manager_id">
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">지역번호</span> <input type="number"
								required="required" class="form-control"  
								name="location_id">
						</div>
						<button id="insertOrUpdate" type="button" class="btn btn-primary">수정</button>
					</form>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(function(){
          $("#btnInsert, #btnUpdate").on("click", function(){
			
			if($(this).text() == "상세"){
			    $("form").attr("action", "detail.do");
			    $("#insertOrUpdate").text("수정");   
			}else{
				$("form").attr("action", "insert.do");
				$("#insertOrUpdate").text("입력");
			}
          });  
    	  $("#myModal").on("show.bs.modal", function(e) {
              var deptid = $(e.relatedTarget).data('deptid');
              var deptname = $(e.relatedTarget).data('deptname');
              var mid = $(e.relatedTarget).data('mid');
              var locid = $(e.relatedTarget).data('locid');
			  $("form input[name='department_id']").val(deptid);
			  $("form input[name='department_name']").val(deptname);
			  $("form input[name='manager_id']").val(mid);
			  $("form input[name='location_id']").val(locid);
               
          });
    	  
    	  
         $("#insertOrUpdate").on("click", function(event){        	 
        	 event.preventDefault();		 
 			 $.ajax({
				url: $("form").attr("action"),
				type:"post",
				data:{"department_id":$('[name="department_id"]').val(),
					"department_name":$('[name="department_name"]').val(),
					"manager_id":$('[name="manager_id"]').val(),
					"location_id":$('[name="location_id"]').val()
				},
				success:function(){
					$("#myModal").hide();
					location.reload();
					//location.href="list.do";
				}
			}); 
         })
      });
    </script>

</body>
</html>







