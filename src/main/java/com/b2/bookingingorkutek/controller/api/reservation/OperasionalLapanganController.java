package com.b2.bookingingorkutek.controller.api.reservation;

import com.b2.bookingingorkutek.dto.OperasionalLapanganRequest;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/frontend/operasional-lapangan")
public class OperasionalLapanganController {
    @Autowired
    RestTemplate restTemplate;

    @Operation(summary = "Create a closing date for existing court (microservice call)")
    @PostMapping(path="/create", produces = "application/json")
    public ResponseEntity<Object> createOperasionalLapangan(@RequestBody OperasionalLapanganRequest operasionalLapanganRequest,
                                                        @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OperasionalLapanganRequest> http = new HttpEntity<>(operasionalLapanganRequest, requestHeaders);
        try{
            return restTemplate.postForEntity("http://34.142.212.224:60/api/v1/gor/close-lapangan", http, Object.class);
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
