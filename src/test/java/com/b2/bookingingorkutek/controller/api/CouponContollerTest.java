package com.b2.bookingingorkutek.controller.api;


import com.b2.bookingingorkutek.controller.api.reservation.CouponController;
import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import com.b2.bookingingorkutek.service.KuponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponController.class)
@AutoConfigureMockMvc(addFilters = false)
class CouponContollerTest{
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private KuponService kuponService;
    @MockBean
    private RestTemplate restTemplate;
    @Test
    void testAccessDeleteCouponPost() throws Exception{
        mvc.perform(delete("/api/v1/frontend/coupon/delete/1")
                        .content("\"Deleted coupon\"")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("deleteCouponPost"));
    }
    @Test
    void testAccessCreateCouponPost() throws Exception{
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(new ResponseEntity<>(null, HttpStatusCode.valueOf(200)));
        mvc.perform(post("/api/v1/frontend/coupon/create")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createCouponPost"));

        when(restTemplate.postForEntity(anyString(), any(), any())).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(401), "[no body]"));
        mvc.perform(post("/api/v1/frontend/coupon/create")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(handler().methodName("createCouponPost"));

    }
}
