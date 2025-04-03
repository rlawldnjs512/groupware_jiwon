<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>일정관리</title>
	<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>
	<link rel="stylesheet" href="./css/calendar.css">
	<style>
		.main-content {
		    display: flex;
		    flex-direction: column;
		    height: calc(100vh - 100px); 
		}
		
		#calendar {
		    flex-grow: 1;
		    width: 100%;
		    height: 100%;
		}
	</style>
</head>
 <%@ include file="sidebar.jsp" %>
<body>

	<div class="content" id="content">
		<div class="main-content">
			<div id='calendar'></div>
		</div> <!-- main-content -->
	</div> <!-- content -->
	
	
	<script>
		document.addEventListener('DOMContentLoaded', async function(event) {
			var calendarEl = document.getElementById('calendar');
			
			async function fetchEvents() {
		        try {
		            const response = await fetch('./schedule/reservation'); // API 호출
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
				initialView : 'dayGridMonth',
				height : '100%',
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






















