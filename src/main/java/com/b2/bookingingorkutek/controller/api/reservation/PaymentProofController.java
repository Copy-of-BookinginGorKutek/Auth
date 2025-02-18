package com.b2.bookingingorkutek.controller.api.reservation;

import com.b2.bookingingorkutek.dto.PaymentProofRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/frontend/pay")
public class PaymentProofController {
    @Autowired
    RestTemplate restTemplate;

    @Operation(summary = "Send payment proof by reservation ID (microservice call)")
    @PostMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Object> sendPaymentProof(@RequestBody PaymentProofRequest paymentProofUrl,
                                                   @PathVariable Integer id,
                                                   @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(paymentProofUrl, requestHeaders);

        try{
            return restTemplate.exchange("http://34.142.212.224:60/api/v1/reservation/bukti-bayar/" + id, HttpMethod.PUT, http, Object.class);
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
