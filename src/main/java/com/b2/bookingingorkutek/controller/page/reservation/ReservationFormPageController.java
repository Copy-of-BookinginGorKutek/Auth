package com.b2.bookingingorkutek.controller.page.reservation;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.kupon.Kupon;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.KuponService;
import com.b2.bookingingorkutek.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("reservation-page")
@RequiredArgsConstructor
public class ReservationFormPageController {
    private final AuthorizationService authorizationService;
    private final KuponService kuponService;
    private final ReservationService reservationService;

    @GetMapping("/create")
    public String createReservation(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("USER"))
            return "redirect:/auth-page/login";
        model.addAttribute("user", user);

        List<Kupon> listOfAllKupon = kuponService.getAllKupon(token);

        model.addAttribute("kuponList", listOfAllKupon);
        model.addAttribute("noKuponExist", listOfAllKupon.isEmpty());
        return "create_reservation";
    }

    @GetMapping("/pay/{id}")
    public String payReservation(@CookieValue(name = "token", defaultValue = "") String token, Model model, @PathVariable Integer id){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("USER"))
            return "redirect:/auth-page/login";
        Reservation reservation = reservationService.getReservasiById(id, token);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String date = reservation.getWaktuMulai().format(dateFormatter);
        String startTime = reservation.getWaktuMulai().format(timeFormatter);
        String endTime = reservation.getWaktuBerakhir().format(timeFormatter);
        model.addAttribute("id", reservation.getId());
        model.addAttribute("harga", reservation.getHarga());
        model.addAttribute("date", date);
        model.addAttribute("time", startTime + "-" + endTime);
        return "send_payment_proof";
    }

}
