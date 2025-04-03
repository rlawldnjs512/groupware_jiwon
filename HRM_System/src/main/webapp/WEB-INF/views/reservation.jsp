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
<script type="text/javascript" src="./js/reservation.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="./js/reservation_date.js"></script>
<link rel="stylesheet" href="./css/reservation.css">


</head>
 <%@ include file="sidebar.jsp" %>
<body>
	<div class="content" id="content">
	<%@ include file="header.jsp" %>
		<div class="main-content">
		<input type="hidden" id="emp_name" value="${sessionScope.loginVo.name}">
	<input type="hidden" id="emp_id" value="${sessionScope.loginVo.emp_id}">
			<div class="date-nav-container">
				<span class="today-btn" id="today-btn">오늘</span>		
				<button class="date-nav-btn" id="prev-day">&lt;</button>
				<span class="date-display" id="date-display"> 			
				<span id="date-text">${nowDate}</span> 
				<input type="date" id="rev_date">
				<input type="hidden" id="convertedDate">
				</span>
				<button class="date-nav-btn" id="next-day">&gt;</button>
			</div>
			<div id="revContent"></div>
			
			<!-- 			<div id="revContent"> -->
			<%-- 				<c:forEach var="room" items="${lists}"> --%>
			<%-- 					<div id="${room.room_id}"> --%>
			<!-- 						<div class="py-3"> -->
			<!-- 							<div class="card shadow-sm card-rounded border border-0"> -->
			<!-- 								<div class="card-header" style="background-color: white;"> -->
			<%-- 									<h4 class="card-title">${room.room_name}</h4> --%>
			<!-- 								</div> -->
			<!-- 								<div class="card-body"> -->
			<!-- 									<div class="reservation-container"> -->
			<%-- 										<c:forEach var="rev" items="${room.reservation}"> --%>
			<%-- 											<span class="btn_bg ${rev.emp_id eq '예약가능' ? 'nocheck': 'check'}" --%>
			<%-- 												name="${rev.slot}">${rev.range}</span> --%>
			<%-- 										</c:forEach> --%>
			<!-- 									</div> -->
			<!-- 								</div> -->
			<!-- 							</div> -->
			<!-- 						</div> -->
			<!-- 					</div> -->
			<%-- 				</c:forEach> --%>
			<!-- 			</div> -->
			
		</div>
	</div>
	
	
		
	<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="staticBackdropLabel">회의실예약</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="#" >
        	<div class="form-group">
        <label for="room">회의실</label>
        <input id="room" name="room" type="text" value="" readonly>
        <label for="name">이름</label>
        <input id="name" name="name" type="text" value="" readonly>
        <label for="date">날짜</label>
        <input id="date" name="date" type="text" value="" readonly>
        <label for="time">시간</label>
        <input id="time" name="time" type="text" value="" readonly>

        <p>예약하시겠습니까?</p>
    </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >취소</button>
        <button type="button" class="btn btn-primary" id="revSubmit">예약하기</button>
      </div>
    </div>
  </div>
</div>

<script id="room-template" type="template">
{{#each lists}}
<div id="{{room_id}}">
    <div class="py-3">
        <div class="card shadow-sm card-rounded border border-0">
            <div class="card-header" style="background-color: white;">
                <h4 class="card-title">{{room_name}}</h4>
            </div>
            <div class="card-body">
                <div class="reservation-container">
                    {{#each reservation}}
                        <span class="btn_bg {{#if (eq emp_id '예약가능')}}nocheck{{else}}check{{/if}}"
                              name="{{slot}}">{{range}}</span>
                    {{/each}}
                </div>
            </div>
        </div>
    </div>
</div>
{{/each}}
</script>




</body>
</html>