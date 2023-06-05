package com.b2.bookingingorkutek.controller.api;

import com.b2.bookingingorkutek.controller.api.reservation.StatusUpdateController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusUpdateController.class)
@AutoConfigureMockMvc(addFilters = false)
class StatusUpdateControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testAccessSetStatusUpdate() throws Exception{
        when(restTemplate.exchange(anyString(), any(), any(), eq(Object.class))).thenReturn(new ResponseEntity<>(null, HttpStatusCode.valueOf(200)));
        mvc.perform(put("/api/v1/frontend/status/update/1")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("setStatusUpdate"));

        when(restTemplate.exchange(anyString(), any(), any(), eq(Object.class))).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(401), "[no body]"));
        mvc.perform(put("/api/v1/frontend/status/update/1")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(handler().methodName("setStatusUpdate"));

    }
}
