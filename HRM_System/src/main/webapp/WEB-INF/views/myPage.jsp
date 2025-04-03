<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>My Page</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="./css/mypage.css">
</head>
<%@ include file="sidebar.jsp"%>
<body>
	<div class="content" id="content">
		<%@ include file="header.jsp" %>
		<div class="main-content">
			<section class="myPage-content">
				<form action="./profileUpload.do" method="POST" name="myPageFrm"
					id="profileFrm" enctype="multipart/form-data"
					onsubmit="return false;">
					<input type="hidden" name="emp_id" value="${employee.emp_id}">
					<section class="profile-info-container">
						<div class="profile-section">
							<div class="profile-image-area">
									<c:choose>
									    <c:when test="${not empty employee.profile_image}">
									        <img src="${employee.profile_image}" id="profileImage">
									    </c:when>
									    <c:otherwise>
									        <img src="/resources/images/user.png" id="profileImage">
									    </c:otherwise>
									</c:choose>
							</div>
							<div class="profile-btn-area">
								<label for="imageInput" class="button-common">
								 <img src="images/picture.svg" alt="아이콘" style="width: 30px; height: 30px;">
								</label> <input
									type="file" name="profileImage" id="imageInput"
									accept="image/*" hidden onchange="previewImage(event)">
								<button type="submit" class="button-common"
									onclick="uploadProfileImage()">
									 <img src="images/chk.svg" alt="아이콘" style="width: 30px; height: 30px;">
									</button>
							</div>
						</div>
					</section>
				</form>


				<div class="info-section">

					<!-- 마이페이지 정보 -->
					<table class="info-table">
						<tr>
							<th>사원번호</th>
							<td>${employee.emp_id}</td>
						</tr>
						<tr>
							<th>이름
							</th>
							<td id="nameDisplay">${employee.name}
							</td>
							
						</tr>
						<tr>
							<th>직위</th>
							<td id="positionDisplay">${employee.position}</td>
						</tr>
						<tr>
							<th>생년월일</th>
							<td id="birthDisplay">${employee.birth}</td>
						</tr>
						<tr>
							<th>연락처</th>
							<td id="phoneDisplay">${employee.phone}</td>
						</tr>
						<tr>
							<th>내선번호</th>
							<td id="telDisplay">${employee.tel}</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td id="emailDisplay">${employee.email}</td>
						</tr>
						<tr>
							<th>입사일</th>
							<td id="hireDisplay">${employee.hire_date}</td>
						</tr>
						<tr>
							<th>부서</th>
							<td id="deptDisplay">${employee.dept_name}</td>
						</tr>
					</table>

					<button class="button-common" type="button" onclick="openModal()">
					<img src="images/pen.svg" alt="아이콘" style="width: 30px; height: 30px;">
					</button>
				</div>
			</section>


		</div>
	</div>

	<!-- 수정 모달 -->
	<div class="modal" id="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>

        <form action="./mypageupload.do" method="POST" id="editForm" onsubmit="return validateForm()">
            <table class="info-table">
		
                <tr>
                    <td><label for="emp_id">사원번호</label></td>
                    <td><input type="text" id="emp_id" name="emp_id" value="${employee.emp_id}" disabled></td>
                </tr>
                <tr>
                    <td><label for="name">이름</label></td>
                    <td><input type="text" id="name" name="name" value="${employee.name}"></td>
                </tr>
                <tr>
                    <td><label for="position">직위</label></td>
                    <td><input type="text" id="position" name="position" value="${employee.position}" disabled></td>
                </tr>
                <tr>
                    <td><label for="dept_name">부서</label></td>
                    <td><input type="text" id="dept_name" name="dept_name" value="${employee.dept_name}" disabled></td>
                </tr>
                <tr>
                    <td><label for="birth">생년월일</label></td>
                    <td><input type="text" id="birth" name="birth" value="${employee.birth}"></td>
                </tr>
                <tr>
                    <td><label for="phone">연락처</label></td>
                    <td><input type="text" id="phone" name="phone" value="${employee.phone}"></td>
                </tr>
                <tr>
                    <td><label for="tel">내선번호</label></td>
                    <td><input type="text" id="tel" name="tel" value="${employee.tel}"></td>
                </tr>
                <tr>
                    <td><label for="email">이메일</label></td>
                    <td><input type="email" id="email" name="email" value="${employee.email}"></td>
                </tr>
                <tr>
                    <td><label for="hire_date">입사일</label></td>
                    <td><input type="text" id="hire_date" name="hire_date" value="${employee.hire_date}" disabled></td>
                </tr>

            </table>
            <button type="submit" class="button-common">
                <img src="images/chk.svg" alt="아이콘" style="width: 30px; height: 30px;">
            </button>
        </form>
    </div>
</div>


</body>



<script>
	
function validateForm() {
    var name = document.getElementById("name").value;
    var birth = document.getElementById("birth").value;
    var phone = document.getElementById("phone").value;
    var email = document.getElementById("email").value;
    var tel = document.getElementById("tel").value;

    var namePattern = /^[a-zA-Z가-힣\s]+$/;
    if (!namePattern.test(name)) {
        alert("이름은 숫자나 특수문자를 사용할 수 없습니다.");
        return false;
    }

    var birthPattern = /^\d{8}$/;
    if (!birthPattern.test(birth)) {
        alert("생년월일은 8자리 숫자 형식이어야 합니다. (예: 19761205)");
        return false;
    }

    var phonePattern = /^\d{3}-\d{4}-\d{4}$/;
    if (!phonePattern.test(phone)) {
        alert("연락처는 xxx-xxxx-xxxx 형식이어야 합니다.");
        return false;
    }

    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (!emailPattern.test(email)) {
        alert("이메일은 (아이디)@(주소) 형식이어야 합니다.");
        return false;
    }



    return true;  
}














	//사진 미리보기 
	function previewImage(event) {
		var reader = new FileReader();

		reader.onload = function() {
			var output = document.getElementById('profileImage');
			output.src = reader.result; // 파일을 읽은 후 미리보기 이미지 업데이트
		};

		reader.readAsDataURL(event.target.files[0]); // 파일 읽기
	}

	
	// 프로필 이미지 업로드 AJAX
	function uploadProfileImage() {
		var formData = new FormData(document.getElementById('profileFrm'));

		
				$.ajax({
					url : '/profileUpload.do',
					type : 'POST',
					data : formData,
					processData : false,
					contentType : false,
					success : function(response) {
						if (response.status === 'success') {
							alert('프로필 이미지가 업데이트되었습니다.');
							$('#profileImage').attr('src',
									response.profile_image_path); // 이미지 변경
						} else {
							alert('파일 업로드 실패: ' + response.message);
						}
					},
					error : function(xhr, status, error) {
						console.error("AJAX 요청 실패:", status, error);
						alert('파일 업로드 중 오류가 발생했습니다.');
					}
				});

		return false; // 폼 제출 방지
	}

	// 수정 버튼 클릭 시 모달 열기
	function openModal() {
		document.getElementById("modal").style.display = "block"; // 모달 보이기
	}

	// 모달 닫기
	function closeModal() {
		document.getElementById("modal").style.display = "none"; // 모달 숨기기
	}
</script>
</body>
</html>
