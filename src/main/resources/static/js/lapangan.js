$(document).on('submit', '#create_lapangan', function(e){
    e.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/create-lapangan/create", false);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.onload = () => {
        if(xhttp.status === 200){
            let id = JSON.parse(xhttp.response).id;
            window.alert("Berhasil membuat lapangan dengan ID " + id);
        }else{
            window.alert((JSON.parse(xhttp.response).message));
        }
    }
    xhttp.send(JSON.stringify({

    }));
})