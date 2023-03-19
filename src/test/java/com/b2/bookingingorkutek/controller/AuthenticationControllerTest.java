package com.b2.bookingingorkutek.controller;
import com.b2.bookingingorkutek.dto.AuthenticationRequest;
import com.b2.bookingingorkutek.dto.RegisterRequest;
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
    private RegisterRequest registerRequest;

    @BeforeEach
    void init() {
        user  = new User();
        user.setEmail("test@email.com");
        user.setPassword("test1234");
        user.setRole("USER");
        registerRequest = new RegisterRequest();
        registerRequest.setEmail(user.getEmail());
        registerRequest.setPassword(user.getPassword());
        registerRequest.setRole(user.getRole());
        authenticationService.register(registerRequest);
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

    @Test
    void testPostLoginSuccesfull() throws Exception {
        AuthenticationRequest req = new AuthenticationRequest();
        req.setEmail("test@email.com");
        req.setPassword("test1234");
        mvc.perform(post("/login-body").param(req.getEmail(), req.getPassword()))
                .andExpect(status().isOk());
    }





}
