package com.b2.bookingingorkutek.controller;
import com.b2.bookingingorkutek.model.User;
import com.b2.bookingingorkutek.service.AuthenticationService;
import com.b2.bookingingorkutek.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.security.test.context.support.WithMockUser;


@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    private User user;

    @BeforeEach
    void init() {
        user  = new User();
        user.setEmail("test@email.com");
        user.setPassword("test1234");
        user.setRole("USER");
    }
    @Test
    @WithAnonymousUser
    void testRegisterPageShouldBeOK() throws Exception {
        mvc.perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testLoginPageShouldBeOK() throws Exception {
        mvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
