<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<div class="header">
<%
    String currentURI = request.getRequestURI();
	String commonClass = "btn btn-sm btn-color-gray-600 bg-state-body btn-active-color-gray-800 fw-bolder fw-bold fs-6 fs-lg-base nav-link px-3 px-lg-4 mx-1 active";
	String dropdownClass = "btn btn-sm btn-color-gray-600 bg-state-body btn-active-color-gray-800 fw-bolder fw-bold fs-6 fs-lg-base nav-link px-3 px-lg-4 mx-1 dropdown-toggle";
%>

        <% if (currentURI.contains("servation") || currentURI.contains("Room")) { %>
	        <ul class="nav flex-wrap border-transparent">
	        	<c:if test="${sessionScope.loginVo.role eq 'U'}">
		        <li class="nav-item my-1">
		            <a class="<%= commonClass %>" href="./reservation.do"> 회의실 예약 </a>
		        </li>
		        <li class="nav-item my-1">
		            <a class="<%= commonClass %>" href="./myReservation.do"> 나의 예약조회 </a>
		        </li>
		    	</c:if>
		        <c:if test="${sessionScope.loginVo.role eq 'A'}">
	            	<li class="nav-item my-1">
	            		<a class="<%= commonClass %>" href="./selectRoom.do"> 회의실 관리 </a>
	                </li>
             	</c:if>
	    	</ul>
        <% } else if (currentURI.contains("notice") 
	        	   || currentURI.contains("free") 
	        	   || currentURI.contains("newNotice")
	        	   || currentURI.contains("newFree")
				   || currentURI.contains("modifyNotice")
				   || currentURI.contains("modifyFree")
				   || currentURI.contains("replyFree")){%>
        	<ul class="nav flex-wrap border-transparent">
                <li class="nav-item my-1">
                    <a class="<%= commonClass %>" href="./notice.do"> 공지사항 </a>
                </li>
                <li class="nav-item my-1">
                    <a class="<%= commonClass %>" href="./free.do"> 커뮤니티 </a>
                </li>
            </ul>
        <% } else if (currentURI.contains("myPage") 
        		   || currentURI.contains("certification")
        		   || currentURI.contains("signature_manage")){%>
             <ul class="nav flex-wrap border-transparent">
	            <li class="nav-item my-1">
	            	<a class="<%= commonClass %>" href="./mypage.do"> 내 정보 </a>
	            </li>
	            <li class="nav-item my-1">
	            	<a class="<%= commonClass %>" href="./certification.do"> 증명서 관리 </a>
	            </li>
	            <li class="nav-item my-1">
	            	<a class="<%= commonClass %>" href="./signature_manage.do"> 서명 관리 </a>
	            </li>
          	</ul>
        <% } else if (currentURI.contains("approval") 
        		   || currentURI.contains("approval_mine") 
        		   || currentURI.contains("approval_store") 
            	   || currentURI.contains("vacationApproval") 
            	   || currentURI.contains("tripApproval")
            	   || currentURI.contains("reportApproval")
            	   || currentURI.contains("approval_receive")
            	   || currentURI.contains("temp_store")
            	   || currentURI.contains("dept_store")
            	   || currentURI.contains("continue")){%>
             <ul class="nav flex-wrap border-transparent">
	            <li class="nav-item my-1">
	            	<a class="<%= commonClass %>" href="./approval.do">결제 홈</a>
	            </li>
	            <li class="nav-item my-1 dropdown">
	            	<a class="<%= dropdownClass %>" href="./approval_mine.do"  id="dropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false"> 내 결재 </a>
	            	<ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
			            <li><a class="dropdown-item" href="./approval_receive.do">결재 수신함</a></li>
			            <li><a class="dropdown-item" href="./approval_mine.do">내 결재함</a></li>
		            </ul>
	            </li>
	            <li class="nav-item my-1 dropdown">
	            	<a class="<%= dropdownClass %>" href="#" id="dropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false"> 결재 문서함 </a>
	            	<ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
		            	<li><a class="dropdown-item" href="./temp_store.do">임시 문서함</a></li>
		            	<li><a class="dropdown-item" href="./dept_store.do">부서 문서함</a></li>
		            </ul>
	            </li>
	            
          	 </ul>
        <% } else if (currentURI.contains("attendance") 
        		   || currentURI.contains("vacation")){%>
        	 <c:choose>
        	 	<c:when test="${sessionScope.loginVo.role eq 'A'}">
        	 		<ul class="nav flex-wrap border-transparent">
					 	<li class="nav-item my-1">
					 		<a class="<%= commonClass %>" href="./vacation_admin"> 직원 휴가 관리 </a>
					 	</li>
				 	</ul>
        	 	</c:when>
        	 	<c:otherwise>
        	 		<ul class="nav flex-wrap border-transparent">
						<li class="nav-item my-1">
							<a class="<%= commonClass %>" href="./attendance"> 나의 근태 </a>
						</li>
						<li class="nav-item my-1">
							<a class="<%= commonClass %>" href="./vacation"> 휴가 내역 조회 </a>
						</li>
					</ul>
        	 	</c:otherwise>
        	 </c:choose>
        <% }%>

</div>

<!-- Bootstrap JS 로드 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>