<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>휴가원 신청</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/approval.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
    <script src="./js/approval_line.js"></script>
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

th, td {
	align-content: center;
}

#reason {
	min-height: 500px; /* 최소 높이 설정 */
	max-height: 600px; /* 최대 높이 설정 */
	overflow-y: auto; /* 높이를 넘어서면 스크롤 */
}

</style>
</head>
<%@ include file="sidebar.jsp" %>
<body>
    <div class="content" id="content">
        <div class="main-content">
        	<form method="post" enctype="multipart/form-data">
	            <div class="card p-4" style="width: 1300px;">
	                <h2 class="text-center mb-4">휴가원</h2>
	                <div>
						<div style="float: left; width: 400px; height: 120px;">
							<table class="table table-bordered mb-3" style="border: 2px solid black">
								<col width="80px">
								<col width="80px">
								<col width="80px">
								<col width="80px">
								<col width="80px">
								<tr style="height: 20px;">
									<th rowspan="2">기안자</th>
									<th>부서</th>
									<th>직급</th>
									<th>성명</th>
									<th>사원번호</th>
								</tr>
								<tr style="height: 100px;">
									<td>${loginVo.dept_name}</td>
									<td>${loginVo.position}</td>
									<td>${loginVo.name}</td>
									<td>${loginVo.emp_id}</td>
								</tr>
							</table>
						</div>
						<div style="float: right; width: 800px">
							<table class="table table-bordered mb-3" style="border: 2px solid black">
								<col width="180px">
								<col width="180px">
								<col width="180px">
								<col width="180px">
								<col width="180px">
								<tr id="approvalLineTd_1" style="height: 20px;">
									<th rowspan="2">결재</th>
									<th>본인</th>
									
								</tr>

								<tr  style="height: 100px;">
									<td>
										<img id="signatureImage" src="${loginVo.signSaved}" style="width: 50%; height: auto; object-fit: contain;"/>
									</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
						</div>
					</div>
	
					<input type="hidden" id="doc_type" name="doc_type"
								class="form-control" value="휴가">
					<table class="table table-bordered mb-3">
						<tr>
							<th>제목</th>
							<th>
							<input type="text" id="title" name="title" class="form-control"
       placeholder="제목을 입력하세요." value="${param.title}">

								</th>
						</tr>
						<tr>
							<th>종류</th>
							<th style="text-align: left;">
							   <input type="radio" id="morning" name="type" value="오전반차" class="form-check-input" checked>
							    <label for="morning" class="form-check-label">오전반차</label>
							    
							    <input type="radio" id="afternoon" name="type" value="오후반차" class="form-check-input ms-2"> 
							    <label for="afternoon" class="form-check-label">오후반차</label>
							    
							    <input type="radio" id="full" name="type" value="연차" class="form-check-input ms-2"> 
							    <label for="full" class="form-check-label">연차</label>
							</th>

						</tr>
						<tr>
							<th>기간</th>
							<th style="text-align: left;">
								<input type="date" class="form-control" name="leave_start" style="width: 30%; display: inline-block;"> ~ <input type="date" class="form-control" name="leave_end" style="width: 30%; display: inline-block;">
							</th>
						</tr>
						<tr>
						    <th>사유</th>
						    <th>
						        <textarea id="reason" name="content" class="form-control" rows="5" placeholder="사유를 입력하세요.">
						            <c:out value="${empty param.content ? '' : param.content}" />
						        </textarea>
						    </th>
							</tr>

					</table>
	                <div class="d-flex justify-content-end mb-3">
						<button type="button" id="line" onclick="windowOpen()" class="btn btn-light-primary ms-2">결재선 선택</button>
						<button type="button" name="reportTemp" class="report btn btn-light-primary ms-2">임시 저장</button>
						<button type="button" class="btn btn-light-primary ms-2" onclick="history.back(-1)">취소</button>
						<button type="button" name="leaveApproval" class="report btn btn-light-primary ms-2">상신 하기</button>
					</div>
	            </div>
			</form>
        </div>
    </div>
	<script type="text/javascript">
	
	function line(approvalLine) {
	    let row1 = document.getElementById("approvalLineTd_1");
	    let frm = document.forms[0];

	    approvalLine.forEach(person => {
	        
	        let td_1 = document.createElement("th");
	        td_1.textContent = person.name;
	        row1.appendChild(td_1);
	        
	        let input_1 = document.createElement("input");
	        input_1.setAttribute("type", "hidden");
	        input_1.setAttribute("name", "appLine");
	        input_1.value = person.id;
	        
	        // 순서 유지하면서 추가하기 위해 appendChild 사용
	        frm.appendChild(input_1);
	    });
	}
	
	
	document.querySelector("form").addEventListener("submit", function(event) {
	    const selectedType = document.querySelector('input[name="type"]:checked');
	    if (!selectedType) {
	        alert("휴가 종류를 선택해주세요.");
	        event.preventDefault(); 
	    }
	});

	
	
	
	</script>
</body>
</html>