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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/admin-page")
public class DashboardAdminPageController {
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    OperasionalLapanganService operasionalLapanganService;
    @GetMapping("/dashboard")
    @CrossOrigin
    public String dashboardAdmin(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("USER")) {
            return "redirect:/auth-page/login";
        }

        // Date format: dd-MM-yyyy
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);

        CompletableFuture<List<Reservation>> allReservationListAsync = CompletableFuture.supplyAsync(() ->
                reservationService.getAllReservasi(token)
        );

        model.addAttribute("reservationNotEmpty", !allReservationListAsync.get().isEmpty());
        model.addAttribute("allReservationList", allReservationListAsync.get());
        model.addAttribute("todayDate", strDate);
        return "dashboard_admin";
    }

    @GetMapping("/gor/closed-lapangan")
    @CrossOrigin
    public String allClosedLapangan(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("USER")) {
            return "redirect:/auth-page/login";
        }

        CompletableFuture<List<OperasionalLapangan>> allOperasionalLapanganAsync = CompletableFuture.supplyAsync(() ->
                operasionalLapanganService.getAllOperasionalLapangan(token)
        );

        model.addAttribute("operasionalLapanganNotEmpty", !allOperasionalLapanganAsync.get().isEmpty());
        model.addAttribute("operasionalLapanganList", allOperasionalLapanganAsync.get());
        return "operasional_lapangan_admin";
    }

    @GetMapping("/close-court-page")
    @CrossOrigin
    public String closeCourt(@CookieValue(name = "token", defaultValue = "") String token) {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("USER")) {
            return "redirect:/auth-page/login";
        }
        return "lapangan_admin";
    }

}

