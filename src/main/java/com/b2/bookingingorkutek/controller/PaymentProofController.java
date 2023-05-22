package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.PaymentProofRequest;
import com.b2.bookingingorkutek.dto.ReservasiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/pay")
public class PaymentProofController {
    @Autowired
    RestTemplate restTemplate;

    @PostMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Object> sendPaymentProof(@RequestBody PaymentProofRequest paymentProofUrl,
                                                   @PathVariable Integer id,
                                                   @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        System.out.println(token);
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(paymentProofUrl, requestHeaders);

        try{
            ResponseEntity<Object> response = restTemplate.exchange("http://34.142.212.224:60/reservation/bukti-bayar/" + id, HttpMethod.PUT, http, Object.class);
            System.out.println("response " + response);
            return response;
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
