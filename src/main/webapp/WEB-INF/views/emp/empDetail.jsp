<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html>
<html lang="en">
<head>
<title>직원수정</title>
<meta charset="utf-8">
<style>
 
.clearfix { 
background-color: hotpink;
text-align: center;
color: white;
padding: 20px;
}
[readonly] { background-color: lightgray;}
 
</style>

</head>
<body>	
 	<div class="container">
 	 		<!-- include 디렉티브태그는 jsp를 합쳐서 컴파일한다.  -->
  		<%@ include file="../common/header.jsp" %>
		<h2 class="clearfix">직원 정보 수정</h2>
		<form action="detail.do" method="post">
			<div class="input-group mb-3">
				<span class="input-group-text">직원번호</span> 
				<input type="number"
				    readonly="readonly"
					required="required" class="form-control" value="${empInfo.employee_id}"
					name="employee_id">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">firstName</span> 
				<input type="text"  value="${empInfo.first_name}"
					class="form-control" name="first_name">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">lastName</span> 
				<input type="text" value="${empInfo.last_name}"
					required="required" class="form-control" name="last_name">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">전화번호</span> 
				<input type="text" value="${empInfo.phone_number}"
					class="form-control" name="phone_number">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">직책</span> 
					
				<select required="required" class="form-control" name="job_id">
				  <c:forEach items="${joblist}" var="job">
				    <option ${job.job_id == empInfo.job_id ?'selected':''}  value="${job.job_id}">
				         ${job.job_id}/${job.job_title}
				    </option>
				  </c:forEach>
				</select>					
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">부서</span> 
					
				<select class="form-control" name="department_id">
				  <option value="-1">부서없음</option>
				  <c:forEach items="${deptlist}" var="dept">				    
				    <option  ${empInfo.department_id==dept.department_id?'selected':'' }    
				    value="${dept.department_id}">
				         ${dept.department_name}
				    </option>
				  </c:forEach>
				</select>	
					
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">메니저</span> 
				<select class="form-control" name="manager_id">
				  <option value="-1">메니저없음</option>
				  <c:forEach items="${managerlist}" var="emp">
				     <option  ${empInfo.manager_id==emp.employee_id?'selected':'' }  value="${emp.employee_id}">
				         ${emp.first_name}/${emp.last_name}
				     </option>
				  </c:forEach>
				</select>	
					
					
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">입사일</span> 
				<input type="date"  value="${empInfo.hire_date}"
					required="required" class="form-control" name="hire_date">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">commission</span> 
				<input type="text" pattern="[0]\.[0-9]{1,2}"
				value="${empInfo.commission_pct}"
					class="form-control" name="commission_pct">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">급여</span> 
				<input type="number"
					class="form-control" name="salary" value="${empInfo.salary}">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">이메일</span> <input type="email"
					required="required" class="form-control" value="${empInfo.email}" name="email">
			</div>
			<button type="submit" class="btn btn-primary">직원정보수정</button>
		</form>
	</div>
    
</body>
</html>






