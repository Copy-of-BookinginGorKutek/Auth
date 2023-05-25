package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.KuponRequest;
import com.b2.bookingingorkutek.service.KuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/coupon-controller")
public class CouponController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    KuponService kuponService;

    @PostMapping(path="/create", produces = "application/json")
    public ResponseEntity<Object> createCouponPost(@RequestBody KuponRequest kuponRequest,
                                                        @CookieValue(name = "token", defaultValue = "") String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<KuponRequest> http = new HttpEntity<>(kuponRequest, requestHeaders);
        try{
            return restTemplate.postForEntity("http://34.142.212.224:60/gor/create-kupon", http, Object.class);
        }catch(HttpServerErrorException | HttpClientErrorException e){
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<Object> deleteCouponPost(@PathVariable Integer id,
                                                   @CookieValue(name = "token", defaultValue = "") String token){
        kuponService.deleteCoupon(id, token);
        return ResponseEntity.ok("\"Deleted coupon\"");
    }
}
