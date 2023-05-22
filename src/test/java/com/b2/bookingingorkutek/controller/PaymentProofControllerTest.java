package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.PaymentProofRequest;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentProofController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentProofControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private RestTemplate restTemplate;
    private PaymentProofRequest paymentProofRequest;
    private ObjectMapper mapper;
    @BeforeEach
    void setup(){
        this.mapper = new ObjectMapper();
        this.paymentProofRequest = PaymentProofRequest.builder()
                .url("Http://test.com")
                .build();
    }

    @Test
    void testSendPaymentProofSuccess() throws Exception{
        when(restTemplate.exchange(anyString(), any(), any(), eq(Object.class))).thenReturn(new ResponseEntity<>(new Reservation(), HttpStatusCode.valueOf(200)));
        mvc.perform(post("/pay/1")
                .content(mapper.writeValueAsString(paymentProofRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().methodName("sendPaymentProof"))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void testSendPaymentProofFail() throws Exception{
        when(restTemplate.exchange(anyString(), any(), any(), eq(Object.class))).thenThrow(new HttpServerErrorException(HttpStatusCode.valueOf(403)));
        mvc.perform(post("/pay/1")
                        .content(mapper.writeValueAsString(paymentProofRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().methodName("sendPaymentProof"))
                .andExpect(status().is4xxClientError());
    }

}
