<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>근태관리</title>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar-scheduler@6.1.15/index.global.min.js"></script>
    <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/resource-timegrid@6.1.15/index.global.min.js'></script>
  	<link rel="stylesheet" href="./css/attendance.css">
</head>
<%@ include file="sidebar.jsp" %>
<body>
	
	<div class="content" id="content">
	<%@ include file="header.jsp" %>
		<div class="main-content">
		
			<div class="d-flex">
				<div class="card border-light mb-3 shadow p-3 rounded">
				  <div class="card-header">
				  	<img src="./images/work.svg">
				  </div>
				  <div class="card-body d-flex flex-column justify-content-between">
				    <h4 class="card-title d-flex align-items-end">
				    	${avgClockInTime}
				    </h4>
				    <p class="card-text d-flex align-items-end">평균 출근 시간</p>
				  </div>
				</div>
				<div class="card border-light mb-3 shadow p-3 rounded">
				  <div class="card-header">
				  	<img src="./images/gohome.svg">
				  </div>
				  <div class="card-body d-flex flex-column justify-content-between">
				    <h4 class="card-title d-flex align-items-center">
				    	${avgClockOutTime}
				    </h4>
				    <p class="card-text d-flex align-items-end">평균 퇴근 시간</p>
				  </div>
				</div>
				<div class="card border-light mb-3 shadow p-3 rounded">
				  <div class="card-header">
				  	<img src="./images/avg.svg">
				  </div>
				  <div class="card-body d-flex flex-column justify-content-between">
				    <h4 class="card-title d-flex align-items-center">
				    	${avgWorkTime}
				    </h4>
				    <p class="card-text d-flex align-items-end">평균 근무 시간</p>
				  </div>
				</div>
				<div class="card border-light mb-3 shadow p-3 rounded">
				  <div class="card-header">
				  	<img src="./images/extraTime.svg">
				  </div>
				  <div class="card-body d-flex flex-column justify-content-between">
				    <h4 class="card-title d-flex align-items-center">
				    	${extraTime}시간
				    </h4>
				    <p class="card-text d-flex align-items-end">보상시간</p>
				  </div>
				</div>
				<div class="card border-light mb-3 shadow p-3 rounded">
				  <div class="card-header">
				  	<img src="./images/late.svg">
				  </div>
				  <div class="card-body d-flex flex-column justify-content-between">
				    <h4 class="card-title d-flex align-items-center">
				    	${late}번
				    </h4>
				    <p class="card-text d-flex align-items-end">이번 달 지각</p>
				  </div>
				</div>
				<div class="card border-light mb-3 shadow p-3 rounded">
				  <div class="card-header">
				  	<img src="./images/vacation.svg">
				  </div>
				  <div class="card-body d-flex flex-column justify-content-between">
				    <h4 class="card-title d-flex align-items-center">
				    	${leaveRemain}일
				    </h4>
				    <p class="card-text d-flex align-items-end">남은 휴가</p>
				  </div>
				</div>
			</div>
			
			<div class="card border-light shadow p-3 rounded" style="width: auto;"><div id='calendar'></div></div>
		
		</div> <!-- main-content -->
	</div>	<!-- content -->
	
	<!-- 모달 창 추가 -->
	<div class="modal fade" id="eventModal" tabindex="-1" aria-labelledby="eventModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="eventModalLabel">근무 정보</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <div class="modal-body">
	                <p><img src="./images/calendar.svg"> <span id="modalEventTitle" style="font-weight: bold;"></span></p>
	                <p>
	                	<svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="#17C653" class="bi bi-circle-fill" viewBox="0 0 16 16"><circle cx="8" cy="8" r="6"/></svg>
	                		<Strong>Start</Strong>
	                	<span id="modalEventStart"></span>
	                </p>
	                <p>
	                	<svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="#F82457" class="bi bi-circle-fill" viewBox="0 0 16 16"><circle cx="8" cy="8" r="6"/></svg>
	                		<Strong>End</Strong>
	                	<span id="modalEventEnd"></span>
	                </p>
	                <p><img src="./images/etc.svg"><span id="modalMemo"></span></p>
	            </div>
	            
	            
	            <div class="modal-footer">
	                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<script>
		document.addEventListener('DOMContentLoaded', async function (event) {
		    var calendarEl = document.getElementById('calendar');
	
		    async function fetchEvents() {
		        try {
		            const response = await fetch('./attendance/events'); // API 호출
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
		        timeZone: 'UTC',
		        initialView: 'timeGridWeek',
		        slotMinTime: '07:00:00',
		        slotMaxTime: '23:00:00',
		        contentHeight: 'auto',
		        headerToolbar: {
		            left: 'prev,next',
		            center: 'title',
		            right: 'today'
		        },
		        events: async function (fetchInfo, successCallback, failureCallback) {
		            // API에서 이벤트 데이터를 비동기 가져오기
		            let sampleData = await fetchEvents();
	
		            // FullCalendar에 맞게 데이터 매핑
		            let events = sampleData.map(event => ({
		            	title: event.title ?? "",
		                start: event.start,
		                end: event.end,
		                allDay: Boolean(event.allDay),
		                backgroundColor: event.color,
		                extendedProps: {
		                    useTime: event.useTime ?? "" 
		                },
		            	classNames: event.allDay ? ['all-day-event'] : [] // all-day 이벤트에 클래스 추가
		            }));
		            
		            console.log(sampleData);
	
		            // FullCalendar에 이벤트 데이터 전달
		            successCallback(events);
		        },
		        eventClick: function(info) {
		        	
		        	function formatDate(date, isAllDay = false) {
		                if (!date) return "";

		                let utcDate = new Date(date);  // UTC 시간을 기준으로 Date 객체 생성
		                // UTC 시간에 9시간 더해서 한국 시간으로 변환
		                let localDate = new Date(utcDate.getTime() + ((-9) * 60 * 60 * 1000));
		                
		                if(isAllDay){
		                	let dateOptions = {
		                		year: 'numeric',
		                		month: 'long',
		                		day: 'numeric'
		                	};
		                	return localDate.toLocaleDateString('en-US', dateOptions);
		                } else {
		                	let options = {
	                			year: 'numeric',  // 2025
			                    month: 'long',    // March
			                    day: 'numeric',   // 11
			                    hour: 'numeric',  // 8
			                    minute: '2-digit',// 50
			                    hour12: true      // AM/PM 형식	
		                	};
		                	let formattedDate = localDate.toLocaleString('en-US', options);
			                return formattedDate.replace(',', '').replace(' at', ' -'); // ' at'을 ' -'로 변경
		                }
		            }
		        	
		            // 보상시간 사용했으면 '보상시간 O시간 사용' 출력하기
		            const useTime = info.event.extendedProps.useTime;
		            let memoText = "";  // 사용하지 않았을 때 기본값

		            if (useTime) {
		                memoText = "보상시간 " + useTime + "시간 사용";
		            }
		            
		        	document.getElementById('modalEventTitle').textContent = info.event.title || "";
		            document.getElementById('modalEventStart').textContent = formatDate(info.event.start, info.event.allDay);
		            document.getElementById('modalEventEnd').textContent = info.event.end ? formatDate(info.event.end, info.event.allDay) : "";
		            document.getElementById('modalMemo').textContent = memoText;
		            
	                var eventModal = new bootstrap.Modal(document.getElementById('eventModal'));
	                eventModal.show();
	            },
	            eventMouseEnter: function(info) {
	                if (info.event.allDay) {
	                    // all-day(휴가) 이벤트인 경우
	                    info.el.style.backgroundColor = "rgba(100, 100, 100, 0.85)"; // 중간 회색 (hover)
	                    info.el.style.color = "white";
	                } else {
	                    // 일반 출퇴근 이벤트인 경우
	                    info.el.style.backgroundColor = "rgba(0, 100, 220, 0.85)"; // 중간 파란색 (hover)
	                    info.el.style.color = "white";
	                }
	            },
	            eventMouseLeave: function(info) {
	                if (info.event.allDay) {
	                    // all-day(휴가) 이벤트인 경우
	                    info.el.style.backgroundColor = "#646464"; // 연한 회색
	                    info.el.style.color = "white";
	                } else {
	                    // 일반 출퇴근 이벤트인 경우
	                    info.el.style.backgroundColor = "#0064DC"; // 연한 파란색
	                    info.el.style.color = "white";
	                }
	            }
		    });
	
		    // 캘린더 렌더링
		    calendar.render();
		});

	</script>

</body>
</html>