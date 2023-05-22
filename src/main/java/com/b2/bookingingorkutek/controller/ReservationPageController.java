package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.kupon.Kupon;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("reservation-page")
@RequiredArgsConstructor
public class ReservationPageController {
    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;

    @GetMapping("/create")
    public String createReservation(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("USER"))
            return "redirect:/auth-page/login";
        model.addAttribute("user", user);

        String getAllKuponUrl = "http://34.142.212.224:60/gor/get-all-kupon";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<Kupon[]> responseAllKupon = restTemplate.exchange(getAllKuponUrl, HttpMethod.GET, http, Kupon[].class);
        Kupon[] arrayOfAllKupon = responseAllKupon.getBody();
        List<Kupon> listOfAllKupon = new ArrayList<>();
        Boolean noKuponExist = true;
        if (arrayOfAllKupon != null){
            listOfAllKupon = Arrays.asList(arrayOfAllKupon);
            noKuponExist = false;
        }
        model.addAttribute("kuponList", listOfAllKupon);
        model.addAttribute("noKuponExist", noKuponExist);
        return "create_reservation";
    }

    @GetMapping("/pay/{id}")
    public String payReservation(@CookieValue(name = "token", defaultValue = "") String token, Model model, @PathVariable Integer id){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("USER"))
            return "redirect:/auth-page/login";
        String getReservationUrl = "http://34.142.212.224:60/reservation/get/" + id;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<Reservation> response = restTemplate.exchange(getReservationUrl, HttpMethod.GET, http, Reservation.class);
        Reservation reservation = response.getBody();
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
