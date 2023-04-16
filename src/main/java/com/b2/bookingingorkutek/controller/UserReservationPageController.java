package com.b2.bookingingorkutek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-reservation-page")
public class UserReservationPageController {
    @GetMapping("/get-self")
    public String getUserReservation(){
        return "user_reservation";
    }
}
