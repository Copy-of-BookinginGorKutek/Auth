package com.b2.bookingingorkutek.controller;


import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = ReservationPageController.class)
//@AutoConfigureMockMvc(addFilters = false)
class ReservationPageControllerTest {
    @Test
    void test(){}
//    @Autowired
//    private MockMvc mvc;
//    @MockBean
//    private JwtService jwtService;
//    private ModelUserDto modelUserDto;
//    @MockBean
//    private AuthorizationService authorizationService;
//    @MockBean
//    private RestTemplate restTemplate;
//    @BeforeEach
//    void setup(){
//         modelUserDto = ModelUserDto.builder()
//                 .emailUser("test@email")
//                 .firstname("test")
//                 .lastname("test")
//                 .role("USER")
//                 .build();
//    }
//    @Test
//    void testAccessingCreateReservationPageWithRoleUser() throws Exception{
//        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto);
//        mvc.perform(get("/reservation-page/create"))
//                .andExpect(status().isOk())
//                .andExpect(handler().methodName("createReservation"));
//    }
//    @Test
//    void testAccessingCreateReservationPageWithoutRoleUser() throws Exception{
//        ModelUserDto modelUserDto2 = ModelUserDto.builder()
//                .emailUser("test@email")
//                .firstname("test")
//                .lastname("test")
//                .role("ADMIN")
//                .build();
//        when(authorizationService.requestCurrentUser(anyString())).thenReturn(modelUserDto2);
//        mvc.perform(get("/reservation-page/create"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(handler().methodName("createReservation"));
//    }



}
