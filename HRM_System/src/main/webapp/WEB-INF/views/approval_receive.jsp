<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="./css/emplist.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <meta charset="UTF-8">
    <title>결재 수신함</title>

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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        thead th {
		    padding: 12px;
		    text-align: center !important;  /* text-align에 !important 추가 */
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
            <div>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th  style="text-align: center;">문서번호</th>
                            <th  style="text-align: center;">유형</th>
                            <th  style="text-align: center;">제목</th>
                            <th  style="text-align: center;">작성자</th>
                            <th  style="text-align: center;">결재상태</th>
                            
                            <th style="text-align: center;">상세보기</th>
                        </tr>
                    </thead>
                    <tbody>
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
                                        <td>진행중</td>
                                     
                                        <td>
										    <a href="./approval_detail.do?doc_id=${approval.doc_id}&apprv_id=${approval.apprv_id}"
										       class="btn btn-light-success">상세보기</a>
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
</html>
