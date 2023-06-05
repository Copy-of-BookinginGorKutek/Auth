package com.b2.bookingingorkutek.controller.page;
import com.b2.bookingingorkutek.controller.page.reservation.LapanganPageController;
import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import com.b2.bookingingorkutek.service.ReservationService;
import com.netflix.discovery.converters.Auto;
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

@WebMvcTest(LapanganPageController.class)
@AutoConfigureMockMvc(addFilters = false)
class LapanganPageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthorizationService authorizationService;

    private ModelUserDto user;
    private ModelUserDto admin;
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
    }

    @Test
    void whenUserRoleIsNotAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/lapangan-page/create"))
                .andExpect(handler().methodName("createLapangan"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/lapangan-page/create"))
                .andExpect(handler().methodName("createLapangan"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testWithRoleAdmin() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/lapangan-page/create"))
                .andExpect(handler().methodName("createLapangan"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Add a new court to the system.")));
    }
}
