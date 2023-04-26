package com.b2.bookingingorkutek.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("reservation-page")
@RequiredArgsConstructor
public class ReservationPageController {
    private final RestTemplate restTemplate;
    @GetMapping("/create")
    public String createReservation(@CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://auth/authorization/get-role", new HttpEntity<>(requestHeaders), String.class);
        String role = responseEntity.getBody();
        assert role != null;
        if(role.equals("Anonymous") || role.equals("Admin"))
            return "redirect:/auth-page/login";
        return "create_reservation";
    }

    @PostMapping(path="/create", produces = "application/json")
    @ResponseBody
    public void createReservationPost(@RequestBody String json,
                                        @CookieValue(name = "token", defaultValue = "") String token,
                                        HttpServletResponse response){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> http = new HttpEntity<>(json, requestHeaders);
        restTemplate.postForEntity("http://reservation/reservation/create", http, Object.class);
    }

}
