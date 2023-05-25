// create coupon
$(document).on('submit', '#create_coupon', function(e){
    e.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/coupon-controller/create", false);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.onload = () => {
        if(xhttp.status === 200){
            window.alert("Berhasil membuat kupon");
            window.location.replace("/admin-coupon-page/get-all-coupon");
        }else{
            window.alert((JSON.parse(xhttp.response).message));
        }
    }
    var namaKupon = document.getElementById("code").value;
    var diskon = document.getElementById("discount").value;
    xhttp.send(JSON.stringify({
        "name" : namaKupon,
        "percentageDiscounted" : diskon
    }));
})




