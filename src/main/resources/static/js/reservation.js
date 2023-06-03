// create reservation
$(document).on('submit', '#create_reservation', function(e){
    e.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/api/v1/frontend/create-reservation/create", false);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.onload = () => {
        if(xhttp.status === 200){
            window.alert("Berhasil membuat reservasi");
            window.location.replace("/user-reservation-page/get-self");
        }else{
            window.alert((JSON.parse(xhttp.response).message));
        }
    }
    var tanggalMain = new Date(document.getElementById("tanggal_main").value);
    var tanggalMainParsed = `${formatDatePart(tanggalMain.getDate())}-${formatDatePart(tanggalMain.getMonth()+1)}-${tanggalMain.getFullYear()}`;
    var jamMulai = document.getElementById("jam_main_mulai").value;
    var menitMulai = document.getElementById("menit_main_mulai").value;
    var jamBerakhir = document.getElementById("jam_main_akhir").value;
    var menitBerakhir = document.getElementById("menit_main_akhir").value;
    var kuponId = document.getElementById("kupon").value;
    xhttp.send(JSON.stringify({
        "emailUser" : document.getElementById("email").value,
        "statusPembayaran" : "MENUNGGU_PEMBAYARAN",
        "buktiTransfer" : null,
        "waktuMulai" : tanggalMainParsed + " " + jamMulai + ":" + menitMulai,
        "waktuBerakhir" : tanggalMainParsed + " " + jamBerakhir + ":" + menitBerakhir,
        "tambahanQuantity" : {
            "AIR_MINERAL" : document.getElementById("airQty").value,
            "RAKET" : document.getElementById("raketQty").value,
            "SHUTTLECOCK" : document.getElementById("shuttlecockQty").value
        },
        "kuponId" : kuponId
    }));
})
function formatDatePart(datePart){
    if (datePart < 10){
        return "0" + datePart.toString();
    }
    return datePart.toString();
}
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



