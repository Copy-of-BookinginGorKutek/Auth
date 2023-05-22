package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.UserReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        System.out.println(userReservationList);
        if (userReservationList.isEmpty()){
            model.addAttribute("noReservation", true);
        } else{
            model.addAttribute("noReservation", false);
        }
        model.addAttribute("reservasiList", userReservationList);
        model.addAttribute("user", user);
        return "user_reservation_list";
    }

    @GetMapping("/get-all-reservation")
    public String getAllReservationsAndCourtAvailability(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("ADMIN")) {
            return "redirect:/auth-page/login";
        }

        CompletableFuture<List<Reservation>> reservationListAsync = CompletableFuture.supplyAsync(() -> {
            String getReservationUrl = "http://34.142.212.224:60/reservation/get-all";
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<Reservation[]> responseReservation = restTemplate.exchange(getReservationUrl, HttpMethod.GET, http, Reservation[].class);
            Reservation[] arrayOfReservation = responseReservation.getBody();
            if (arrayOfReservation == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfReservation);
        });

        CompletableFuture<List<OperasionalLapangan>> operasionalLapanganListAsync = CompletableFuture.supplyAsync(() -> {
            String getOperasionalUrl = "http://34.142.212.224:60/gor/closed-lapangan";
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<OperasionalLapangan[]> responseOperasional = restTemplate.exchange(getOperasionalUrl, HttpMethod.GET, http, OperasionalLapangan[].class);
            OperasionalLapangan[] arrayOfOperasional = responseOperasional.getBody();
            if (arrayOfOperasional == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfOperasional);
        });

        if (reservationListAsync.get().isEmpty()){
            model.addAttribute("notEmptyReservation", false);
        } else {
            model.addAttribute("notEmptyReservation", true);
        }

        if (operasionalLapanganListAsync.get().isEmpty()){
            model.addAttribute("noClosedLapangan", true);
        } else {
            model.addAttribute("noClosedLapangan", false);
        }

        model.addAttribute("reservationList", reservationListAsync.get());
        model.addAttribute("operasionalLapanganList", operasionalLapanganListAsync.get());
        return "all_reservation_court_availability";
    }

    @GetMapping("/get-all-reservation/{date}")
    public String getAllReservationsAndCourtAvailabilityByDate(@CookieValue(name = "token", defaultValue = "") String token, Model model, @PathVariable String date) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("ADMIN")) {
            return "redirect:/auth-page/login";
        }

        CompletableFuture<List<Reservation>> reservationListAsync = CompletableFuture.supplyAsync(() -> {
            String getReservationUrl = "http://34.142.212.224:60/reservation/get-reservasi-by-date/" + date;
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<Reservation[]> responseReservation = restTemplate.exchange(getReservationUrl, HttpMethod.GET, http, Reservation[].class);
            Reservation[] arrayOfReservation = responseReservation.getBody();
            if (arrayOfReservation == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfReservation);
        });

        CompletableFuture<List<OperasionalLapangan>> operasionalLapanganListAsync = CompletableFuture.supplyAsync(() -> {
            String getOperasionalUrl = "http://34.142.212.224:60/gor/closed-lapangan/by-date/" + date;
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<OperasionalLapangan[]> responseOperasional = restTemplate.exchange(getOperasionalUrl, HttpMethod.GET, http, OperasionalLapangan[].class);
            OperasionalLapangan[] arrayOfOperasional = responseOperasional.getBody();
            if (arrayOfOperasional == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfOperasional);
        });

        if (reservationListAsync.get().isEmpty()){
            model.addAttribute("notEmptyReservation", false);
        } else {
            model.addAttribute("notEmptyReservation", true);
        }

        if (operasionalLapanganListAsync.get().isEmpty()){
            model.addAttribute("noClosedLapangan", true);
        } else {
            model.addAttribute("noClosedLapangan", false);
        }

        model.addAttribute("reservationList", reservationListAsync.get());
        model.addAttribute("operasionalLapanganList", operasionalLapanganListAsync.get());
        return "all_reservation_court_availability";
    }
}