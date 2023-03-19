package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.dto.AuthenticationRequest;
import com.b2.bookingingorkutek.dto.AuthenticationResponse;
import com.b2.bookingingorkutek.dto.RegisterRequest;
import com.b2.bookingingorkutek.exceptions.UserAlreadyExistException;
import com.b2.bookingingorkutek.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthenticationService authenticationService;

    @Test
    void testAuthServiceRegisterShouldAddToRepository() throws Exception {
        RegisterRequest request =
                new RegisterRequest("aaa", "bbb", "test@email.com", "abab", "admin");
        AuthenticationResponse res = authenticationService.register(request);
        var checkUser = userRepository.findByEmail("test@email.com");
        assertNotNull(checkUser);
    }

    @Test
    void testAuthServiceRegisterShouldAddToRepositoryVers2() throws Exception {
        RegisterRequest request =
                new RegisterRequest("aaa", "bbb", "test@email.com", "abab", "admin");
        AuthenticationResponse res = authenticationService.register(request);
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
                new RegisterRequest("aaa", "bbb", "test@email.com", "abab", "admin");
        authenticationService.register(request);
        assertThrows(UserAlreadyExistException.class, () -> authenticationService.register(request));
    }

    @Test
    void testAuthenticateMethod() throws Exception {
        RegisterRequest request =
                new RegisterRequest("aaa", "bbb", "test@email.com", "abab", "admin");
        AuthenticationResponse firstRes = authenticationService.register(request);
        AuthenticationRequest req = new AuthenticationRequest("test@email.com", "abab");
        AuthenticationResponse secRes = authenticationService.authenticate(req);
        String resToken = firstRes.getToken();
        String reqToken = secRes.getToken();
        assertEquals(resToken, reqToken);
    }

}
