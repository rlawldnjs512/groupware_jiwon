<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Home</title>
	<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script type="text/javascript" src="./js/attendance.js"></script>
	<script type="text/javascript" src="./js/chart.js"></script>	
	<link rel="stylesheet" href="./css/calendar.css">
	<link rel="stylesheet" href="./css/home.css">
	
</head>
<%@ include file="sidebar.jsp"%>
<style type="text/css">
		a {
		    color: inherit; /* 부모 요소의 색상을 상속 */
		    text-decoration: none; /* 밑줄 제거 */
		  }
		  
		.btn-light-secondary:hover {
		    background-color: #e9ecef; /* 약간 어두운 배경색 */
		    transform: scale(1.05); /* 살짝 확대 */
		    transition: all 0.3s ease; /* 부드러운 전환 효과 */
		}
</style>
<body>
	<div class="content" id="content">
		<div>
			<input type="hidden" id="extraTime" value="${extraTime}"> 
			<input type="hidden" id="attendType" value="${attendType}">
			<input type="hidden" id="role" value="${sessionScope.loginVo.role}">
		</div>

		<div class="main-content">
		
			<c:if test="${sessionScope.loginVo.role eq 'U'}">
				<div class="user">
					<div class="card-container 1">
						<div class="card border-light mb-3 shadow p-3 rounded attendance">
							<div class="profile-image-area"><img src="${profileImg}"></div>
							<div class="card-body">
								<h3 class="card-title">${empName}</h3>
								<p>${deptName}</p>
			
								<div class="card-text">
								
									<div class="attendTime">
										<div>
											<span id="clockInTime" style="font-weight: bold;">${clockIn}</span><br>
											<small class="text-body-secondary">출근 시간</small>
										</div>
										<div>
											<span id="clockOutTime" style="font-weight: bold;">${clockOut}</span><br>
											<small class="text-body-secondary">퇴근 시간</small>
										</div>
									</div>
			
									
									<div class="progress" role="progressbar" aria-label="Animated striped example" 
										 aria-valuenow="${progress}" aria-valuemin="0" aria-valuemax="100" style="position: relative;">
										<div class="progress-bar progress-bar-striped progress-bar-animated" style="width: ${progress}%">
											<span style="font-weight: bold;">${progress}%</span>
										</div>
									</div>
			
			
									<div class="attendButton">
										<form id="clockInForm" action="/insertAttendance" method="post">
											<button type="submit" class="btn btn-outline-secondary btn-lg"
												id="clockIn" <c:if test="${isClockedIn}">disabled</c:if>>출근
											</button>
										</form>
										<form id="clockOutForm">
											<button type="button" class="btn btn-outline-secondary btn-lg"
												id="clockOut" <c:if test="${!isClockedIn}">disabled</c:if>>퇴근
											</button>
										</form>
									</div>
									
								</div> <!-- card-text -->
							</div> <!-- card-body -->
						</div> <!-- attendance card -->
					
						<div class="card border-light mb-3 shadow p-3 rounded board">
						    <ul class="nav nav-tabs nav-line-tabs mb-5 fs-6">
						        <li class="nav-item">
						            <a class="nav-link active" data-bs-toggle="tab" href="#kt_tab_pane_1">공지사항</a>
						        </li>
						        <li class="nav-item">
						            <a class="nav-link" data-bs-toggle="tab" href="#kt_tab_pane_2">커뮤니티</a>
						        </li>
						    </ul>
		
						    <div class="tab-content" id="myTabContent">
						        <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel">
						        	
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
								                </c:otherwise>
								            </c:choose>
								        </tbody>
								    </table>
								    <div style="display: flex; justify-content: flex-end; padding-right: 30px;">
									  <a href="./notice.do" style="color: blue;">더보기</a>
									</div>
						        </div> <!-- 공지사항 탭 끝 -->
			
						        <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel">
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
									             </c:otherwise>
									         </c:choose>
									     </tbody>
									 </table>
									 <div style="display: flex; justify-content: flex-end; padding-right: 30px;">
									  <a href="./free.do" style="color: blue;">더보기</a>
									</div>
						        </div> <!-- 커뮤니티 탭 끝 -->
						        
						    </div> <!-- tab-content 끝 -->
						</div> <!-- board card 끝 -->
					</div> <!-- card-container 1 -->
				
				
					<div class="card-container 2">
						<div class="card border-light mb-3 shadow p-3 rounded approval">
							<br>
							<a href="./approval_receive.do">
								<button class="btn btn-light-secondary" style="font-size: 20px;"><b>결재할 문서</b> <br><br> ${myCnt}개</button>
							</a>
							<br><br>	
							<a href="./approval_mine.do">
								<button class="btn btn-light-secondary" style="font-size: 20px;"><b>진행중인 결재</b> <br><br> ${continueCnt}개</button>
							</a>
							<br><br>			
							<a href="./temp_store.do">
								<button class="btn btn-light-secondary" style="font-size: 20px;"><b>임시저장된 결재</b> <br><br> ${tempCnt}개</button>
							</a>					
						</div> <!-- approval card -->
						<div class="card border-light mb-3 shadow p-3 rounded calendar">
							<div id='calendar'></div>
						</div> <!-- calendar card -->
					</div> <!-- card-container 2 -->
				</div> <!-- user 끝 -->
			</c:if> <!-- 사원 로그인 -->
			
			
			<c:if test="${sessionScope.loginVo.role eq 'A'}">
				<div class="admin">
				<div class="d-flex">
					<div class="card border-light mb-3 shadow p-3 rounded">
					  <div class="card-header">
					  	<img src="./images/work.svg">
					  </div>
					  <div class="card-body d-flex flex-column justify-content-between">
					    <h4 class="card-title d-flex align-items-end">
					    	${avgClockInTimeAll}
					    </h4>
					    <p class="card-text d-flex align-items-end">직원 평균 출근</p>
					  </div>
					</div>
					<div class="card border-light mb-3 shadow p-3 rounded">
					  <div class="card-header">
					  	<img src="./images/gohome.svg">
					  </div>
					  <div class="card-body d-flex flex-column justify-content-between">
					    <h4 class="card-title d-flex align-items-center">
					    	${avgClockOutTimeAll}
					    </h4>
					    <p class="card-text d-flex align-items-end">직원 평균 퇴근</p>
					  </div>
					</div>
					<div class="card border-light mb-3 shadow p-3 rounded">
					  <div class="card-header">
					  	<img src="./images/avg.svg">
					  </div>
					  <div class="card-body d-flex flex-column justify-content-between">
					    <h4 class="card-title d-flex align-items-center">
					    	${avgWorkTimeAll}
					    </h4>
					    <p class="card-text d-flex align-items-end">일 평균 근무</p>
					  </div>
					</div>
					<div class="card border-light mb-3 shadow p-3 rounded">
					  <div class="card-header">
					  	<img src="./images/late.svg">
					  </div>
					  <div class="card-body d-flex flex-column justify-content-between">
					    <h4 class="card-title d-flex align-items-center">
					    	${lateToday}명
					    </h4>
					    <p class="card-text d-flex align-items-end">오늘 지각한 사원</p>
					  </div>
					</div>
					<div class="card border-light mb-3 shadow p-3 rounded">
					  <div class="card-header">
					  	<img src="./images/vacation.svg">
					  </div>
					  <div class="card-body d-flex flex-column justify-content-between">
					    <h4 class="card-title d-flex align-items-center">
					    	2명
					    </h4>
					    <p class="card-text d-flex align-items-end">오늘 휴가 사용</p>
					  </div>
					</div>
				</div>
			
				<div class="chart-container">
				<div class="card border-light mb-3 shadow p-3 rounded" style="width: 880px;">
					<canvas id="lateEmpChart" style="margin-right: 100px;"></canvas>
				</div>
				<div class="card border-light mb-3 shadow p-3 rounded" style="width: 450px;">
					<canvas id="departmentWorkChart"></canvas>
				</div>
				</div>
					
				</div> <!-- admin 끝 -->
			</c:if> <!-- 관리자 로그인 -->

			
		</div> <!-- main-content -->
	</div> <!-- content -->
	
	<!-- 캘린더 js -->
	<script>
		document.addEventListener('DOMContentLoaded', async function(event) {
			var calendarEl = document.getElementById('calendar');
			
			async function fetchEvents() {
		        try {
		            const response = await fetch('./homeList.do/calendar'); // API 호출
		            if (!response.ok) {
		                throw new Error('Network response was not ok');
		            }
		            const data = await response.json(); // JSON 변환
		            return data; // 데이터 반환
		        } catch (error) {
		            console.error('There was a problem with the fetch operation:', error);
		            return []; // 오류 발생 시 빈 배열 반환
		        }
		    }
	
			var calendar = new FullCalendar.Calendar(calendarEl, {
				timeZone : 'UTC',
				initialView : 'dayGridWeek',
				// height : '100%',
				events : async function (fetchInfo, successCallback, failureCallback) {
					// API에서 이벤트 데이터를 비동기 가져오기
		            let sampleData = await fetchEvents();
	
		            // FullCalendar에 맞게 데이터 매핑
		            let events = sampleData.map(event => ({
		                title: event.title,
		                start: event.start,
		                end: event.end,
		                backgroundColor: event.color, // 배경색
		            }));
	
		            // FullCalendar에 이벤트 데이터 전달
		            successCallback(events);
				}
			});
			calendar.render();
		});
	</script>
	
	

</body>
</html>