<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/7.0.0/normalize.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="./css/loginform.css">
<style type="text/css">
.forgot-password {
	margin-top: 10px;
	text-align: center;
}

.forgot-password a {
	font-size: 14px;
	color: #007bff;
	text-decoration: none;
}

.forgot-password a:hover {
	text-decoration: underline;
}
</style>



</head>
<body>
	<div class="login">
		<div class="form">
			<h2>Sign In</h2>
			<form action="./login.do" method="POST">
				<div class="form-field">
					<label for="login-mail"><i class="fa fa-user"></i></label> <input
						id="emp_id" type="text" name="emp_id" placeholder="사원번호"
						pattern="\d{8}" required>


				</div>
				<div class="form-field">
					<label for="login-password"><i class="fa fa-lock"></i></label> <input
						id="password" type="password" name="password" placeholder="비밀번호"
						pattern=".{6,}" required>

				</div>
				<button type="submit" class="button">
					<div class="arrow-wrapper">
						<span class="arrow"></span>
					</div>
					<p class="button-text">Login</p>
				</button>
				
				<!-- 비밀번호를 잊으셨나요 링크 추가 -->
				<div class="forgot-password">
					<a href="./forgot.do">비밀번호를 잊으셨나요?</a>
				</div>
				
			
				
			</form>
		</div>
	</div>






</body>
</html>
