package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.kupon.Kupon;
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
public class KuponService {
    @Autowired
    RestTemplate restTemplate;

    public List<Kupon> getAllKupon(String token){
        String getAllKuponUrl = "http://34.142.212.224:60/api/v1/gor/get-all-kupon";
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<Kupon[]> responseAllKupon = restTemplate.exchange(
                getAllKuponUrl, HttpMethod.GET, http, Kupon[].class);
        Kupon[] arrayOfAllKupon = responseAllKupon.getBody();
        List<Kupon> listOfAllKupon = new ArrayList<>();
        if (arrayOfAllKupon != null){
            listOfAllKupon = Arrays.asList(arrayOfAllKupon);
        }
        return listOfAllKupon;
    }

    public void deleteCoupon(Integer id, String token){
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestHeaders);
        String url = "http://34.142.212.224:60/api/v1/gor/delete-kupon/" + id;
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private HttpHeaders getJSONHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return requestHeaders;
    }
}
