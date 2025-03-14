document.addEventListener("DOMContentLoaded",function (){
    const token = localStorage.getItem("token");
    if (token != null) {
        document.getElementById("login-button").style.display = "none";
        document.getElementById("registration").style.display = "none";
        document.getElementById("logout-button").style.display = "inline";
        document.getElementById("admin").style.display = "inline";
    }

    document.getElementById("logout-button").addEventListener("click", function (event) {
        event.preventDefault(); // 기본 동작(페이지 이동) 막기
        logout();
    });

    function logout() {
        localStorage.removeItem("token"); // 토큰 삭제
        alert("로그아웃 되었습니다.");
        location.href = "/"; // 메인 페이지로 리디렉션
    }
})