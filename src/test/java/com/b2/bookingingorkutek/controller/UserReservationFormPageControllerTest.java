package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.controller.page.reservation.ReservationPageController;
import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import com.b2.bookingingorkutek.service.OperasionalLapanganService;
import com.b2.bookingingorkutek.service.ReservationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationPageController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserReservationFormPageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private OperasionalLapanganService operasionalLapanganService;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private AuthorizationService authorizationService;
    private ModelUserDto user;
    private ModelUserDto admin;
    private ModelUserDto anonymous;

    @BeforeEach
    void setup() {
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

        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = Reservation.builder()
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
                .build();
        reservationList.add(reservation);
        when(reservationService.getReservasiById(any(), anyString())).thenReturn(reservation);
        when(reservationService.getAllReservasi(any())).thenReturn(reservationList);
        when(reservationService.getReservasiByDate(any(), any())).thenReturn(reservationList);
        when(reservationService.getSelf(any(), any())).thenReturn(reservationList);

        List<OperasionalLapangan> operasionalLapanganList = new ArrayList<>();
        operasionalLapanganList.add(OperasionalLapangan.builder()
                .id(1)
                .idLapangan(1)
                .tanggalLibur(new Date())
                .build()
        );
        when(operasionalLapanganService.getOperasionalLapanganByDate(any(), any())).thenReturn(operasionalLapanganList);
        when(operasionalLapanganService.getAllOperasionalLapangan(any())).thenReturn(operasionalLapanganList);
    }
    @Test
    void whenAccessingGetUserReservationWithAnonymousOrRoleAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/user-reservation-page/get-self/1"))
                .andExpect(handler().methodName("getUserReservation"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/user-reservation-page/get-self/1"))
                .andExpect(handler().methodName("getUserReservation"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void whenAccessingGetUserReservationListWithAnonymousOrRoleAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/user-reservation-page/get-self"))
                .andExpect(handler().methodName("getUserReservationList"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/user-reservation-page/get-self"))
                .andExpect(handler().methodName("getUserReservationList"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void whenAccessingGetAllReservationAndCourtAvailabilityWithAnonymousOrRoleAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/user-reservation-page/get-all-reservation"))
                .andExpect(handler().methodName("getAllReservationsAndCourtAvailability"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/user-reservation-page/get-all-reservation"))
                .andExpect(handler().methodName("getAllReservationsAndCourtAvailability"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void whenAccessingGetAllReservationsAndCourtAvailabilityByDateWithAnonymousOrRoleAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/user-reservation-page/get-all-reservation/2020-11-11"))
                .andExpect(handler().methodName("getAllReservationsAndCourtAvailabilityByDate"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/user-reservation-page/get-all-reservation/2020-11-11"))
                .andExpect(handler().methodName("getAllReservationsAndCourtAvailabilityByDate"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void whenAccessingGetUserReservationWithRoleUserShouldSuccess() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/user-reservation-page/get-self/1"))
                .andExpect(handler().methodName("getUserReservation"))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void whenAccessingGetUserReservationListWithRoleUserShouldSuccess() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/user-reservation-page/get-self"))
                .andExpect(handler().methodName("getUserReservationList"))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void whenAccessingGetAllReservationAndCourtAvailabilityWithRoleUserShouldSuccess() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/user-reservation-page/get-all-reservation"))
                .andExpect(handler().methodName("getAllReservationsAndCourtAvailability"))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void whenAccessingGetAllReservationsAndCourtAvailabilityByDateWithRoleUserShouldSuccess() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/user-reservation-page/get-all-reservation/2020-11-11"))
                .andExpect(handler().methodName("getAllReservationsAndCourtAvailabilityByDate"))
                .andExpect(status().is2xxSuccessful());
    }



}
