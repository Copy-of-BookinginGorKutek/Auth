package com.b2.bookingingorkutek.controller.page;

import com.b2.bookingingorkutek.controller.page.reservation.OperasionalLapanganPageController;
import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.Lapangan;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OperasionalLapanganPageController.class)
@AutoConfigureMockMvc(addFilters = false)
class OperasionalLapanganPageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private OperasionalLapanganService operasionalLapanganService;

    private List<OperasionalLapangan> operasionalLapanganList;
    private ModelUserDto admin;
    private ModelUserDto user;
    private ModelUserDto anonymous;
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
        Instant instant = Instant.now();
        OperasionalLapangan operasionalLapangan = OperasionalLapangan.builder()
                .id(1)
                .idLapangan(2)
                .tanggalLibur(Date.from(instant))
                .build();
        operasionalLapanganList = new ArrayList<>();
        operasionalLapanganList.add(operasionalLapangan);
    }

    @Test
    void whenCreateOperasionalLapanganAndUserRoleIsNotAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/operasional-lapangan-page/create"))
                .andExpect(handler().methodName("createOperasionalLapangan"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/operasional-lapangan-page/create"))
                .andExpect(handler().methodName("createOperasionalLapangan"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void whenAccessingAllClosedLapanganAndUserRoleIsNotAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/operasional-lapangan-page/closed/all"))
                .andExpect(handler().methodName("allClosedLapangan"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/operasional-lapangan-page/closed/all"))
                .andExpect(handler().methodName("allClosedLapangan"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testAccesingCreateOperasionalLapangan() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/operasional-lapangan-page/create"))
                .andExpect(handler().methodName("createOperasionalLapangan"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Lapangan ID")))
                .andExpect(content().string(containsString("Closing Date")));
    }

    @Test
    void testAccessingAllClosedLapanganWhenOperasionalLapanganNotExist() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        when(operasionalLapanganService.getAllOperasionalLapangan(anyString())).thenReturn(List.of());
        mvc.perform(get("/operasional-lapangan-page/closed/all"))
                .andExpect(handler().methodName("allClosedLapangan"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("No Closed Court")));

    }

    @Test
    void testAccessingAllClosedLapanganWhenOperasionalLapanganExist() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        when(operasionalLapanganService.getAllOperasionalLapangan(anyString())).thenReturn(operasionalLapanganList);
        mvc.perform(get("/operasional-lapangan-page/closed/all"))
                .andExpect(handler().methodName("allClosedLapangan"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Operational ID")))
                .andExpect(content().string(containsString("Court ID")))
                .andExpect(content().string(containsString("Closed Date")));
    }
}
