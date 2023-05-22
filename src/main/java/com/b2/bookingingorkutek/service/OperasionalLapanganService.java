package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OperasionalLapanganService {
    @Autowired
    RestTemplate restTemplate;

    public List<OperasionalLapangan> getOperasionalLapanganByDate(String date, String token){
        String getTodayOperasionalUrl = "http://34.142.212.224:60/gor/closed-lapangan/by-date/" + date;
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<OperasionalLapangan[]> responseTodayOperasional = restTemplate.exchange(
                getTodayOperasionalUrl, HttpMethod.GET, http, OperasionalLapangan[].class);
        OperasionalLapangan[] arrayOfTodayOperasional = responseTodayOperasional.getBody();
        if (arrayOfTodayOperasional == null){
            return new ArrayList<>();
        }
        return Arrays.asList(arrayOfTodayOperasional);
    }

    public List<OperasionalLapangan> getAllOperasionalLapangan(String token){
        String getOperasionalUrl = "http://34.142.212.224:60/gor/closed-lapangan";
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<OperasionalLapangan[]> responseOperasional = restTemplate.exchange(
                getOperasionalUrl, HttpMethod.GET, http, OperasionalLapangan[].class);
        OperasionalLapangan[] arrayOfOperasional = responseOperasional.getBody();
        if (arrayOfOperasional == null){
            return new ArrayList<>();
        }
        return Arrays.asList(arrayOfOperasional);
    }
    private HttpHeaders getJSONHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return requestHeaders;
    }
}
