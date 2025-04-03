<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="./css/emplist.css">
    <meta charset="UTF-8">
    <title>전자결재</title>
<style>
:root {
	--bs-primary: #1b84ff;
	--bs-primary-light: #e9f3ff;
	--bs-primary-white: #fff;
}

.btn.btn-light-primary {
	color: var(--bs-primary);
	border-color: var(--bs-primary-light);
	background-color: var(--bs-primary-light);
}

.btn.btn-light-primary:hover {
	color: var(--bs-primary-white);
	border-color: var(--bs-primary);
	background-color: var(--bs-primary);
}

.btn.btn-light-primary:focus {
	outline: none;
	box-shadow: 0 0 0 0.25rem rgba(var(--bs-primary), 0.5);
}

thead th {
	padding: 12px;
	text-align: center !important; /* text-align에 !important 추가 */
	vertical-align: middle;
	border-bottom: 2px solid #ddd;
}

tbody td {
	padding: 10px;
	text-align: center;
	border-bottom: 1px solid #eee;
}

tbody tr:hover {
	background-color: var(--bs-primary-light);
}

</style>
</head>
<%@ include file="sidebar.jsp" %>
<body>
    <div class="content" id="content">
        <%@ include file="header.jsp" %>
        <div class="main-content">
			<div class="table-responsive">
				<!-- 부서별 결재 완료된 문서 조회 -->
        		<table class="table table-hover">
        			<thead>
	        			<tr>
	        				<th>발급번호</th>
	        				<th>유형</th>
	        				<th>제목</th>
	        				<th>부서</th>
	        				<th>작성자</th>
	        				<th>승인상태</th>
	        				<th>승인날짜</th>
	        			</tr>
        			</thead>
        			<tbody>
						<c:choose>
							<c:when test="${empty lists}">
								<tr>
									<td colspan="7" class="text-center">문서가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="vo" items="${lists}">
									<tr>
										<td>${vo.doc_num}</td>
										<td>${vo.doc_type}</td>
										<td><a
											onclick="successDocView('${vo.doc_id}', '${vo.doc_type}')">
												${vo.title} </a></td>
										<td>${vo.dept_name}</td>
										<td>${vo.name}</td>
										<c:if test="${vo.doc_status eq 'Y'}">
											<td>승인완료</td>
										</c:if>
										<td>${vo.apprv_date}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
        		</table>
        	</div>
        </div>
    </div>
</body>
<script type="text/javascript">
	function successDocView(doc_id,doc_type) {
	    $.ajax({
	        type: "GET",
	        url: "/successDocView.do",
	        data: { 
	            doc_id: doc_id,
	            doc_type: doc_type
	        },
	        success: function(response) {
	            window.location.href = "/successDocView.do?doc_id=" + doc_id + "&doc_type=" + doc_type;
	        },
	        error: function(xhr, status, error) {
	            alert("수정 페이지로 이동하는 중 오류 발생: " + error);
	        }
	    });
	}
</script>
</html>
