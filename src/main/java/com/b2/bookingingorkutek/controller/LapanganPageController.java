package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.kupon.Kupon;
import com.b2.bookingingorkutek.model.lapangan.Lapangan;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.LapanganService;
import com.b2.bookingingorkutek.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/lapangan-page")
@RequiredArgsConstructor
public class LapanganPageController {

    private final RestTemplate restTemplate;
    @Autowired
    private final AuthorizationService authorizationService;
    @Autowired
    LapanganService lapanganService;
    static final String ADMIN = "ADMIN";
    static final String REDIRECT_TO_LOGIN = "redirect:/auth-page/login";


    @GetMapping("/create")
    public String createLapangan(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("ADMIN"))
            return "redirect:/auth-page/login";
        model.addAttribute("user", user);
        return "create_lapangan";
    }

    @GetMapping("/get-all-reserved")
    public String getAllReservedCourt(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals(ADMIN)) {
            return REDIRECT_TO_LOGIN;
        }
        List<Lapangan> userLapanganList = lapanganService.getAllLapangan(token);
        model.addAttribute("totalUsers", userLapanganList.size());

        return "list_used_court";
    }
}
