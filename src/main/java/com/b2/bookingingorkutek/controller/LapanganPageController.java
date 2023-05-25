package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.kupon.Kupon;
import com.b2.bookingingorkutek.model.lapangan.Lapangan;
import com.b2.bookingingorkutek.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Controller
@RequestMapping("/lapangan-page")
@RequiredArgsConstructor
public class LapanganPageController {
    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;

    @GetMapping("/create")
    public String createLapangan(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("ADMIN"))
            return "redirect:/auth-page/login";
        model.addAttribute("user", user);
        return "create_lapangan";
    }

    @GetMapping("/get-all-court")
    public String getAllCourt(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("ADMIN")) {
            return "redirect:/auth-page/login";
        }

        CompletableFuture<List<Lapangan>> lapanganListAsync = CompletableFuture.supplyAsync(() -> {
            String getLapanganUrl = "http://34.142.212.224:60/gor/get-all-kupon";
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<Lapangan[]> responseLapangan = restTemplate.exchange(getLapanganUrl, HttpMethod.GET, http, Lapangan[].class);
            Lapangan[] arrayOfLapangan = responseLapangan.getBody();
            if (arrayOfLapangan == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfLapangan);
        });
        model.addAttribute("lapangannList", lapanganListAsync.get());
        return "list_coupon";
    }
}
