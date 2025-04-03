<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>전자결재</title>
    
    <!-- CSS & JS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="./css/emplist.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

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

        thead th {
    text-align: center !important;
    vertical-align: middle !important;
}
tbody td {
    text-align: center;
    vertical-align: middle;
}
      
    </style>
</head>

<body>
    <%@ include file="sidebar.jsp" %>

    <div class="content" id="content">
        <%@ include file="header.jsp" %>

        <div class="main-content">
            <div class="card list border-light mb-3 shadow p-3 rounded wide-card">
                <!-- 탭 메뉴 -->
                <ul class="nav nav-tabs nav-line-tabs mb-5 fs-6">
                    <li class="nav-item"><a class="nav-link active" data-bs-toggle="tab" href="#tab1">진행중인 결재</a></li>
                    <li class="nav-item"><a class="nav-link" data-bs-toggle="tab" href="#tab2">종료된 결재</a></li>
                    <li class="nav-item"><a class="nav-link" data-bs-toggle="tab" href="#tab3">전체 결재</a></li>
                </ul>

                <div class="tab-content" id="myTabContent">
                    <!-- 진행중인 결재 -->
                    <div class="tab-pane fade show active" id="tab1">
                        <table class="table table-hover table-striped border">
                            <thead>
                                <tr>
                                    <th>문서번호</th>
                                    <th>유형</th>
                                    <th>제목</th>
                                    
                                    <th>결재상태</th>
                                    <th>기안일</th>
                                </tr>
                            </thead>
                            <tbody>
    <c:choose>
        <c:when test="${empty docList}">
            <tr><td colspan="5" class="text-center text-muted">결재가 없습니다</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="doc" items="${docList}">
                <c:if test="${doc.doc_status == 'N'}">
                    <tr>
                        <td>${doc.doc_num}</td>
                        <td>${doc.doc_type}</td>
                        <td>${doc.title}</td>
                        <td><span class="badge bg-warning text-dark"><i class="fas fa-hourglass-half"></i> 진행중</span></td>
                        <td><fmt:formatDate value="${doc.create_date}" pattern="yyyy-MM-dd" /></td>
                    </tr>
                </c:if>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</tbody>

                        </table>
                    </div>

                    <!-- 종료된 결재 -->
                    <div class="tab-pane fade" id="tab2">
                        <table class="table table-hover table-striped border">
                            <thead>
                               <tr>
                                    <th>문서번호</th>
                                    <th>유형</th>
                                    <th>제목</th>
                                    
                                    <th>결재상태</th>
                                    <th>기안일</th>
                                </tr>
                            </thead>
                           <tbody>
    <c:choose>
        <c:when test="${empty docList}">
            <tr><td colspan="5" class="text-center text-muted">결재가 없습니다.</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="doc" items="${docList}">
                <c:if test="${doc.doc_status == 'Y'}">
                    <tr>
                        <td>${doc.doc_num}</td>
                        <td>${doc.doc_type}</td>
                        <td>${doc.title}</td>
                        <td>
                        <span class="badge bg-success"><i class="fas fa-check-circle"></i> 결재완료</span></td>
                        <td><fmt:formatDate value="${doc.create_date}" pattern="yyyy-MM-dd" /></td>
                    </tr>
                </c:if>
                <c:if test="${doc.doc_status == 'R'}">
                    <tr>
                        <td>${doc.doc_num}</td>
                        <td>${doc.doc_type}</td>
                        <td>${doc.title}</td>
                        <td><span class="badge bg-warning text-dark"><i class="fas fa-ban"></i> 반려</span></td>
                        <td><fmt:formatDate value="${doc.create_date}" pattern="yyyy-MM-dd" /></td>
                    </tr>
                </c:if>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</tbody>

                        </table>
                    </div>



                    <!-- 전체 결재 -->
                    <div class="tab-pane fade" id="tab3">
                        <table class="table table-hover table-striped border">
                            <thead>
                                <tr>
                                    <th>문서번호</th>
                                    <th>유형</th>
                                    <th>제목</th>
                                    <th>결재상태</th>
                                    <th>기안일</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty docList}">
                                        <tr><td colspan="5" class="text-center text-muted">결재가 없습니다.</td></tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="doc" items="${docList}">
                                            <tr>
                                                <td>${doc.doc_num}</td>
                                                <td>${doc.doc_type}</td>
                                                <td>${doc.title}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${doc.doc_status == 'N'}">
                                                            <span class="badge bg-warning text-dark">
                                                                <i class="fas fa-hourglass-half"></i> 진행중
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${doc.doc_status == 'Y'}">
                                                            <span class="badge bg-success">
                                                                <i class="fas fa-check-circle"></i> 결재완료
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${doc.doc_status == 'R'}">
                                                            <span class="badge bg-warning text-dark">
                                                                <i class="fas fa-ban"></i> 반려
                                                            </span>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td><fmt:formatDate value="${doc.create_date}" pattern="yyyy-MM-dd" /></td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    
                </div>
            </div>    
        </div>
    </div>
</body>
</html>
