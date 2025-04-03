document.addEventListener("DOMContentLoaded", function () {
    // Handlebars 헬퍼 등록
    Handlebars.registerHelper("eq", function (a, b) {
        return a === b;
    });

    // 예약 데이터를 가져오는 함수
    function fetchReservations(selectedDate) {
        $.ajax({
            url: "./reservationapi.do",
            type: "GET",
            dataType: "json",
            data: { nowDate: selectedDate },
            success: function(response) {
                console.log("응답 타입:", typeof response);
                console.log("응답 데이터:", response);

                var source = document.getElementById("room-template").innerHTML;
                var template = Handlebars.compile(source);
                var html = template(response);

                $("#revContent").html(html);
            },
            error: function(xhr, status, error) {
                console.error("Error fetching reservation data:", error);
            }
        });
    }
    
    if (dateInput) {
        // 처음 로드될 때 한 번 실행
        fetchReservations(dateInput.value);
    } else {
        console.error("날짜 입력 필드를 찾을 수 없습니다.");
    }

    
    $(document).ready(function() {
        let currentDate = new Date();

        function updateDateDisplay() {
            const year = currentDate.getFullYear();
            const month = String(currentDate.getMonth() + 1).padStart(2, "0");
            const day = String(currentDate.getDate()).padStart(2, "0");
            $("#date-text").text(`${year}년 ${month}월 ${day}일`);
            $("#rev_date").val(`${year}-${month}-${day}`);
            
            let dateText = `${year}-${month}-${day}`;
            console.log("변환된 날짜:", dateText);
            
            fetchReservations($("#rev_date").val());
            
            document.getElementById("convertedDate").setAttribute('value',dateText)
        }

        updateDateDisplay();

        $("#today-btn").on("click", function() {
            currentDate = new Date();
            updateDateDisplay();
        });

        $("#prev-day").on("click", function() {
		var convertedDate = document.getElementById("convertedDate").value;
			if (dateMin.min != convertedDate){
            currentDate.setDate(currentDate.getDate() - 1);
            updateDateDisplay();
            }
        });

        $("#next-day").on("click", function() {
            currentDate.setDate(currentDate.getDate() + 1);
            updateDateDisplay();
        });

        $("#date-display").on("click", function() {
            $("#rev_date")[0].showPicker();
        });

        $("#rev_date").on("change", function() {
            currentDate = new Date(this.value);
            updateDateDisplay();
        });
    });
    
    
    // 달력에서 오늘 이전 날짜를 막아놓기 위한 오늘 값
    var dateMin = document.getElementById("rev_date");
    // 날짜 입력 필드 탐색
    var dateInput = document.querySelector("#rev_date");
    // 달력에서 오늘보다 이전의 날짜들은 선택하지 못하도록
    const today = new Date();
    
    const yyyy = today.getFullYear();
    let mm = today.getMonth() + 1;  
    let dd = today.getDate();
    
    if (mm < 10) mm = '0' + mm;  
    if (dd < 10) dd = '0' + dd;  
    
    const todayFormatted = `${yyyy}-${mm}-${dd}`;
    
    document.getElementById('rev_date').setAttribute('min', todayFormatted);
    
    console.log(typeof(dateMin.min), dateMin.min)
});