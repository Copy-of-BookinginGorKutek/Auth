package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.UserReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/user-reservation-page")
public class UserReservationPageController {
    @Autowired
    UserReservationService userReservationService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/get-self/{idReservasi}")
    public String getUserReservation(@CookieValue(name = "token", defaultValue = "") String token, @PathVariable Integer idReservasi, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("ADMIN")) {
            return "redirect:/auth-page/login";
        }
        Reservation reservation = userReservationService.getReservasiById(user.getEmailUser(), idReservasi, token);
        model.addAttribute("reservasi", reservation);
        return "user_reservation";
    }

    @GetMapping("/get-self")
    public String getUserReservationList(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("ADMIN")) {
            return "redirect:/auth-page/login";
        }
        List<Reservation> userReservationList = userReservationService.getSelf(user.getEmailUser(), token);
        model.addAttribute("reservasiList", userReservationList);
        model.addAttribute("user", user);
        return "user_reservation_list";
    }
}
