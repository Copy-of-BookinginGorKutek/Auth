package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.dto.AuthenticationRequest;
import com.b2.bookingingorkutek.dto.AuthenticationResponse;
import com.b2.bookingingorkutek.dto.RegisterRequest;
import com.b2.bookingingorkutek.exceptions.UserAlreadyExistException;
import com.b2.bookingingorkutek.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    AuthenticationService authenticationService;// =
     //       new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);

    @Test
    void testAuthServiceRegisterShouldAddToRepository() throws Exception {
        RegisterRequest request =
                new RegisterRequest("aaa", "bbb", "test@email.com", "abab", "admin");
        authenticationService.register(request);
        var checkUser = userRepository.findByEmail("test@email.com");
        assertNotNull(checkUser);
    }

    @Test
    void testFindByWrongEmail() throws Exception {
        var checkUser = userRepository.findByEmail("randomemail@email.com").orElse(null);
        assertNull(checkUser);
    }

    @Test
    void testAuthServiceRegisterTwiceShouldThrowsError() throws Exception {
        RegisterRequest request =
                new RegisterRequest("aaa", "bbb", "test1@email.com", "abab", "admin");
        AuthenticationService authenticationService = (AuthenticationService) applicationContext.getBean("authenticationService");
        authenticationService.register(request);
        assertThrows(UserAlreadyExistException.class, () -> authenticationService.register(request));
    }

    @Test
    void testAuthenticateMethod() throws Exception {
        RegisterRequest request =
                new RegisterRequest("aaa", "bbb", "test2@email.com", "abab", "admin");
        AuthenticationService authenticationService = (AuthenticationService) applicationContext.getBean("authenticationService");
        authenticationService.register(request);
        AuthenticationRequest req = new AuthenticationRequest("test2@email.com", "abab");
        AuthenticationResponse secRes = authenticationService.authenticate(req);
        String email = authenticationService.getJwtService().extractUsername(secRes.getToken());
        assertEquals("test2@email.com", email);
    }

}
