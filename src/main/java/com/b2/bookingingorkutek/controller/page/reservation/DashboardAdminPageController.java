package com.b2.bookingingorkutek.controller.page.reservation;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/dashboard")
    @CrossOrigin
    public String dashboardAdmin(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("USER")) {
            return "redirect:/auth-page/login";
        }

        LocalDateTime localDateTimeUTC = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime localDateTimeConverted = localDateTimeUTC.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Jakarta"))
                .toLocalDateTime();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strDate = dateFormat.format(localDateTimeConverted);

        CompletableFuture<List<Reservation>> allReservationListAsync = CompletableFuture.supplyAsync(() ->
                reservationService.getAllReservasi(token)
        );

        model.addAttribute("reservationNotEmpty", !allReservationListAsync.get().isEmpty());
        model.addAttribute("allReservationList", allReservationListAsync.get());
        model.addAttribute("todayDate", strDate);
        return "dashboard_admin";
    }
}

