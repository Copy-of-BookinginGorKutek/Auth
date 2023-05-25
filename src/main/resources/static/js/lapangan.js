$(document).on('submit', '#create_lapangan', function(e){
    e.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/create-lapangan/create", false);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.onload = () => {
        if(xhttp.status === 200){
            window.alert("Berhasil membuat lapangan");
        }else{
            window.alert((JSON.parse(xhttp.response).message));
        }
    }
})