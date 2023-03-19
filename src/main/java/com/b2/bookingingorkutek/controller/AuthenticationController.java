package com.b2.bookingingorkutek.controller;


import com.b2.bookingingorkutek.dto.AuthenticationRequest;
import com.b2.bookingingorkutek.dto.AuthenticationResponse;
import com.b2.bookingingorkutek.dto.RegisterRequest;
import com.b2.bookingingorkutek.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register-body")
    public ResponseEntity<AuthenticationResponse> register_body (
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
    public ResponseEntity<AuthenticationResponse> login (
            @RequestParam String email,
            @RequestParam String password
    ) {
        AuthenticationRequest request = AuthenticationRequest.builder().email(email).password(password).build();
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/login-body")
    public ResponseEntity<AuthenticationResponse> login_body (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
