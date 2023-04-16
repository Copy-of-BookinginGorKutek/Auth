package com.b2.bookingingorkutek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("reservation-page")
public class ReservationPageController {
    @GetMapping("/create")
    public String createReservation(){ return "create_reservation"; }
}
