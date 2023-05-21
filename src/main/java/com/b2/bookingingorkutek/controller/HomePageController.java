package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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

        CompletableFuture<List<Reservation>> reservationTodayListAsync = CompletableFuture.supplyAsync(() -> {
            String getTodayReservationUrl = "http://localhost:8082/reservation/get-reservasi-by-date/" + localDateAsString;
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<Reservation[]> responseTodayReservation = restTemplate.exchange(getTodayReservationUrl, HttpMethod.GET, http, Reservation[].class);
            Reservation[] arrayOfTodayReservation = responseTodayReservation.getBody();
            if (arrayOfTodayReservation == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfTodayReservation);
        });

        CompletableFuture<List<OperasionalLapangan>> operasionalLapanganTodayListAsync = CompletableFuture.supplyAsync(() -> {
            String getTodayOperasionalUrl = "http://localhost:8082/gor/closed-lapangan/by-date/" + localDateAsString;
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<OperasionalLapangan[]> responseTodayOperasional = restTemplate.exchange(getTodayOperasionalUrl, HttpMethod.GET, http, OperasionalLapangan[].class);
            OperasionalLapangan[] arrayOfTodayOperasional = responseTodayOperasional.getBody();
            if (arrayOfTodayOperasional == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfTodayOperasional);
        });

        if (reservationTodayListAsync.get().isEmpty()){
            model.addAttribute("notEmptyTodayReservation", false);
        } else {
            model.addAttribute("notEmptyTodayReservation", true);
        }

        if (operasionalLapanganTodayListAsync.get().isEmpty()){
            model.addAttribute("noClosedLapanganToday", true);
        } else {
            model.addAttribute("noClosedLapanganToday", false);
        }

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
