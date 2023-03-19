package com.b2.bookingingorkutek.controller;


import com.b2.bookingingorkutek.dto.AuthenticationRequest;
import com.b2.bookingingorkutek.dto.AuthenticationResponse;
import com.b2.bookingingorkutek.dto.RegisterRequest;
import com.b2.bookingingorkutek.model.User;
import com.b2.bookingingorkutek.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import java.io.IOException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register-body")
    public ResponseEntity<AuthenticationResponse> registerBody (
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role
    ) {
        RegisterRequest request = RegisterRequest
                .builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .role(role)
                .build();
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public void login (
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response
    ) throws IOException {
        AuthenticationRequest request = AuthenticationRequest.builder().email(email).password(password).build();
        AuthenticationResponse authResponse = authenticationService.authenticate(request);
        Cookie cookie = new Cookie("token", authResponse.getToken());
        cookie.setSecure(true);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/dum/test");
    }

    @PostMapping("/login-body")
    public ResponseEntity<AuthenticationResponse> loginBody (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }



}
