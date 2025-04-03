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
    <title>임시문서함</title>
<style>
:root {
	--bs-primary: #1b84ff;
	--bs-primary-light: #e9f3ff;
	--bs-primary-white: #fff;
}

.btn.btn-light-success {
   color: var(--bs-success);
   border-color: var(--bs-success-light);
   background-color: var(--bs-success-light);
}

.btn.btn-light-success:hover {
   color: var(--bs-white);
   border-color: var(--bs-success);
   background-color: var(--bs-success);
}

.btn.btn-light-success:focus {
   outline: none;
   box-shadow: 0 0 0 0.25rem rgba(var(--bs-success), 0.5);
}

.btn.btn-light-secondary {
   color: var(--bs-secondary);
   border-color: var(--bs-secondary-light);
   background-color: var(--bs-secondary-light);
}

.btn.btn-light-secondary:hover {
   color: var(--bs-white);
   border-color: var(--bs-secondary);
   background-color: var(--bs-secondary);
}

.btn.btn-light-secondary:focus {
   outline: none;
   box-shadow: 0 0 0 0.25rem rgba(var(--bs-secondary), 0.5);
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
        	<!-- 결재 상신 전에 문서 임시저장 장소 -->
        	<div>
        		<table class="table table-hover">
        			<thead>
	        			<tr>
	        				<th>발급번호</th>
	        				<th>유형</th>
	        				<th>제목</th>
	        				<th>승인상태</th>
	        				<th>이어하기</th>
	        				<th>삭제</th>
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
			        					<td>${vo.title}</td>
			        					<c:if test="${vo.doc_status eq 'T'}">
				        					<td>
				        						임시저장
				        					</td>
			        					</c:if>
			        					<td>
			        						<input class="btn btn-light-success" type="button" onclick="continueTemp('${vo.doc_id}', '${vo.doc_type}')" value="보기">
			        					</td>
			        					<td>
			        						<input class="btn btn-light-secondary"  type="button" onclick="deleteTemp('${vo.doc_id}', '${vo.doc_type}')" value="삭제">
			        					</td>
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
function continueTemp(doc_id, doc_type) {
    $.ajax({
        type: "GET",
        url: "/continueTemp.do",
        data: { 
            doc_id: doc_id,
            doc_type: doc_type
        },
        success: function(response) { 
            window.location.href = "/continueTemp.do?doc_id=" + doc_id + "&doc_type=" + doc_type;
        },
        error: function(xhr, status, error) {
            alert("수정 페이지로 이동하는 중 오류 발생: " + error);
        }
    });
}

	
	function deleteTemp(doc_id, doc_type) {
	    $.ajax({
	        type: "GET",
	        url: "/deleteTemp.do",
	        data: { 
	            doc_id: doc_id,
	            doc_type: doc_type
	        },
	        success: function(response) {
	            window.location.href = "/temp_store.do"; 
	        },
	        error: function(xhr, status, error) {
	            alert("삭제 중 오류 발생: " + error);
	        }
	    });
	}

</script>
</html>
