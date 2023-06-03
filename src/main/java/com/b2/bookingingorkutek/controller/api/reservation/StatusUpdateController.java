package com.b2.bookingingorkutek.controller.api.reservation;

import com.b2.bookingingorkutek.dto.ReservasiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/frontend/status")
public class StatusUpdateController {
    @Autowired
    RestTemplate restTemplate;
    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<Object> sendPaymentProof(@RequestBody ReservasiRequest request,
                                                   @PathVariable Integer id,
                                                   @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(request, requestHeaders);

        try{
            ResponseEntity<Object> response = restTemplate.exchange("http://34.142.212.224:60/api/v1/reservation/stat-update/" + id, HttpMethod.PUT, http, Object.class);
            return response;
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
