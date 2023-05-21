package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class UserReservationService {
    @Autowired
    RestTemplate restTemplate;

    public List<Reservation> getSelf(String emailUser, String token){
        HttpHeaders requestHeaders = getHttpHeaders(token);
        HttpEntity<List<Reservation>> httpEntity = new HttpEntity<>(requestHeaders);
        String url = String.format("http://localhost:8082/reservation/get-self?emailUser=%s", emailUser);
        try {
            ResponseEntity<Reservation[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Reservation[].class);
            return List.of(Objects.requireNonNull(responseEntity.getBody()));
        }catch(Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    public Reservation getReservasiById(String emailUser, Integer idReservasi, String token){
        List<Reservation> reservationList = getSelf(emailUser, token);
        return reservationList.stream()
                .filter(reservation -> idReservasi.equals(reservation.getId()))
                .findAny()
                .orElse(null);
    }

    private HttpHeaders getHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        return requestHeaders;
    }
}
