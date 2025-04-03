document.addEventListener("DOMContentLoaded", function () {
    var reservationData;
    var revSpan;
    var modal;

    // 이벤트 위임 방식으로 클릭 이벤트 적용
    document.body.addEventListener("click", function (event) {
        if (event.target.matches(".reservation-container span.nocheck")) {
            handleClick.call(event.target);
        }
    });

    function handleClick() {
        revSpan = this;

        var range = this.textContent;
        console.log("클릭한 예약 시간: " + range);

        var slot = this.getAttribute("name");
        console.log("클릭한 slot: " + slot);

        var rev_date = document.getElementById("rev_date").value;
        console.log("예약 날짜: " + rev_date);

        var roomName = this.closest(".card").querySelector(".card-title").textContent;
        console.log("예약 회의실: " + roomName);

        var room_id = this.closest(".py-3").parentNode.id;
        console.log("예약 회의실 ID: " + room_id);

        var emp_name = document.getElementById("emp_name").value;
        var emp_id = document.getElementById("emp_id").value;

        document.getElementById("time").value = range;
        document.getElementById("date").value = rev_date;
        document.getElementById("name").value = emp_name;
        document.getElementById("room").value = roomName;

        reservationData = {
            range: range,
            slot: slot,
            rev_date: rev_date,
            roomName: roomName,
            room_id: room_id,
            emp_name: emp_name,
            emp_id: emp_id
        };

        console.log("JSON 데이터: " + JSON.stringify(reservationData));

        modal = new bootstrap.Modal(document.getElementById('staticBackdrop'));
        modal.show();

        document.getElementById('revSubmit').onclick = async () => {
            let chk = await ajaxSubmit();
            console.log(chk, typeof chk);
            if (chk) {
                this.classList.remove("nocheck");
                this.classList.add("check");
                modal.hide();
            }
        };
    }

    var ajaxSubmit = async function () {
        console.log("전송 데이터:", reservationData);
        try {
            const response = await fetch("./insertReserv.do", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(reservationData)
            });

            const data = await response.json();

            if (data.isc) {
                alert("예약이 완료되었습니다!");
            } else {
                alert("예약에 실패했습니다. 다시 시도해주세요.");
            }
            return data;
        } catch (error) {
            console.error("예약 실패:", error);
            alert("예약에 실패했습니다. 다시 시도해주세요.");
            return null;
        }
    };

    var date = new Date();
    var year = String(date.getFullYear());
    var month = String(date.getMonth() + 1).padStart(2, '0');
    var day = String(date.getDate()).padStart(2, '0');

    var graveEx = `${year}-${month}-${day}`;
    document.getElementById("rev_date").value = graveEx;
    
});