package com.b2.bookingingorkutek.service;



import com.b2.bookingingorkutek.dto.AuthenticationRequest;
import com.b2.bookingingorkutek.dto.AuthenticationResponse;
import com.b2.bookingingorkutek.dto.RegisterRequest;
import com.b2.bookingingorkutek.dto.RegisterResponse;
import com.b2.bookingingorkutek.exceptions.UserAlreadyExistException;
import com.b2.bookingingorkutek.model.User;
import com.b2.bookingingorkutek.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Data
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        var checkUser = userRepository.findByEmail(request.getEmail()).orElse(null);

        if(checkUser != null) {
            throw new UserAlreadyExistException();
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .active(true)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        return RegisterResponse.builder().message("success").build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole());
        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder().token(jwtToken).role(user.getRole()).build();
    }

}
