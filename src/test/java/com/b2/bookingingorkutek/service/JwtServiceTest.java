package com.b2.bookingingorkutek.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    @Test
    void testNewGeneratedTokenMustBeValid(){
        UserDetails user = new User("test", "test", new ArrayList<>());
        String token = jwtService.generateToken(new HashMap<>(), user);
        assertTrue(jwtService.isTokenValid(token, user));
    }
}
