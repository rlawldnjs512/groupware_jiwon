<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table class="table table-hover">
        <thead>
            <tr class="fw-semibold fs-6 text-gray-800 border-bottom-2 border-gray-200">
                <th>ID</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty noticeLists}">
                    <tr>
                        <td colspan="4" class="text-center text-muted">등록된 공지사항이 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="vo" items="${noticeLists}">
                        <tr>
                            <td>${vo.not_id}</td>
                            <td>${vo.title}</td>
                            <td>${vo.name}</td>
                            <td>${vo.regdate}</td>
                        </tr>
                    </c:forEach>
                    <c:set var="currentSize" value="${fn:length(noticeLists)}"/>
         <c:forEach var="i" begin="1" end="${5 - currentSize}">
             <tr>
                 <td colspan="4">&nbsp;</td>
             </tr>
         </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <div class="pagination-container text-center">
		<c:if test="${noticePageDto.totalPage > 1}">
		   <ul class="pagination pagination-lg">
		       <c:if test="${noticePageDto.page > 1}">
		           <li><a href="./homeList.do?noticePage=${noticePageDto.page - 1}">&laquo;</a></li>
		       </c:if>
		
		       <c:forEach var="i" begin="${noticePageDto.stagePage}" end="${noticePageDto.endPage}" step="1">
		           <li class="${i == noticePageDto.page ? 'active' : ''}">
		               <a href="./homeList.do?noticePage=${i}">${i}</a>
		           </li>
		       </c:forEach>
		
		       <c:if test="${noticePageDto.page < noticePageDto.totalPage}">
		           <li><a href="./homeList.do?noticePage=${noticePageDto.page + 1}">&raquo;</a></li>
		       </c:if>
		   </ul>
		</c:if>
	</div>





</body>
</html>