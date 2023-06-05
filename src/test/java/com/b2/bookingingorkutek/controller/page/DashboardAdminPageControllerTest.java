package com.b2.bookingingorkutek.controller.page;

import com.b2.bookingingorkutek.controller.page.reservation.DashboardAdminPageController;
import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardAdminPageController.class)
@AutoConfigureMockMvc(addFilters = false)
class DashboardAdminPageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private JwtService jwtService;

    private List<Reservation> reservationList;
    private Reservation reservation;
    private ModelUserDto admin;
    private ModelUserDto user;
    private ModelUserDto anonymous;

    @BeforeEach
    void setup(){
        reservation = Reservation.builder()
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
        reservationList = new ArrayList<>();
        reservationList.add(reservation);

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
    }

    @Test
    void whenUserRoleIsNotAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/admin-page/dashboard"))
                .andExpect(handler().methodName("dashboardAdmin"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/admin-page/dashboard"))
                .andExpect(handler().methodName("dashboardAdmin"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testAccesingWithRoleUserAndNoReservation() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        when(reservationService.getAllReservasi(anyString())).thenReturn(List.of());
        mvc.perform(get("/admin-page/dashboard"))
                .andExpect(handler().methodName("dashboardAdmin"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("No Reservation for Today")));
    }

    @Test
    void testAccesingWithRoleUser() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        when(reservationService.getAllReservasi(anyString())).thenReturn(reservationList);
        mvc.perform(get("/admin-page/dashboard"))
                .andExpect(handler().methodName("dashboardAdmin"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(reservation.getEmailUser())))
                .andExpect(content().string(containsString("Menunggu Pembayaran")));
    }

}
