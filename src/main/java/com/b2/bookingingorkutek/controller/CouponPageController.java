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

@Controller
@RequestMapping("coupon-page")
@RequiredArgsConstructor
public class CouponPageController {
    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;

    @GetMapping("/create")
    public String createCoupon(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("ADMIN"))
            return "redirect:/auth-page/login";
        model.addAttribute("user", user);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        return "create_coupon";
    }
}
