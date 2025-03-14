function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/members/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`); // 응답 상태 확인
            }
            return response.json(); // JSON 파싱
        })
        .then(data => {
            console.log("응답 데이터:", data);

            if (data.token) { // 토큰이 있을 경우에만 저장
                localStorage.setItem("token", data.token);
                alert("로그인 성공");
                window.location.href = "/"; // 메인 페이지로 이동
            } else {
                alert("로그인 실패! 다시 확인해주세요.");
            }
        })
        .catch(error => {
            alert("로그인 오류 발생!");
            console.error("Error:", error);
        });
}
