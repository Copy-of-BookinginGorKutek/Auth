package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.User;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Generated
public class AuthorizationService {
    @Autowired
    RestTemplate restTemplate;

    public User getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken){
            return null;
        }
        return (User)securityContext.getAuthentication().getPrincipal();
    }

    public ModelUserDto createModelUserDto(){
        User user = getCurrentUser();
        if(user == null){
            return null;
        }
        return ModelUserDto
                .builder()
                .emailUser(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .build();
    }

    public ModelUserDto requestCurrentUser(String token){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(requestHeaders);
        try {
            ResponseEntity<ModelUserDto> responseEntity = restTemplate
                    .exchange("http://34.142.212.224:100/api/v1/auth/authorization/user-checking", HttpMethod.GET, httpEntity, ModelUserDto.class);
            return responseEntity.getBody();
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
