window.addEventListener("DOMContentLoaded", function () {
    document.getElementById("toggleBtn").addEventListener("click", function () {
        document.getElementById("sidebar").classList.toggle("hidden");
        document.getElementById("content").classList.toggle("expanded");
    });

    // 메뉴 클릭 시 활성화 스타일 적용
    /*document.querySelectorAll("#menuList li").forEach(item => {
        item.addEventListener("click", function () {
            document.querySelectorAll("#menuList li").forEach(li => li.classList.remove("active"));
            this.classList.add("active");
        });
    });*/
});