<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>비밀번호 재설정</title>
    <link rel="stylesheet" href="./css/newPw.css">

</head>

<body>
    <div class="content" id="content">
        <div class="main-content">
            <div class="card">
                <div class="card-header">
                    <img src="images/pw.svg" alt="아이콘" style="width: 70px; height: 50px;">
                </div>
                <div class="card-body">
                    <form action="/updatePassword.do" method="POST" onsubmit="return checkPassword();">
                        <div class="form-group">
                            <label for="newPassword">새 비밀번호 :</label>
                            <input type="password" id="newPassword" name="newPassword" required>
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">새 비밀번호 확인 :</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" required>
                        </div>
                        <button type="submit" class="btn">변경</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>

<script type="text/javascript">
    // 비밀번호 유효성 검사 함수
    function checkPassword() {
        var newPassword = document.getElementById("newPassword").value;
        var confirmPassword = document.getElementById("confirmPassword").value;

        var passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

        if (!passwordRegex.test(newPassword)) {
            // 유효성 검사 실패 시 경고 메시지
            Swal.fire({
                text: "비밀번호는 영문자와 숫자를 포함하여 최소 8자리 이상이어야 합니다.",
                icon: 'error',
                confirmButtonColor: '#FF0000'
            });
            return false; // 폼 제출을 막음
        }

        if (newPassword !== confirmPassword) {
            Swal.fire({
                text: "비밀번호가 일치하지 않습니다",
                icon: 'error',
                confirmButtonColor: '#FF0000'
            });
            return false;
        }

        return true;
    }
</script>

</html>







