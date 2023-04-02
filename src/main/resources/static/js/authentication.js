// Login User
function login(){
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/auth/login", false);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.onload = () => {
        var response = JSON.parse(xhttp.response);
        document.cookie = "token="+response.token+"; path=/";
    }

    xhttp.send(JSON.stringify({
        "email": document.getElementById("email").value,
        "password":document.getElementById("password").value
    }));
}

// Register User
function register(){
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/auth/register", false);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.onload = () => {
        window.alert(xhttp.response);
        var response = JSON.parse(xhttp.response);
        if(response.message === "success"){
            window.history.pushState({},document.title, "/auth-page/login");
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
}



