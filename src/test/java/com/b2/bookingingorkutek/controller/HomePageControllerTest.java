package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import com.b2.bookingingorkutek.service.OperasionalLapanganService;
import com.b2.bookingingorkutek.service.ReservationService;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomePageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HomePageControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private OperasionalLapanganService operasionalLapanganService;
    private ModelUserDto user;
    private ModelUserDto admin;
    private ModelUserDto anonymous;
    private List<OperasionalLapangan> operasionalLapanganList;
    private List<Reservation> reservationList;



    @BeforeEach
    void setup(){
        this.user = ModelUserDto.builder()
                .firstname("User")
                .lastname("User")
                .role("USER")
                .emailUser("user@test.com")
                .build();
        this.admin = ModelUserDto.builder()
                .firstname("Admin")
                .lastname("Admin")
                .role("ADMIN")
                .emailUser("admin@test.com")
                .build();
        this.anonymous = null;

        this.operasionalLapanganList = new ArrayList<>();
        this.operasionalLapanganList.add(OperasionalLapangan.builder()
                .id(1)
                .idLapangan(1)
                .tanggalLibur(new Date())
                .build()
        );

        this.reservationList = new ArrayList<>();
        this.reservationList.add(Reservation.builder()
                .id(1)
                .idLapangan(1)
                .emailUser("user@test.com")
                .buktiTransfer(null)
                .statusPembayaran("MENUNGGU_PEMBAYARAN")
                .harga(100000)
                .idKupon(null)
                .tambahanList(null)
                .waktuMulai(LocalDateTime.of(2020,11,11,5,0,0))
                .waktuBerakhir(LocalDateTime.of(2020, 11, 11, 6, 0, 0))
                .build()
        );


    }
    @Test
    void accessingWithAnonymous() throws Exception {
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/home/"))
                .andExpect(handler().methodName("homePage"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void accessingWithRoleAdmin() throws Exception {
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/home/"))
                .andExpect(handler().methodName("homePage"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void accessingWithRoleUser() throws Exception {
        when(operasionalLapanganService.getOperasionalLapanganByDate(anyString(), anyString())).thenReturn(operasionalLapanganList);
        when(reservationService.getReservasiByDate(anyString(), anyString())).thenReturn(reservationList);
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/home/"))
                .andExpect(handler().methodName("homePage"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("user@test.com")));
    }

    @Test
    void whenOperasionalLapanganAndReservationIsEmptyStillSuccess() throws Exception{
        when(operasionalLapanganService.getOperasionalLapanganByDate(anyString(), anyString())).thenReturn(new ArrayList<>());
        when(reservationService.getReservasiByDate(anyString(), anyString())).thenReturn(new ArrayList<>());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/home/"))
                .andExpect(handler().methodName("homePage"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("user@test.com")));
    }

}
