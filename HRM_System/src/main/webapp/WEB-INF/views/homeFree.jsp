<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
             <c:when test="${empty freeLists}">
                 <tr>
                     <td colspan="4" class="text-center text-muted">등록된 커뮤니티 글이 없습니다.</td>
                 </tr>
             </c:when>
             <c:otherwise>
                 <c:forEach var="vo" items="${freeLists}">
                     <tr>
                         <td>${vo.free_id}</td>
                         <td>${vo.title}</td>
                         <td>${vo.name}</td>
                         <td>${vo.regdate}</td>
                     </tr>
                 </c:forEach>
                 <c:set var="currentSize" value="${fn:length(freeLists)}"/>
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
		<c:if test="${freePageDto.totalPage > 1}">
		<ul class="pagination pagination-lg">
		    <c:if test="${freePageDto.page > 1}">
		        <li><a href="./homeList.do?freePage=${freePageDto.page - 1}">&laquo;</a></li>
		    </c:if>
		
		    <c:forEach var="i" begin="${freePageDto.stagePage}" end="${freePageDto.endPage}" step="1">
		        <li class="${i == freePageDto.page ? 'active' : ''}">
		            <a href="./homeList.do?freePage=${i}">${i}</a>
		        </li>
		    </c:forEach>
		
		    <c:if test="${freePageDto.page < freePageDto.totalPage}">
		        <li><a href="./homeList.do?freePage=${freePageDto.page + 1}">&raquo;</a></li>
		    </c:if>
		</ul>
		</c:if>
	</div>


