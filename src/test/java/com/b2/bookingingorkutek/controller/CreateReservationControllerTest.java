package com.b2.bookingingorkutek.controller;


import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CreateReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreateReservationControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    private ModelUserDto modelUserDto;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private RestTemplate restTemplate;
    @Test
    void testAccessCreateReservation() throws Exception{
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(new ResponseEntity<>(null, HttpStatusCode.valueOf(200)));
        mvc.perform(post("/create-reservation/create")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createReservationPost"));
    }
}
