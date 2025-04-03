<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <meta charset="UTF-8">
    <title>전자결재</title>
<style>
:root {
	--bs-white: #fff;
	--bs-primary: #1b84ff;
	--bs-primary-light: #e9f3ff;
	--bs-success: #17c653;
	--bs-success-light: #dfffea;
	--bs-secondary: #252f4a;
	--bs-secondary-light: #f9f9f9;
	--bs-warning: #f6c000;
	--bs-warning-light: #fff8dd;
}

.btn.btn-light-dark:hover {
	color: var(--bs-white);
	border-color: var(--bs-secondary);
	background-color: var(--bs-secondary);
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: center;
}

th {
	background-color: #f0f0f0;
}

.gray {
	background-color: #ddd;
}

.main-content {
	width: 100%;
	padding-top: 20px; /* 위쪽 여백 추가 */
	text-align: center; /* 버튼 가운데 정렬 */
	flex-wrap: wrap; /* 요소가 줄 바꿈될 수 있도록 */
	align-items: flex-start; /* 카드들의 높이를 각각 콘텐츠에 맞게 설정 */
}

.button-container {
	display: flex;
	justify-content: center; /* 가로 중앙 정렬 */
	gap: 20px; /* 버튼 간격 */
	margin-bottom: 20px; /* 아래쪽 여백 */
}

.button-container button {
	flex: 1; /* 버튼 크기를 동일하게 확장 */
	max-width: 300px; /* 버튼 최대 너비 설정 */
	height: 40px; /* 버튼 높이 */
	font-size: 18px; /* 글자 크기 */
	font-weight: bold; /* 글자 굵게 */
}

.d-flex {
    display: flex;
    align-items: stretch;  /* 두 카드의 높이를 일치시킴 */
    gap: 20px; /* 카드 사이의 간격 */
}

.card-body {
    display: flex;
    flex-direction: column;
    justify-content: center;  /* 세로 방향으로 중앙 정렬 */
    align-items: center;  /* 가로 방향으로 중앙 정렬 */
    height: 100%;  /* 카드의 전체 높이 사용 */
}

.card-body img {
    width: 200px;  /* 이미지 크기 */
    height: 200px; /* 이미지 크기 */
}

.card-body button {
    width: 200px;  /* 버튼 너비 */
    height: 200px;  /* 버튼 높이 */
    font-size: 18px;  /* 버튼 글자 크기 */
    display: flex;  /* 내용 중앙 정렬을 위한 flexbox */
    justify-content: center;  /* 수평 중앙 정렬 */
    align-items: center;  /* 수직 중앙 정렬 */
    border-radius: 10px;  /* 모서리를 둥글게 만들기 */
    padding: 0;  /* 버튼 내 여백 제거 */
}

.card-body button:hover {
    background-color: #e9f3ff;  /* hover 시 배경 색상 */
    color: var(--bs-primary);  /* hover 시 글자 색상 */
}

.button-container button {
	width: 1000px;  /* 버튼 너비 */
    height: 50px;  /* 버튼 높이 */
    font-size: 22px;  /* 버튼 글자 크기 */
    display: flex;  /* 내용 중앙 정렬을 위한 flexbox */
    justify-content: center;  /* 수평 중앙 정렬 */
    align-items: center;  /* 수직 중앙 정렬 */
    border-radius: 10px;  /* 모서리를 둥글게 만들기 */
    padding: 0;  /* 버튼 내 여백 제거 */
}


