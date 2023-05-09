package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


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
        return "create_reservation";
    }



}
