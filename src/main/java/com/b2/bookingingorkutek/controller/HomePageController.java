package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.OperasionalLapanganService;
import com.b2.bookingingorkutek.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/home")
public class HomePageController {
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    OperasionalLapanganService operasionalLapanganService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/")
    @CrossOrigin
    public String homePage(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("ADMIN")) {
            return "redirect:/auth-page/login";
        }
        // Date format: yyyy-MM-dd
        Date localDate = new Date();
        String localDateAsString = (localDate.getYear() + 1900) + "-" + formatDatePart(localDate.getMonth() + 1) + "-" + formatDatePart(localDate.getDate());

        CompletableFuture<List<Reservation>> reservationTodayListAsync = CompletableFuture.supplyAsync(() ->
                reservationService.getReservasiByDate(localDateAsString, token)
        );

        CompletableFuture<List<OperasionalLapangan>> operasionalLapanganTodayListAsync = CompletableFuture.supplyAsync(() ->
                operasionalLapanganService.getOperasionalLapanganByDate(localDateAsString, token)
        );

        model.addAttribute("notEmptyTodayReservation", !reservationTodayListAsync.get().isEmpty());
        model.addAttribute("noClosedLapanganToday", operasionalLapanganTodayListAsync.get().isEmpty());
        model.addAttribute("todayOperasionalList", operasionalLapanganTodayListAsync.get());
        model.addAttribute("todayReservationList", reservationTodayListAsync.get());
        model.addAttribute("name", user.getFirstname() + " " + user.getLastname());
        model.addAttribute("email", user.getEmailUser());
        return "home";
    }

    private String formatDatePart(Integer datePart){
        return (datePart < 10) ? "0" + datePart: datePart.toString();
    }
}
