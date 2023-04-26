package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
public class AuthorizationController {
    private final UserDetailsService userDetailsService;
    @PostMapping("/get-role")
    public ResponseEntity<String> getRole(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken){
            return ResponseEntity.ok("Anonymous");
        }
        if (securityContext.getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.ok("Admin");
        }
        return ResponseEntity.ok("User");
    }

    @PostMapping("user-checking")
    public ResponseEntity<User> userChecking(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken){
            return ResponseEntity.badRequest().body(null);
        }
        return null;
    }
}
