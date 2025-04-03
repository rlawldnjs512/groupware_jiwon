document.addEventListener("DOMContentLoaded", function () {
    // 날짜 입력 필드 탐색
    var dateInput = document.querySelector("#expired");

    if (dateInput) {
        // 달력에서 오늘보다 이전 날짜 선택 불가
        const today = new Date();
        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, '0');  
        const dd = String(today.getDate()).padStart(2, '0');  
        const todayFormatted = `${yyyy}-${mm}-${dd}`;

        dateInput.setAttribute('min', todayFormatted);

        // 처음 로드될 때 실행
        fetchReservations(dateInput.value);

        // 날짜 변경 시 이벤트 추가
        dateInput.addEventListener("change", function () {
            fetchReservations(this.value);
        });
    } else {
        console.error("날짜 입력 필드를 찾을 수 없습니다.");
    }
});
