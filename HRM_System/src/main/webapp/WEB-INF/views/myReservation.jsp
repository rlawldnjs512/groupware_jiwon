<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>예약관리</title>
	<link rel="stylesheet" href="./css/emplist.css">
	<style>
		td{
			text-align: center;
			
		}
		tbody tr {
		    height: 60px;
		    vertical-align: middle; 
		}
	</style>
</head>
 <%@ include file="sidebar.jsp" %>
<body>
	<input type="hidden" id="emp_name" value="${sessionScope.loginVo.name}">
	<input type="hidden" id="emp_id" value="${sessionScope.loginVo.emp_id}">
	<div class="content" id="content">
	<%@ include file="header.jsp" %>
		<div class="main-content">
			<table class="table table-hover">
				<thead>
	            	<tr>
						<th style="text-align: center;">번호</th>
						<th style="text-align: center;">회의실 이름</th>
						<th style="text-align: center;">예약자</th>
						<th style="text-align: center;">예약날짜</th>
						<th style="text-align: center;">예약시간</th>
						<th></th>
					</tr>
	            </thead>
				
				<tbody>
					<c:set var="index" value="1" />
					<c:forEach var="room" items="${lists}">
							<tr>
								<td>${index}</td>
								<td>${room.room_name}</td>
								<td>${room.myreservation.name} (${room.myreservation.emp_id})</td>
								<td>${room.myreservation.rev_date}</td>
								<td>${room.range}</td>
								<td>
<!-- 									오늘 포함 이후의 날짜들만 취소가 가능하도록 함. -->
									<fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd" var="today" />
									<c:if test="${room.myreservation.rev_date >= today}">
										<form action="./deleteReservation.do" method="post" onsubmit="return confirmCancel()">
										    <input type="hidden" name="reserv_id" value="${room.myreservation.reserv_id}">
										    <input type="hidden" name="emp_id" value="${room.myreservation.emp_id}">
										    <button type="submit" class="btn btn-danger">취소</button>
										</form>
									</c:if>
								</td>
							</tr>
							<c:set var="index" value="${index + 1}" />
					</c:forEach>
				</tbody>
				
			</table>
		</div> <!-- main-content -->
	</div> <!-- content -->
	
	<script>
		function confirmCancel() {
		    if (confirm("정말 예약을 취소하시겠습니까?")) {
		        alert("취소되었습니다.");
		        return true;
		    }
		    return false;
		}
	</script>

</body>
</html>