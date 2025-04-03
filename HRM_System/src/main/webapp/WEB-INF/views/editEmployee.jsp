<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 정보 수정</title>
<!-- Bootstrap CSS 추가 -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/editemp.css">
</head>
<%@ include file="sidebar.jsp" %>
<body>
    <div class="content" id="content">
        <%@ include file="header.jsp"%>
        <div class="main-content">
        
            
           
            <div class="card">
                <div class="card-header">
                     ${employee.emp_id}
                    <img src="images/e.svg" alt="아이콘" style="width: 40px; height: 40px;">
                </div>
                <div class="card-body">
                    <!-- 프로필 사진 -->
                    <div class="text-center">
                        <img src="${empty employee.profile_image ? '/upload/profile/default.png' : employee.profile_image}" class="profile-img" alt="프로필 이미지">
                    </div>

                    <form action="./updateEmployee.do" method="POST">
                        <input type="hidden" name="emp_id" value="${employee.emp_id}">

                        <div class="form-group">
                            <label for="name">이름:</label>
                            <input type="text" id="name" name="name" value="${employee.name}" class="form-control" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="birth">생년월일:</label>
                            <input type="text" id="birth" name="birth" value="${employee.birth}" class="form-control" required>
                        </div>

						<div class="form-group">
							<label for="dept_id">부서:</label> <select id="dept_id"
								name="dept_id" class="form-control" required>
								<option value="1" ${employee.dept_id == 1 ? 'selected' : ''}>1
									- 개발부</option>
								<option value="2" ${employee.dept_id == 2 ? 'selected' : ''}>2
									- 디자인부</option>
								<option value="3" ${employee.dept_id == 3 ? 'selected' : ''}>3
									- 인사부</option>
								<option value="4" ${employee.dept_id == 4 ? 'selected' : ''}>4
									- 재무부</option>
								<option value="5" ${employee.dept_id == 5 ? 'selected' : ''}>5
									- 영업부</option>
								<option value="6" ${employee.dept_id == 6 ? 'selected' : ''}>6
									- 개발부</option>
							</select>
						</div>


						<div class="form-group">
                            <label for="position">직책:</label>
                            <input type="text" id="position" name="position" value="${employee.position}" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="phone">전화번호:</label>
                            <input type="text" id="phone" name="phone" value="${employee.phone}" class="form-control" required>
                        </div>
                        
                         <div class="form-group">
                            <label for="phone">내선번호:</label>
                            <input type="text" id="tel" name="tel" value="${employee.tel}" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="email">이메일:</label>
                            <input type="email" id="email" name="email" value="${employee.email}" class="form-control" required>
                        </div>

                        <div class="button-container">
							<button type="submit" class="btn button-common">
								수정
							</button>
							<a href="javascript:window.history.back();" class="btn button-common">
							뒤로가기
							</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
  

   
</body>
</html>
