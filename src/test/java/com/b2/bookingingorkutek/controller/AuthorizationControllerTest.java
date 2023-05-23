package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthorizationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorizationControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private JwtService jwtService;
    private ModelUserDto admin;
    private ModelUserDto user;
    private ModelUserDto anonymous;

    @BeforeEach
    void setUp(){
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
    void userCheckingWithRoleUser() throws Exception{
        when(authorizationService.createModelUserDto()).thenReturn(this.user);
        mvc.perform(get("/authorization/user-checking"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().methodName("userChecking"));
    }

    @Test
    void userCheckingWithRoleAdmin() throws Exception{
        when(authorizationService.createModelUserDto()).thenReturn(this.admin);
        mvc.perform(get("/authorization/user-checking"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().methodName("userChecking"));
    }

    @Test
    void userCheckingWithAnonymousUser() throws Exception{
        when(authorizationService.createModelUserDto()).thenReturn(this.anonymous);
        mvc.perform(get("/authorization/user-checking"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().methodName("userChecking"));
    }


}
