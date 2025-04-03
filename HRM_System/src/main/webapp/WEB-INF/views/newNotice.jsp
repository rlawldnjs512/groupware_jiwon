<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/29.1.0/classic/ckeditor.js"></script>
	<script type="text/javascript" src="./js/newNotice.js"></script>
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

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: left;
}

th {
	background-color: #f0f0f0;
}

.gray {
	background-color: #ddd;
}

.container {
	width: 80%;
	margin: 0 auto;
}

.form-group {
	margin-bottom: 15px;
}

label {
	font-weight: bold;
	display: block;
}

input[type="text"], textarea {
	width: 100%;
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

input[type="file"] {
	margin-top: 5px;
}

button {
	padding: 10px 15px;
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
}

button:hover {
	background-color: #45a049;
}

.file-container {
	display: flex;
	align-items: center;
}

.file-container input[type="file"] {
	flex-grow: 1;
}

.file-container button {
	margin-left: 10px;
}

textarea {
	height: 200px;
}
</style>
</head>
<%@ include file="sidebar.jsp" %>
<body>
    <div class="content" id="content">
		<%@ include file="header.jsp" %>
        <div class="main-content">
		    <h1 class="page-heading d-flex text-gray-900 fw-bold fs-3 flex-column justify-content-center my-0">
					공지사항 글 등록하기</h1>
		    <form action="/submitNotice.do" method="post" enctype="multipart/form-data">
			    <table>
			        <!-- 작성자 -->
			        <tr>
			            <td><label for="name">작성자</label></td>
			            <td>${loginVo.name}</td>
			        </tr>
			
			        <!-- 제목 -->
			        <tr>
			            <td><label for="title">제목</label></td>
			            <td><input type="text" id="title" name="title" required placeholder="제목을 입력하세요"></td>
			        </tr>
			        
			        <!-- 기한 -->
			        <tr>
			            <td><label for="expired">기한</label></td>
			            <td>
			            	<input type="date" id="expired" name="expired">
			            </td>
			        </tr>
			
			        <!-- 첨부파일 -->
			        <tr>
			            <td><label for="file">첨부파일</label></td>
			            <td>
			                <div class="file-container">
			                    <input class="form-control form-control-sm" id="formFileSm" type="file" name="file">
			                </div>
			            </td>
			        </tr>
			
			        <!-- 내용 -->
			        <tr>
			            <td><label for="content">내용</label></td>
			            <td>
			                <textarea class="form-control" id="classic" name="content" placeholder="내용을 입력하세요"></textarea>
			            </td>
			        </tr>
			        
			        <!-- 제출 버튼 -->
			        <tr class="form-group" style="text-align: right;">
			            <td colspan="2" class="submit-btn">
			                <button type="submit" class="btn btn-light-primary ms-2" id="newNotice">등록하기</button>
			                <button class="btn btn-light-primary ms-2" onclick="history.back(-1)">취소</button>
			            </td>
			        </tr>
			    </table>
			</form>
		</div>
    </div>
    <script>
		ClassicEditor
	    .create(document.querySelector('#classic'), {
	        // 높이 설정
	        height: 400, // 높이를 400px로 설정
	        
	        // 툴바 설정 (옵션에 따라 조정)
	        toolbar: [
	            'bold', 'italic', 'link', 'undo', 'redo' // 툴바에 포함될 버튼들
	        ],
	        // 기본 글꼴 크기 및 스타일 설정
	        fontSize: '11px', // 기본 글꼴 크기
	    })
	    .catch(error => {
	        console.error(error);
	    });
    </script>
</body>
</html>
