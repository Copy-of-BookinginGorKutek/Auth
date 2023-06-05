package com.b2.bookingingorkutek.controller.api;
import com.b2.bookingingorkutek.controller.api.auth.AuthenticationController;
import com.b2.bookingingorkutek.dto.RegisterRequest;
import com.b2.bookingingorkutek.model.User;
import com.b2.bookingingorkutek.service.AuthenticationService;
import com.b2.bookingingorkutek.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
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
    void setup() {
        user = User.builder()
                .firstname("Cicak")
                .lastname("Bin Kadal")
                .email("cbkadal@email.com")
                .password("thisistheway")
                .role("USER")
                .build();
    }

    @Test
    void testPostRegisterSuccessful() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setFirstname(user.getFirstname());
        req.setLastname(user.getLastname());
        req.setEmail(user.getEmail());
        req.setPassword(user.getPassword());
        req.setRole(user.getRole());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson =ow.writeValueAsString(req);

        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("register"));
    }

    @Test
    void testPostLoginSuccessful() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setEmail(user.getEmail());
        req.setPassword(user.getPassword());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson =ow.writeValueAsString(req);

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("login"));
    }

}
