document.addEventListener('DOMContentLoaded', function() {
	
	var userRole = document.getElementById("role").value;
	if (userRole === "A") {
        return;
    }
	
    const clockInBtn = document.getElementById("clockIn");
	const clockOutBtn = document.getElementById("clockOut"); 

	var clockInTime = document.getElementById("clockInTime").textContent;
    var clockOutTime =document.getElementById("clockOutTime").textContent;
    
    // 출근&퇴근 버튼의 비활성화
    if(clockInTime == "" && clockOutTime == ""){
		document.getElementById("clockIn").disabled = false;
		document.getElementById("clockOut").disabled = true;
	}else{
		document.getElementById("clockIn").disabled = true;
		document.getElementById("clockOut").disabled = false;
	}
	if(clockInTime != "" && clockOutTime != ""){
		document.getElementById("clockIn").disabled = true;
		document.getElementById("clockOut").disabled = true;
	}

	// 출근 버튼 클릭
    clockInBtn.addEventListener("click", function () {
        confirmClockIn();
    });

    clockOut.addEventListener('click', function() {
		
		// 사용자의 보상시간을 가져옴.
		var extraTime = document.getElementById("extraTime").value;
		
		// 사용자의 지각여부를 가져옴.
		var attendType = document.getElementById("attendType").value;
		
		// 현재 시간(퇴근버튼을 누른 시간)
        const currentTime = new Date();
        const exitHour = currentTime.getHours();

		var infoAtten = {
			exitHour : "Y",
			useBonusTime : "N"
		}

        // 지각이 아니면서 18시 이전 퇴근 시
        if (attendType == null && exitHour < 18) {
			let checkTime = confirm("현재 18시 이전입니다. 퇴근하시겠습니까?");
			if(checkTime){
				infoAtten.exitHour="N";
				
				// 디버깅을 위한 콘솔 출력
		        console.log("나의 현재 보상시간:", extraTime);
		        console.log("필요한 보상시간:", 18 - exitHour);
				
		        if(extraTime >= (18-exitHour)){
					let useBonusTime = confirm(`현재 보유한 보상시간은 ${extraTime}시간 입니다. 사용하시겠습니까?`);
					if(useBonusTime){
						infoAtten.useBonusTime = "Y";
					}
				} else {
					alert(`현재 보유한 보상시간은 ${extraTime}시간 입니다. 조퇴 처리되었습니다.`);
				}
			} else { // 취소 클릭 시
				return;
			}
        } else {
			// 지각 or 18시 이후 퇴근
            attendance(infoAtten);
        }
		
        attendance(infoAtten);
        
        clockInBtn.disabled = true;  // 출근 버튼 비활성화
	    clockOutBtn.disabled = true; // 퇴근 버튼 비활성화
        
        alert("퇴근 기록이 정상적으로 저장되었습니다.");
       
    });
});

// 출근 버튼 클릭 메소드
function confirmClockIn() {
    if (confirm("출근하시겠습니까?")) {
        document.getElementById("clockInForm").submit();
    }
}

function attendance(infoAtten) {
	
	console.log((infoAtten.exitHour == "N" &  infoAtten.useBonusTime=="Y") ? "보상시간 사용하여 퇴근":"");
	console.log((infoAtten.exitHour == "N" &  infoAtten.useBonusTime=="N") ? "조퇴":"");
	console.log((infoAtten.exitHour == "Y" &  infoAtten.useBonusTime=="N") ? "정상퇴근":"");
	
	fetch('./updateAttendance', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(infoAtten) // JSON 데이터 전송
	})
	.then(response => response.json())
	.then(data => { 
		console.log(data);
		location.href="./homeList.do" ;
	})
	.catch(error => {
						alert("오류 발생");
						console.error("Error:", error);
					});
}
