fetch("/members/admin", {
    method:"GET",
    headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    }
}).then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error("Error:", error));