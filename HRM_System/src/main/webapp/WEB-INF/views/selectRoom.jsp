<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회의실 관리</title>
<link rel="stylesheet" href="./css/emplist.css">
<style type="text/css">
tr, td {
	text-align: center;	
	vertical-align: middle;
}

.main-content table thead tr th,
.main-content table thead tr td {
    text-align: center;
}

</style>
</head>
 <%@ include file="sidebar.jsp" %>
<body>
	<div class="content" id="content">
	<%@ include file="header.jsp" %>
		<div class="main-content">
		
			<table class="table table-hover">
				<tbody>
					<tr>
						<td>
							<form action="/insertRoom.do" method="post">
								회의실 추가&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input placeholder="회의실 이름" name="room_name">
								<button type="submit" class="btn btn-success">추가</button>
							</form>
						</td>
					</tr>
				</tbody>
			</table>
			
			
			<table class="table table-hover">
				<thead>
	            	<tr>
						<td>회의실 번호</td>
						<td>회의실 이름</td>
						<td></td>
					</tr>
	            </thead>
				<tbody>
					<c:forEach var="room" items="${lists}" varStatus="vs">
						<tr>
							<td>${room.room_id}</td>
							<td>${room.room_name}</td>
							<td>
								<form action="./deleteRoom.do" method="post"
									onsubmit="return confirmCancel()">
								    <input type="hidden" name="room_id" value="${room.room_id}">
									<button type="submit" class="btn btn-danger">삭제</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
		</div>	
	</div>
	
	<script>
function confirmCancel() {
    if (confirm("정말 회의실을 삭제하시겠습니까?")) {
        alert("삭제되었습니다.");
        return true;
    }
    return false;
}
</script>
</body>
</html>