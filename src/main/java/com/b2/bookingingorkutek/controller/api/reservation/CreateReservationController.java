package com.b2.bookingingorkutek.controller.api.reservation;

import com.b2.bookingingorkutek.dto.ReservasiRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/frontend/create-reservation")
public class CreateReservationController {
    @Autowired
    RestTemplate restTemplate;

    @Operation(summary = "Create a new reservation (microservice call)")
    @PostMapping(path="/create", produces = "application/json")
    public ResponseEntity<Object> createReservationPost(@RequestBody ReservasiRequest reservasiRequest,
                                                        @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservasiRequest> http = new HttpEntity<>(reservasiRequest, requestHeaders);
        try{
            return restTemplate.postForEntity("http://34.142.212.224:60/api/v1/reservation/create", http, Object.class);
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
