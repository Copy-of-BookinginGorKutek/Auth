package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.reservation.Reservasi;
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

    public List<Reservasi> getSelf(String emailUser, String token){
        HttpHeaders requestHeaders = getHttpHeaders(token);
        HttpEntity<List<Reservasi>> httpEntity = new HttpEntity<>(requestHeaders);
        String url = String.format("http://34.142.212.224:60/reservation/get-self?emailUser=%s", emailUser);
        try {
            ResponseEntity<Reservasi[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Reservasi[].class);
            return List.of(Objects.requireNonNull(responseEntity.getBody()));
        }catch(Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    public Reservasi getReservasiById(String emailUser, Integer idReservasi, String token){
        List<Reservasi> reservasiList = getSelf(emailUser, token);
        return reservasiList.stream()
                .filter(reservasi -> idReservasi.equals(reservasi.getId()))
                .findAny()
                .orElse(null);
    }

    private HttpHeaders getHttpHeaders(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        return requestHeaders;
    }
}
