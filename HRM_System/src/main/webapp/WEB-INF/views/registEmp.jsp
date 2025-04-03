<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 등록</title>
<link rel="stylesheet" href="./css/editemp.css">
</head>
<%@ include file="sidebar.jsp" %>
<body>
    <div class="content" id="content">
        
        <div class="main-content">
            <div class="card">
                <div class="card-header">
                    사원 등록
                    <img src="images/e.svg" alt="아이콘" style="width: 40px; height: 40px;">
                </div>
                <div class="card-body">
                    <!-- 사원 등록 폼 -->
                    <form action="./regist.do" method="POST" onsubmit="return validateForm()">
                        <div class="form-group">
                            <label for="name">이름:</label>
                            <input type="text" id="name" name="name" class="form-control" required
                                pattern="^[A-Za-z가-힣]+$" title="이름은 숫자나 특수문자를 포함할 수 없습니다.">
                        </div>
                        
                        <div class="form-group">
                            <label for="birth">생년월일: (예: 19981205)</label>
                            <input type="text" id="birth" name="birth" class="form-control" required
                                pattern="^\d{8}$" title="생년월일은 8자리 숫자로 입력해주세요 (예: 19761205)">
                        </div>

                        <div class="form-group">
                            <label for="gender">성별:</label>
                            <select id="gender" name="gender" class="form-control" required>
                                <option value="M">남성</option>
                                <option value="F">여성</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="phone">휴대폰 번호:</label>
                            <input type="text" id="phone" name="phone" class="form-control" required
                                pattern="^\d{3}-\d{4}-\d{4}$" title="휴대폰 번호는 xxx-xxxx-xxxx 형식이어야 합니다. (예: 010-1234-5678)">
                        </div>
                        
                         <div class="form-group">
                            <label for="tel">내선번호:</label>
                            <input type="text" id="tel" name="tel" class="form-control" required>
                        </div>
                        

                        <div class="form-group">
                            <label for="email">이메일:</label>
                            <input type="email" id="email" name="email" class="form-control" required
                                pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" title="이메일은 (아이디)@(주소) 형식이어야 합니다.">
                        </div>
                        
                        <div class="form-group">
                            <label for="dept_id">부서:</label> 
                            <select id="dept_id" name="dept_id" class="form-control" required>
                                <option value="1">1 - 개발부</option>
                                <option value="2">2 - 디자인부</option>
                                <option value="3">3 - 인사부</option>
                                <option value="4">4 - 재무부</option>
                                <option value="5">5 - 영업부</option>
                                <option value="6">6 - 개발부</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="position">직책:</label>
                            <select id="position" name="position" class="form-control" required>
                                <option value="사원">사원</option>
                                <option value="대리">대리</option>
                                <option value="과장">과장</option>
                                <option value="차장">차장</option>
                                <option value="부장">부장</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="hire_date">입사일:</label>
                            <input type="date" id="hire_date" name="hire_date" class="form-control" required
                                pattern="\d{4}-\d{2}-\d{2}" title="입사일은 yyyy-mm-dd 형식이어야 합니다.">
                        </div>

                        <div class="button-container">
                            <button type="submit" class="btn button-common">
                                등록
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

<script>
// 추가적인 유효성 검사 (JavaScript) - 생년월일 등 형식 체크
function validateForm() {
    var birth = document.getElementById("birth").value;
    var phone = document.getElementById("phone").value;
    var email = document.getElementById("email").value;
    
    // 생년월일 유효성 검사
    if (!/^\d{8}$/.test(birth)) {
        alert("생년월일은 8자리 숫자(예: 19981205)로 입력해주세요.");
        return false;
    }

    // 휴대폰 번호 유효성 검사
    if (!/^\d{3}-\d{4}-\d{4}$/.test(phone)) {
        alert("휴대폰 번호는 xxx-xxxx-xxxx 형식이어야 합니다.");
        return false;
    }

    // 이메일 유효성 검사
    if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
        alert("이메일 형식이 올바르지 않습니다.");
        return false;
    }
    
    return true; // 모든 검사를 통과하면 폼 제출
}
</script>
