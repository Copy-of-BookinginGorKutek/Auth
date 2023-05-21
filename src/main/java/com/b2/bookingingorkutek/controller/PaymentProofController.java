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
@RequestMapping("/pay")
public class PaymentProofController {
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/{id}")
    public ResponseEntity<Object> sendPaymentProof(@RequestBody String paymentProofUrl,
                                                   @PathVariable Integer id,
                                                   @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(paymentProofUrl, requestHeaders);

        try{
            return restTemplate.postForEntity("http://localhost:8082/reservation/bukti-bayar/" + id, http, Object.class);
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
