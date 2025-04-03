<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- SweetAlert CDN -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="css/forgot.css">
<title>비밀번호 재설정 화면</title>
</head>
<body>
<div class="container">
    <h2 class="header">
     <img src="images/forgot.svg" alt="아이콘" style="width: 70px; height: 80px;">
    </h2>
    
    <!-- 1단계 인증 폼 -->
    <form action="/check.do" method="GET" class="card">
        <table>
            <tr>
                <td>이름</td>
                <!-- 이름 입력 값 유지 -->
                <td><input id="name" type="text" name="name" placeholder="이름" required value="${param.name}" class="input-field"></td>
            </tr>

            <tr>
                <td>사원번호</td>
                <!-- 사원번호 입력 값 유지 -->
                <td><input id="emp_id" type="text" name="emp_id" placeholder="사원번호" required value="${param.emp_id}" class="input-field"></td>
            </tr>
            
            <tr>
                <td colspan="2">
                    <input type="submit" value="1단계 인증" class="btn">
                </td>
            </tr>
        </table>
    </form>

    <!-- 이메일 입력 폼: 사원번호가 일치할 경우에만 보여짐 -->
    <c:if test="${empIdExists}">
    <form id="emailForm" action="/sendEmail.do" method="POST" class="card">
        <input type="hidden" name="emp_id" value="${emp_id}" />
        <input type="hidden" name="name" value="${name}" />
        
        <table>
            <tr>
                <td>Email</td>
                <td>
                    <input type="text" id="emailInput" name="emailInput" class="input-field"> @ 
                    <select id="emailDomain" name="emailDomain" class="input-field">
                        <option>naver.com</option>
                        <option>gmail.com</option>
                        <option>daum.net</option>
                        <option>nate.com</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="전송" class="btn" onclick="submitEmailForm(); return false;">
                </td>
            </tr>
        </table>
    </form>
    </c:if>

    <!-- 인증번호 입력 폼: 이메일이 존재할 때만 보임 -->
    <c:if test="${emailExists}">
    <form id="authForm" class="card">
        <input type="hidden" name="emp_id" value="${emp_id}" />
        <table>
            <tr>
                <td>인증번호</td>
                <td><input id="check" type="text" name="check" class="input-field"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="button" value="2단계인증" class="btn" onclick="checkAuthKey();">
                </td>
            </tr>
        </table>
    </form>
    </c:if>

    <!-- 이메일 인증 후 새 비밀번호 폼 -->
    <div id="passwordForm" style="display:none;">
    <form action="/resetPassword.do" method="POST" onsubmit="return checkPassword();" class="card">
        <input type="hidden" name="emp_id" value="${sessionScope.emp_id}" />
        <table>
            <tr>
                <td>새비밀번호</td>
                <td><input id="password" type="password" name="password" required class="input-field"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="저장" class="btn">
                </td>
            </tr>
        </table>
    </form>
    </div>
</div>

<script type="text/javascript">
    <!-- 페이지 로드 후 alert 메시지가 있을 경우 SweetAlert으로 띄우기 -->
    <c:if test="${not empty alertMessage}">
        Swal.fire({
            text: '${alertMessage}', // Controller에서 전달된 alertMessage
            icon: '${alertType}',  // 'success' or 'error'를 받아서 알림의 타입을 설정
            confirmButtonColor: '#4CAF50'  // 버튼 색상 변경
        });
    </c:if>
    
    function submitEmailForm() {
        var emailInput = document.getElementById("emailInput").value.trim(); // 이메일 입력값에서 공백 제거
        var emailDomain = document.getElementById("emailDomain").value.trim(); 
        var fullEmail = emailInput + "@" + emailDomain;  

       
        var emailField = document.createElement("input");
        emailField.type = "hidden";
        emailField.name = "email";  // 서버에서 받을 파라미터 이름을 "email"로 설정
        emailField.value = fullEmail;

        // 서버로 보낼 폼에 hidden input 추가
        var form = document.getElementById("emailForm");
        form.appendChild(emailField);

        // 폼 전송
        form.submit();
    }
    
    function checkPassword() {
        var password = document.getElementById("password").value;

        // 비밀번호 유효성 검사: 영문자, 숫자를 포함한 8자리 이상
        var passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

        if (!passwordRegex.test(password)) {
            // 유효성 검사 실패 시 경고 메시지
            Swal.fire({
                text: "영문자와 숫자를 포함하여 8자리 이상이어야 합니다.",
                icon: 'error',
                confirmButtonColor: '#FF0000'
            });
            return false; // 폼 제출을 막음
        }
        
        return true; // 유효성 검사 성공 시 폼 제출
    }

    function checkAuthKey() {
        var inputAuthKey = document.getElementById("check").value.trim();
        var serverAuthKey = "${authKey}";  // 서버에서 전달된 authKey

        // 인증번호 비교
        if (inputAuthKey === serverAuthKey) {
            Swal.fire({
                text: "인증번호가 일치합니다.",
                icon: 'success',
                confirmButtonColor: '#4CAF50'
            }).then(() => {
                // 인증번호가 일치하면 새 비밀번호 입력 폼을 보여줌
                document.getElementById("passwordForm").style.display = "block";
            });
        } else {
            // 인증 실패
            Swal.fire({
                text: "인증번호가 틀렸습니다.",
                icon: 'error',
                confirmButtonColor: '#FF0000'
            });
        }
    }    
</script>

</body>
</html>
