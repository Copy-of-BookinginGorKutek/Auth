package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.kupon.Kupon;
import com.b2.bookingingorkutek.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("admin-coupon-page")
@RequiredArgsConstructor
public class AdminCouponPageController {
    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;

    @GetMapping("/get-all-coupon")
    public String getAllCoupon(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("ADMIN")) {
            return "redirect:/auth-page/login";
        }

        CompletableFuture<List<Kupon>> couponListAsync = CompletableFuture.supplyAsync(() -> {
            String getCouponUrl = "http://34.142.212.224:60/gor/get-all-kupon";
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(token);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

            ResponseEntity<Kupon[]> responseCoupon = restTemplate.exchange(getCouponUrl, HttpMethod.GET, http, Kupon[].class);
            Kupon[] arrayOfCoupon = responseCoupon.getBody();
            if (arrayOfCoupon == null){
                return new ArrayList<>();
            }
            return Arrays.asList(arrayOfCoupon);
        });
        model.addAttribute("couponList", couponListAsync.get());
        return "list_coupon";
    }

    @GetMapping("/create")
    public String createCoupon(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals("ADMIN"))
            return "redirect:/auth-page/login";
        model.addAttribute("user", user);
        return "create_coupon";
    }

    @DeleteMapping(path="/delete/{id}", produces = "application/json")
    public ResponseEntity<String> deleteCouponPost(@PathVariable int id,
                                                   @CookieValue(name = "token", defaultValue = "") String token){
        String url = "http://34.142.212.224:60/gor/delete-kupon/" + id;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> http = new HttpEntity<>(requestHeaders);
        try{
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, http, String.class);
            return response;
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
