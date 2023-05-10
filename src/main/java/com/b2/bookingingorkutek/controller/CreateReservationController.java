package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ReservasiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/create-reservation")
public class CreateReservationController {
    @Autowired
    RestTemplate restTemplate;

    @PostMapping(path="/create", produces = "application/json")
    public ResponseEntity<Object> createReservationPost(@RequestBody ReservasiRequest reservasiRequest,
                                                        @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservasiRequest> http = new HttpEntity<>(reservasiRequest, requestHeaders);
        try{
            return restTemplate.postForEntity("http://reservation/reservation/create", http, Object.class);
        }catch(HttpServerErrorException | HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
