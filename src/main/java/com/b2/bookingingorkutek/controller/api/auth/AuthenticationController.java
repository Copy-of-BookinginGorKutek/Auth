package com.b2.bookingingorkutek.controller.api.auth;


import com.b2.bookingingorkutek.dto.AuthenticationRequest;
import com.b2.bookingingorkutek.dto.AuthenticationResponse;
import com.b2.bookingingorkutek.dto.RegisterRequest;
import com.b2.bookingingorkutek.dto.RegisterResponse;
import com.b2.bookingingorkutek.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register new user or admin")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register (
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Log in user or admin")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
