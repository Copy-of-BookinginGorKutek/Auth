package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.KuponRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/delete-coupon")
public class DeleteCouponController {
    @Autowired
    RestTemplate restTemplate;

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
