package com.b2.bookingingorkutek.controller;


import com.b2.bookingingorkutek.controller.page.reservation.ReservationFormPageController;
import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.kupon.Kupon;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import com.b2.bookingingorkutek.service.KuponService;
import com.b2.bookingingorkutek.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationFormPageController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservationFormPageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    private ModelUserDto modelUserDto;
    private ModelUserDto modelUserDto2;
    private ModelUserDto modelUserDto3;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private KuponService kuponService;
    @MockBean
    private ReservationService reservationService;
    private List<Kupon> kuponList;
    @BeforeEach
    void setup(){
         modelUserDto = ModelUserDto.builder()
                 .emailUser("test@email")
                 .firstname("test")
                 .lastname("test")
                 .role("USER")
                 .build();
        modelUserDto2 = ModelUserDto.builder()
                .emailUser("test@email")
                .firstname("test")
                .lastname("test")
                .role("ADMIN")
                .build();
        modelUserDto3 = null;

        kuponList = new ArrayList<>();
        Kupon kupon = Kupon
                .builder()
                .id(1)
                .name("kupondiskon50")
                .percentageDiscounted(50)
                .build();
        kuponList.add(kupon);

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
        when(reservationService.getReservasiById(any(),any())).thenReturn(reservation);
    }
    @Test
    void testAccessingCreateReservationPageWithRoleUser() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto);
        when(kuponService.getAllKupon(any())).thenReturn(kuponList);
        mvc.perform(get("/reservation-page/create"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createReservation"))
                .andExpect(content().string(containsString("kupondiskon50")));
    }
    @Test
    void testAccessingCreateReservationPageWithoutRoleUser() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto2);
        mvc.perform(get("/reservation-page/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("createReservation"));
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto3);
        mvc.perform(get("/reservation-page/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("createReservation"));
    }
    @Test
    void testAccessingPayReservation() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto);
        mvc.perform(get("/reservation-page/pay/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().methodName("payReservation"));
    }
    @Test
    void testAccessingPayReservationWithoutRoleUser() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto2);
        mvc.perform(get("/reservation-page/pay/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("payReservation"));
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto3);
        mvc.perform(get("/reservation-page/pay/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("payReservation"));
    }
    @Test
    void testAcessingCreateReservationPageWithNoCouponExist() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto);
        when(kuponService.getAllKupon(any())).thenReturn(List.of());
        mvc.perform(get("/reservation-page/create"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createReservation"))
                .andExpect(content().string(containsString("No Coupon Used")));
    }



}
