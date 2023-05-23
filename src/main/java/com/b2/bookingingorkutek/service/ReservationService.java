package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.reservation.Reservation;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Generated
public class ReservationService {
    @Autowired
    RestTemplate restTemplate;

    public List<Reservation> getSelf(String emailUser, String token){
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<List<Reservation>> httpEntity = new HttpEntity<>(requestHeaders);
        String url = String.format("http://34.142.212.224:60/reservation/get-self?emailUser=%s", emailUser);
        try {
            ResponseEntity<Reservation[]> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, httpEntity, Reservation[].class);
            return List.of(Objects.requireNonNull(responseEntity.getBody()));
        }catch(Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    public Reservation getReservasiById(Integer id, String token){
        String getReservationUrl = "http://34.142.212.224:60/reservation/get/" + id;
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<Reservation> response = restTemplate.exchange(
                getReservationUrl, HttpMethod.GET, http, Reservation.class);
        return response.getBody();
    }

    public List<Reservation> getReservasiByDate(String date, String token){
        String getTodayReservationUrl = "http://34.142.212.224:60/reservation/get-reservasi-by-date/" + date;
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<Reservation[]> responseTodayReservation = restTemplate.exchange(
                getTodayReservationUrl, HttpMethod.GET, http, Reservation[].class);
        Reservation[] arrayOfTodayReservation = responseTodayReservation.getBody();
        if (arrayOfTodayReservation == null){
            return new ArrayList<>();
        }
        return Arrays.asList(arrayOfTodayReservation);
    }

    public List<Reservation> getAllReservasi(String token){
        String getReservationUrl = "http://34.142.212.224:60/reservation/get-all";
        HttpHeaders requestHeaders = getJSONHttpHeaders(token);
        HttpEntity<Object> http = new HttpEntity<>(requestHeaders);

        ResponseEntity<Reservation[]> responseReservation = restTemplate.exchange(
                getReservationUrl, HttpMethod.GET, http, Reservation[].class);
        Reservation[] arrayOfReservation = responseReservation.getBody();
        if (arrayOfReservation == null){
            return new ArrayList<>();
        }
        return Arrays.asList(arrayOfReservation);
    }

    private HttpHeaders getJSONHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return requestHeaders;
    }
}
