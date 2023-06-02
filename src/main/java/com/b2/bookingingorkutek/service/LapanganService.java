package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.lapangan.Lapangan;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Generated
public class LapanganService {

    @Autowired
    RestTemplate restTemplate;

    public List<Lapangan> getAllLapangan(String token){
        String getLapanganUrl = "http://34.142.212.224:60/reservation/get-all";
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<Lapangan[]> responseLapangan = restTemplate.exchange(
                getLapanganUrl, HttpMethod.GET, http, Lapangan[].class);
        Lapangan[] arrayOfLapangan = responseLapangan.getBody();
        if (arrayOfLapangan == null){
            return new ArrayList<>();
        }
        return Arrays.asList(arrayOfLapangan);
    }

    private HttpHeaders getJSONHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return requestHeaders;
    }
}
