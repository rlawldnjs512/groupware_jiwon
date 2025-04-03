<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>vacation</title>
	<link rel="stylesheet" href="./css/vacation.css">
</head>
<%@ include file="sidebar.jsp" %>
<body>

	<div class="content" id="content">
	<%@ include file="header.jsp" %>
		<div class="main-content">
			<div class="card border-light mb-3 shadow p-3 rounded">
				<div class="card-body">
					<div class="vacationInfo">
						<div class="card-title">
							<h4>총휴가</h4>
							<p>${vacationMap['LEAVE_TOTAL']}일</p>
						</div>
						
						<div class="card-title">
							<h4>사용휴가</h4>
							<p>${vacationMap['LEAVE_USE']}일</p>
						</div>
						
						<div class="card-title">
							<h4>잔여휴가</h4>
							<p>${vacationMap['LEAVE_REMAIN']}일</p>
						</div>
					</div>
					<div class="period">
						<input type="date" id="startDate">
						<p>~</p>
						<input type="date" id="endDate">
						<button id="searchBtn" class="btn btn-light-primary ms-2">조회</button>
					</div>
				</div> <!-- card-body -->
			</div> <!-- card -->
			
			<table class="table table-hover">
				<thead class="table-light">
					<tr>
						<td>번호</td>
						<td>휴가 유형</td>
						<td>기간</td>
					</tr>
				</thead>
				<tbody id="leaveTableBody">
			    	<c:forEach var="leave" items="${leaveList}" varStatus="status">
				        <tr>
				            <td>${status.index + 1}</td>
				            <td>${leave.TYPE}</td>
				            <td>${leave.LEAVE_START} ~ ${leave.LEAVE_END}</td>
				        </tr>
				    </c:forEach>   
			    </tbody>
			</table>
		</div> <!-- main-content -->
	</div> <!-- content -->
	
	<script>
		document.addEventListener('DOMContentLoaded', function() {
		    // 조회 버튼 클릭 이벤트
		    const searchBtn = document.getElementById("searchBtn");
		    searchBtn.addEventListener("click", function() {
		        var startDate = document.getElementById("startDate").value;
		        var endDate = document.getElementById("endDate").value;
		        selectPeriod(startDate, endDate);
		    });
		});
	
		function selectPeriod(startDate, endDate) {
		    var url = "/vacation/filter?startDate="+startDate+"&endDate="+endDate;
		    fetch(url)
		        .then(response => response.json())
		        .then(data => {
		            console.log(data); 
		            
 		            const leaveTableBody = document.getElementById("leaveTableBody");
 		            leaveTableBody.innerHTML = "";
		            
		            data.forEach((leave, index) => {
		            	const tr = document.createElement("tr");
		            	
		            	const tdIndex = document.createElement("td");
		            	tdIndex.textContent = index + 1;
		            	
		            	const tdType = document.createElement("td");
		            	tdType.textContent = leave.TYPE;
		            	
		            	const tdPeriod = document.createElement("td");
		            	tdPeriod.textContent = leave.LEAVE_START + " ~ " + leave.LEAVE_END;
		            	
		            	tr.appendChild(tdIndex);
		            	tr.appendChild(tdType);
		            	tr.appendChild(tdPeriod);
		            	
		            	leaveTableBody.appendChild(tr);
		            });
		        })
		        .catch(error => {
		            console.error(error);
		        });
		}
	</script>
	
	
	
	
	
	
						
	
<!-- 	<div class="main-content"> -->
<!-- 		<h3>사원들의 연차 정보 테이블 조회</h3> -->
<!-- 		<table class="table table-hover"> -->
<!-- 			<thead> -->
<!-- 				<tr class="success"> -->
<!-- 					<td>번호</td> -->
<!-- 					<td>사원번호</td> -->
<!-- 					<td>연차발생일</td> -->
<!-- 					<td>연차소멸일</td> -->
<!-- 					<td>총연차</td> -->
<!-- 					<td>사용한연차</td> -->
<!-- 					<td>남은연차</td> -->
<!-- 					<td>보상시간</td> -->
<!-- 				</tr> -->
<!-- 			</thead> -->
<!-- 			<tbody> -->
<%-- 				<c:forEach var="vo" items="${lists}" varStatus="vs"> --%>
<!-- 					<tr> -->
<%-- 						<td>${vo.leave_id}</td> --%>
<%-- 						<td>${vo.emp_id}</td> --%>
<%-- 						<td>${vo.start_date}</td> --%>
<%-- 						<td>${vo.end_date}</td> --%>
<%-- 						<td>${vo.leave_total}</td> --%>
<%-- 						<td>${vo.leave_use}</td> --%>
<%-- 						<td>${vo.leave_remain}</td> --%>
<%-- 						<td>${vo.extra_time}</td> --%>
<!-- 					</tr> -->
<%-- 				</c:forEach> --%>
<!-- 			</tbody> -->
<!-- 		</table> -->
		

<!-- 		<br> -->
<!-- 		<br> -->

<!-- 		<form action="/insertVacation" method="POST"> -->
<!-- 			<h3>현재 사원(input value="사원번호")의 연차 등록(insert)</h3> -->
<!-- 			<table class="table table-hover"> -->
<!-- 				<thead> -->
<!-- 					<tr class="success"> -->
<!-- 						<td>leave_id(seq)</td> -->
<!-- 						<td>emd_id</td> -->
<!-- 						<td>start_date</td> -->
<!-- 						<td>end_date</td> -->
<!-- 						<td>leave_total</td> -->
<!-- 						<td>leave_use</td> -->
<!-- 						<td>leave_remain</td> -->
<!-- 					</tr> -->
<!-- 				</thead> -->
<!-- 				<tbody> -->
<!-- 					<tr> -->
<!-- 						<td><input type="text" name="leaveId"></td> -->
<!-- 						<td><input type="text" name="empId"></td> -->
<!-- 						<td><input type="text" name="startDate"></td> -->
<!-- 						<td><input type="text" name="endDate" value="20251231"></td> -->
<!-- 						<td><input type="text" name="leaveTotal"></td> -->
<!-- 						<td><input type="text" name="leaveUse"></td> -->
<!-- 						<td><input type="text" name="leaveRemain"></td> -->
<!-- 					</tr> -->
<!-- 				</tbody> -->
<!-- 			</table> -->
<!-- 					<button type="submit" class="btn btn-secondary">연차 정보 저장</button> -->
<!-- 		</form> -->
<!-- 	</div> -->
	
	
	
</body>
</html>