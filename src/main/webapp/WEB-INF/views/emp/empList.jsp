<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
	<div class="container">
		<%@ include file="../common/header.jsp"%>
		<hr>
		<button id="btnSalary" class="btn btn-danger">조회(only급여)</button>
		<button id="btnJob" class="btn btn-danger">조회(only직책)</button>
		<button id="btnDept" class="btn btn-danger">조회(only부서)</button>
		<button id="btnJobJoin" class="btn btn-danger">조회(only직책join)</button>
		<button id="btnJobJoin2" class="btn btn-danger">조회(only직책join-map)</button>
		<button id="btnTransfer" class="btn btn-danger">Transaction연습</button>
		<hr>	
		<h3>Restful API사용하기</h3>
		<button id="btnSelect" class="btn btn-secondary">전체조회</button>
		<input type="number" id="empid" value="100">
		<button id="btnDetail" class="btn btn-secondary">특정상세보기</button>
		<button id="btnInsert" class="btn btn-secondary">입력</button>
		<button id="btnUpdate" class="btn btn-secondary">수정</button>
		<button id="btnDelete" class="btn btn-secondary">삭제</button>
		<input type="number" id="empid" value="21">
		<script>
			$(function() {
				$("#btnSelect").click(f_select);
				$("#btnDetail").click(f_detail);
				$("#btnInsert").click(f_insert);
				$("#btnUpdate").click(f_update);
				$("#btnDelete").click(f_delete);
			});
			
			function print(responseData) {
				var dynamicRows = "";
				
				$.each(responseData, function(index, emp) {
				dynamicRows += `
				<tr>
				<td>\${index+1}</td>
				<td><a href="${path}/emp/detail.do?empid=\${emp.employee_id}">\${emp.employee_id}</a></td>
				<td><a href="${path}/emp/detail.do?empid=\${emp.employee_id}">\${emp.first_name}</a></td>
				<td>\${emp.last_name}</td>
				<td>\${emp.email}</td>
				<td>\${emp.phone_number}</td>
				<td>\${emp.job_id}</td>
				<td>\${emp.hire_date}</td>
				<td>\${emp.commission_pct}</td>
				<td>\${emp.salary}</td>
				<td>\${emp.manager_id}</td>
				<td>\${emp.department_id}</td>
				</tr>`;
				});
				
				var output = `
				<table class="table table-striped table-bordered table-hover">
				<tr>
				<td>순서</td>
				<td>직원번호</td>
				<td>fname</td>
				<td>lname</td>
				<td>email</td>
				<td>phone</td>
				<td>job</td>
				<td>hiredate</td>
				<td>commission</td>
				<td>salary</td>
				<td>manager</td>
				<td>department</td>
				</tr>
				\${dynamicRows}
			</table>`;
			return output;
			}
			
			function f_select() {
				alert("f_select");
				$.ajax({
					url:"${path}/rest/emplist.do",
					success:function(responseData) {
						var output = print(responseData);
						$("#table_here").html(output);
					},
					error:function(){}
				});
			}
			function f_detail() {
				alert("f_detail");
				var empid = $("#empid").val();
				$.ajax({
					url:`${path}/rest/empdetail.do/\${empid}`,
					success:function(responseData) {
						var dynamicOutput = "";
						for(let prop in responseData) {
							dynamicOutput += `<li>\${prop}의 값은 \${responseData[prop]}</li>`;
						}
						var output = `
						<ul>
							\${dynamicOutput}
						</ul>`;
						$("#table_here").html(output);
					},
					error:function(){}
				});
			}
			function getData() {
				var obj = {
						  "employee_id": 21,
						  "first_name": "zz",
						  "last_name": "wang",
						  "email": "wang@naver.com",
						  "phone_number": "010.1234.6667",
						  "hire_date": 1055775600000,
						  "job_id": "AD_PRES",
						  "salary": 24000.0,
						  "commission_pct": 0.5,
						  "manager_id": 100,
						  "department_id": 90
						};
				return obj;
			}
			function f_insert() {
				alert("f_insert");
				var emp = getData();
				//'application/x-www-form-urlencoded'
				$.ajax({
					url:"${path}/rest/empinsert.do",
					type:"post",
					contentType:"application/json",
					data:JSON.stringify(emp),
					success:function(responseData) {
						alert(responseData);
					},
					error:function(err) {
						console.log(err);
					}
				});
			}
			function updateData() {
				var obj = {
						  "employee_id": 21,
						  "first_name": "jo",
						  "last_name": "bd",
						  "email": "bd@naver.com",
						  "phone_number": "010.1234.6667",
						  "hire_date": 1055775600000,
						  "job_id": "AD_PRES",
						  "salary": 24000.0,
						  "commission_pct": 0.5,
						  "manager_id": 100,
						  "department_id": 90
						};
				return obj;
			}
			function f_update() {
				alert("f_update");
				var emp = updateData();
				$.ajax({
					url:"${path}/rest/empupdate.do",
					type:"put",
					contentType:"application/json",
					data:JSON.stringify(emp),
					success:function(responseData) {
						alert(responseData);
					},
					error:function(err) {
						console.log(err);
					}
				});
			}
			function f_delete() {
				alert("f_delete");
				var empid = $("#empid").val();
				$.ajax({
					url:`${path}/rest/empdelete.do/\${empid}`,
					type:"delete",
					success:function(responseData) {
						alert(responseData);
					},
					error:function(err) {
						console.log(err);
					}
				});
			}
		</script>
		<hr>	 
			<div class="input-group mt-5 mb-5">
				<span class="input-group-text">부서</span> 
				<select
					class="form-control" name="department_id">
					<option value="-1">선택안함</option>
					<c:forEach items="${deptlist}" var="dept">
						<option value="${dept.department_id}">
							${dept.department_name}</option>
					</c:forEach>
				</select>
				<span class="input-group-text">직책</span> 
					
				<select required="required" class="form-control" name="job_id">
				  <option value="-1">선택안함</option>
				  <c:forEach items="${joblist}" var="job">
				    <option ${job.job_id=='IT_PROG'?'selected':''}  value="${job.job_id}">
				         ${job.job_id}/${job.job_title}
				    </option>
				  </c:forEach>
				</select>
				<span class="input-group-text">급여(이상)</span> 
				<input type="number"
					class="form-control" name="salary" value="2000">
				 
				<span class="input-group-text">입사일(이후)</span> 
				<input type="date" class="form-control" name="hire_date">
				 
				 <div class="input-group-text">
					    <input class="form-check-input mt-0" type="checkbox" value="1" name="chkDate"
					    aria-label="Checkbox for following text input"><span>모든일자</span>
	  			</div>
				<button id="btn_condition" class="btn btn-primary">조건조회</button>
			</div>
	 

		<hr>
		<h1>직원List</h1>

		<%--JSP가 .java로 변환시 주석은 무시한다. ${} --%>
		<!-- HTML주석이므로 .java로 변환시 포함한다.  -->
        <div id="table_here">
	         -------------------------------------------
		</div>
	</div>
	<script>
 $(function(){

   var result = "${resultMessage}";
   if(result!=""){
	   alert(result);
   }
	 
	 
   var d = new Date();
   d.setFullYear(d.getFullYear() - 20) ;
   $('[name="hire_date"]').val(d.toISOString().split("T")[0] );
   $("#btn_condition").on("click", f_ajax);
   $("#btn_condition").trigger("click"); //이벤트 호출 
   $("#btnSalary").on("click", f_salary);
   $("#btnJob").on("click", f_job);
   $("#btnDept").on("click", f_dept);
   $("#btnJobJoin").on("click", f_jobjoin);
   $("#btnJobJoin2").on("click", f_jobjoin2);
   $("#btnTransfer").on("click", f_transfer);
});  
function f_transfer() {
	alert("f_transfer");
	$.ajax({
		url:"${path}/emp/transfer.do",
		success:function(responseData) {
			alert("responseData");
		},
		error:function() {}
	});
}
function f_salary() {
	alert("f_salary");
	$.ajax({
		url:"${path}/emp/listBySalary.do",
		data:{salary: $("input[name='salary']").val()},
		success:function(responseData) {
			$("#table_here").html(responseData);
		},
		error:function() {}
	});
}
function f_job() {
	alert("f_job");
	$.ajax({
		url:"${path}/emp/listByJob.do",
		data:{job: $("select[name='job_id']").val()},
		success:function(responseData) {
			$("#table_here").html(responseData);
		},
		error:function() {}
	});
}
function f_dept() {
	alert("f_dept");
	$.ajax({
		url:"${path}/emp/listByDept.do",
		data:{dept_id: $("select[name='department_id']").val()},
		success:function(responseData) {
			$("#table_here").html(responseData);
		},
		error:function() {}
	});
}

function f_jobjoin() {
	alert("f_jobjoin");
	$.ajax({
		url:"${path}/emp/listByJobJoin.do",
		data:{job: $("select[name='job_id']").val()},
		success:function(responseData) {
			$("#table_here").html(responseData);
		},
		error:function() {}
	});
}

function f_jobjoin2() {
	alert("f_jobjoin2");
	$.ajax({
		url:"${path}/emp/listByJobJoin2.do",
		data:{job: $("select[name='job_id']").val()},
		success:function(responseData) {
			$("#table_here").html(responseData);
		},
		error:function() {}
	});
}


function f_ajax(){
  $.ajax({
    url:"${path}/emp/list2.do",
    type:"get",
    data:{
         "deptid": $('[name="department_id"]').val(),
         "jobid": $('[name="job_id"]').val(),
         "salary": $('[name="salary"]').val(),
         "hdate": $('[name="hire_date"]').val(),
         "chk": $('[name="chkDate"]').prop("checked")
    },
    success:function(responseData){
      //2.data를 받아서 HTML만들까?NO
      //3.JSP를 받아서 현재화면에 대치(replace)?OK
      $("#table_here").html(responseData);
    },
    error:function(err){
		alert(err);
    }
  });
}
</script>
</body>
</html>




