<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>직원 휴가 관리</title>
	<link rel="stylesheet" href="./css/vacation_admin.css">
	<style>
		.searchArea {
			display: flex;
			justify-content: flex-end;
			align-items: center;     /* 세로 중앙 정렬 */
		    gap: 10px;               /* 요소 간 간격 */
		}
		
		/* 검색폼 영역 스타일 */
		.main-content fieldset {
		    border: 1px solid #ddd;
		    border-radius: 4px;
		    padding: 15px;
		    background-color: #f9f9f9;
		    margin-bottom: 20px;
		    min-width: 1024px; /* 최소 너비를 1024px로 설정 */
		}
	</style>
</head>
<%@ include file="sidebar.jsp" %>
<body>
	<div class="content" id="content">
	<%@ include file="header.jsp" %>
		<div class="main-content">
		
			<form action="/vacation_admin" method="get" name="vacationSearch">
				<div class="table-responsive">
					<fieldset class="btn-container">
						<div class="searchArea">
							<select name="type" id="type">
						            <option value="name" ${(param.type == "name")?"selected":""}>이름</option>
						        	<option value="emp_id" ${(param.type == "emp_id")?"selected":""}>사원번호</option>
						    </select>
							<input type="text" name="keyword" value="${param.keyword}" placeholder="검색어를 입력해주세요.">
							<button type="submit" class="button-common search-btn">
								<img src="images/search.svg" alt="검색 아이콘" style="width: 30px; height: 30px;">
							</button>
						</div>
					</fieldset>
				</div> <!-- 검색 영역 -->
			
				<table class="table table-hover">
					<thead class="table-light">
						<tr>
							<th>번호</th>
							<th>이름</th>
							<th>사원번호</th>
							<th>휴가 유형</th>
							<th>기간</th>
						</tr>
					</thead>
					<tbody>
				    	<c:forEach var="leave" items="${leaveLists}" varStatus="status">
					        <tr>
					        	
					            <td>${(page.page-1) * page.countList + status.index + 1}</td>
					            <td>${leave.NAME}</td>
					            <td>${leave.EMP_ID}</td>
					            <td>${leave.TYPE}</td>
					            <td>
					            	<fmt:formatDate value="${leave.LEAVE_START}" pattern="yyyy-MM-dd" /> 
					            	 ~ 
					            	<fmt:formatDate value="${leave.LEAVE_END}" pattern="yyyy-MM-dd" />
					            </td>
					        </tr>
					    </c:forEach>   
				    </tbody>
				</table>
			</form>
		</div> <!-- main-content -->
		
		<div class="pagination-container text-center">
            <c:if test="${page.totalPage >= 1}">
                <ul class="pagination pagination-lg">
                    <c:if test="${page.page > 1}">
                        <li><a href="./vacation_admin?page=${page.page - 1}&type=${param.type}&keyword=${param.keyword}">&laquo;</a></li>
                    </c:if>

                    <c:forEach var="i" begin="${page.stagePage}" end="${page.endPage}" step="1">
                        <li class="${i == page.page ? 'active' : ''}">
                            <a href="./vacation_admin?page=${i}&type=${param.type}&keyword=${param.keyword}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${page.page < page.totalPage}">
                        <li><a href="./vacation_admin?page=${page.page + 1}&type=${param.type}&keyword=${param.keyword}">&raquo;</a></li>
                    </c:if>
                </ul>
            </c:if>
        </div> <!-- 페이징 -->
		
		
		
	</div> <!-- content -->
</body>
</html>