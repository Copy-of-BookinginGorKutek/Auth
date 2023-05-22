package com.b2.bookingingorkutek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class UserNotificationService {
    @Autowired
    RestTemplate restTemplate;

    public List<Object> getSelf(String emailUser, String token){
        HttpHeaders requestHeaders = getHttpHeaders(token);
        HttpEntity<List<Object>> httpEntity = new HttpEntity<>(requestHeaders);
        String url = String.format("http://34.142.212.224:80/notification/get/%s", emailUser);
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Object[].class);
            return List.of(Objects.requireNonNull(responseEntity.getBody()));
        }catch(Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    private HttpHeaders getHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        return requestHeaders;
    }
}
