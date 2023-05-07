// create reservation
$(document).on('submit', '#create_reservation', function(e){
    e.preventDefault();
    window.alert("halo");
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/reservation-page/create", false);
    xhttp.setRequestHeader("Content-Type", "application/json");
    // xhttp.setRequestHeader("Authorization", "Bearer " + getCookie("token"))
    xhttp.onload = () => {
        if(xhttp.status === 200){
            window.alert("Berhasil membuat reservasi");
        }else{
            window.alert("Gagal membuat reservasi");
        }
    }
    var tanggalMain = document.getElementById("tanggal_main").value;
    var jamMulai = document.getElementById("jam_main_mulai").value;
    var jamBerakhir = document.getElementById("jam_main_berakhir").value;
    xhttp.send(JSON.stringify({
        "emailUser" : document.getElementById("email").value,
        "statusPembayaran" : "MENUNGGU_PEMBAYARAN",
        "buktiTransfer" : null,
        "waktuMulai" : tanggalMain + " " + jamMulai,
        "waktuBerakhir" : tanggalMain + " " + jamBerakhir,
        "tambahanQuantity" : {
            "AIR_MINERAL" : document.getElementById("airQty").value,
            "RAKET" : document.getElementById("raketQty").value,
            "SHUTTLECOCK" : document.getElementById("shuttlecockQty").value
        }
    }));
})

// function getCookie(cname) {
//     let name = cname + "=";
//     let decodedCookie = decodeURIComponent(document.cookie);
//     let ca = decodedCookie.split(';');
//     for(let i = 0; i <ca.length; i++) {
//         let c = ca[i];
//         if (c.indexOf(name) == 0) {
//             return c.substring(name.length, c.length);
//         }
//     }
//     return "";
// }