</style>
</head>
<%@ include file="sidebar.jsp" %>
<body>
    <div class="content" id="content">
        <%@ include file="header.jsp"%>
		<div class="main-content">

			<div class="button-container">
				<button type="button" class="btn btn-light-dark"
					onclick="location.href='/vacationForm.do'">휴가 작성하기</button>
				<button type="button" class="btn btn-light-dark"
					onclick="location.href='/tripForm.do'">출장 작성하기</button>
				<button type="button" class="btn btn-light-dark"
					onclick="location.href='/reportForm.do'">보고서 작성하기</button>
			</div>

			<div class="d-flex align-items-start gap-1">
				<!-- 진행 중인 결재 -->
				<div class="card list border-light mb-3 shadow p-3 rounded wide-card" style="flex: 1 1 20%;">
					<div class="card-header">진행중인 결재 목록</div>
					<div class="card-body text-center">
						<table
							class="table table-hover table-rounded table-striped border gy-7 gs-7">
							<thead>
								<tr
									class="fw-semibold fs-6 text-gray-800 border-bottom-2 border-gray-200">
									<th>문서번호</th>
									<th>유형</th>
									<th>제목</th> 
									<th>작성자</th>
									<th>결재상태</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<c:choose>
			                            <c:when test="${empty approvalList}">
			                                <tr>
			                                    <td colspan="7" class="text-center">결재할 문서가 없습니다.</td>
			                                </tr>
			                            </c:when>
			                            <c:otherwise>
			                                <c:forEach var="approval" items="${approvalList}">
			                                <input type="hidden" name="apprv_id" value="${approval.apprv_id}">
			                                    <tr>
			                                        <td>${approval.doc_num}</td>
			                                        <td>${approval.doc_type}</td>
			                                        <td>${approval.title}</td>
			                                        <td>${approval.name}</td>
			                                        <c:if test="${approval.doc_status eq 'N'}">
				                                        <td>결재진행중</td>
			                                        </c:if>
			                                    </tr>
			                                </c:forEach>
			                            </c:otherwise>
			                        </c:choose>
								</tr>
							</tbody>
						</table>
						<div class="pagination-container text-center">
							<c:if test="${page.totalPage > 1}">
								<ul class="pagination pagination-lg">
									<c:if test="${page.page > 1}">
										<li><a href="./homeList.do?page=${page.page - 1}">&laquo;</a></li>
									</c:if>
		                    
									<c:forEach var="i" begin="${page.stagePage}"
										end="${page.endPage}" step="1">
										<li class="${i == page.page ? 'active' : ''}"><a
											href="./homeList.do?page=${i}">${i}</a></li>
									</c:forEach>
		                    
									<c:if test="${page.page < page.totalPage}">
										<li><a href="./homeList.do?page=${page.page + 1}">&raquo;</a></li>
									</c:if>
								</ul>
							</c:if>
						</div>
					</div>
				</div>
				<!-- 내 서명 -->
				<div class="card list border-light mb-3 shadow p-3 rounded wide-card">
					<div class="card-header">내 서명</div>
					<div class="card-body text-center">
						<img id="signatureImage" src="${loginVo.signSaved}" width="80"
							height="75" style="border: 1px solid black;" />
					</div>
					<div class="card-body text-center">
						<button type="button" class="btn btn-light-secondary"
							onclick="location.href='./signature_manage.do'">서명관리</button>
					</div>
				</div>
			</div>

			<div class="card list border-light mb-3 shadow p-3 rounded wide-card">
				<ul class="nav nav-tabs nav-line-tabs mb-5 fs-6">
					<li class="nav-item"><a class="nav-link active"
						data-bs-toggle="tab" href="#kt_tab_pane_1">임시문서함</a></li>
					<li class="nav-item"><a class="nav-link" data-bs-toggle="tab"
						href="#kt_tab_pane_2">부서문서함</a></li>
				</ul>

				<div class="tab-content" id="myTabContent">
					<div class="tab-pane fade show active" id="kt_tab_pane_1"
						role="tabpanel">
						<table
							class="table table-hover table-rounded table-striped border gy-7 gs-7">
							<thead>
								<tr
									class="fw-semibold fs-6 text-gray-800 border-bottom-2 border-gray-200">
									<th>발급번호</th>
			        				<th>유형</th>
			        				<th>제목</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty lists}">
										<tr>
											<td colspan="4" class="text-center text-muted">문서가 없습니다.</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="vo" items="${lists}">
											<tr>
												<td>${vo.doc_num}</td>
												<td>${vo.doc_type}</td>
												<td>${vo.title}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>

						<div class="pagination-container text-center">
							<c:if test="${page.totalPage > 1}">
								<ul class="pagination pagination-lg">
									<c:if test="${page.page > 1}">
										<li><a href="./homeList.do?page=${page.page - 1}">&laquo;</a></li>
									</c:if>

									<c:forEach var="i" begin="${page.stagePage}"
										end="${page.endPage}" step="1">
										<li class="${i == page.page ? 'active' : ''}"><a
											href="./homeList.do?page=${i}">${i}</a></li>
									</c:forEach>

									<c:if test="${page.page < page.totalPage}">
										<li><a href="./homeList.do?page=${page.page + 1}">&raquo;</a></li>
									</c:if>
								</ul>
							</c:if>
						</div>
					</div><!-- 임시문서함 탭 끝 -->

					<div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel">
						<table
							class="table table-hover table-rounded table-striped border gy-7 gs-7">
							<thead>
								<tr
									class="fw-semibold fs-6 text-gray-800 border-bottom-2 border-gray-200">
									<th>발급번호</th>
			        				<th>유형</th>
			        				<th>제목</th>
			        				<th>작성자</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty successlists}">
										<tr>
											<td colspan="4" class="text-center text-muted">등록된 문서가 없습니다.</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="vo" items="${successlists}">
											<tr>
												<td>${vo.doc_num}</td>
												<td>${vo.doc_type}</td>
												<td>${vo.title}</td>
												<td>${vo.name}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>

						<div class="pagination-container text-center">
							<c:if test="${page.totalPage > 1}">
								<ul class="pagination pagination-lg">
									<c:if test="${page.page > 1}">
										<li><a href="./homeList.do?page=${page.page - 1}">&laquo;</a></li>
									</c:if>

									<c:forEach var="i" begin="${page.stagePage}"
										end="${page.endPage}" step="1">
										<li class="${i == page.page ? 'active' : ''}"><a
											href="./homeList.do?page=${i}">${i}</a></li>
									</c:forEach>

									<c:if test="${page.page < page.totalPage}">
										<li><a href="./homeList.do?page=${page.page + 1}">&raquo;</a></li>
									</c:if>
								</ul>
							</c:if>
						</div>
					</div><!-- 부서문서함 탭 끝 -->
				</div><!-- tab-content 끝 -->

			</div>

		</div>
	</div>
</body>
</html>
