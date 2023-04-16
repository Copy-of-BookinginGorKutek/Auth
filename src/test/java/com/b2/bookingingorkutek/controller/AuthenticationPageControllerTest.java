package com.b2.bookingingorkutek.controller;


import com.b2.bookingingorkutek.service.AuthenticationService;
import com.b2.bookingingorkutek.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationPageControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithAnonymousUser
    void testLoginPageNotLoggedInShouldBeOk() throws Exception {
        mvc.perform(get("/auth-page/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "password", roles = "USER")
    void testLoginPageWhenLoggedIn() throws Exception {
        mvc.perform(get("/auth-page/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testRegisterPageNotLoggedInShouldBeOk() throws Exception {
        mvc.perform(get("/auth-page/register"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "password", roles = "USER")
    void testRegisterPageWhenLoggedIn() throws Exception {
        mvc.perform(get("/auth-page/register"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
