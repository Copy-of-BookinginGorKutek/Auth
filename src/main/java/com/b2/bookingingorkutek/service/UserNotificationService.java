package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.notification.Notification;
import lombok.Generated;
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
@Generated
public class UserNotificationService {
    @Autowired
    RestTemplate restTemplate;

    public List<Notification> getSelf(String emailUser, String token){
        HttpHeaders requestHeaders = getHttpHeaders(token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestHeaders);
        String url = String.format("http://34.142.212.224:40/notification/get/%s", emailUser);
        try {
            ResponseEntity<Notification[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Notification[].class);
            return List.of(Objects.requireNonNull(responseEntity.getBody()));
        }catch(Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    public void deleteNotification(Integer id, String token){
        HttpHeaders requestHeaders = getHttpHeaders(token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestHeaders);
        String url = "http://34.142.212.224:40/notification/delete/" + id;
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private HttpHeaders getHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        return requestHeaders;
    }
}
