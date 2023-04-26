package com.b2.bookingingorkutek.controller;


import com.b2.bookingingorkutek.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationPageController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservationPageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private RestTemplate restTemplate;
    @Test
    void testAccessingCreateReservationPageWithRoleUser() throws Exception{
        when(restTemplate.postForEntity(anyString(), any(Object.class), eq(String.class))).thenReturn(new ResponseEntity<>("User", HttpStatusCode.valueOf(200)));
        mvc.perform(get("/reservation-page/create"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createReservation"));
    }
    @Test
    void testAccessingCreateReservationPageWithoutRoleUser() throws Exception{
        when(restTemplate.postForEntity(anyString(), any(Object.class), eq(String.class))).thenReturn(new ResponseEntity<>("Admin", HttpStatusCode.valueOf(200)));
        mvc.perform(get("/reservation-page/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("createReservation"));
    }


}
