// Login User
$(document).on('submit', '#loginForm', function(e) {
    e.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/auth/login", false);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.onload = () => {
        var response = JSON.parse(xhttp.response);
        if(xhttp.status !== 200){
            window.alert(response.message);
        }else {
            document.cookie = "token=" + response.token + "; path=/; secure";
        }
    }

    xhttp.send(JSON.stringify({
        "email": document.getElementById("email").value,
        "password": document.getElementById("password").value
    }));
})

// Register User
$(document).on('submit', '#registerForm', function(e) {
    e.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/auth/register", false);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.onload = () => {
        var response = JSON.parse(xhttp.response);
        if(response.message === "success"){
            window.location.replace("/auth-page/login");
        }else{
            window.alert(response.message);
        }
    }

    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value
    var role = $('#sel1').val();
    xhttp.send(JSON.stringify({
        "firstname": firstname,
        "lastname": lastname,
        "email": email,
        "password": password,
        "role":role
    }));
})



