package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
@CrossOrigin
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    @GetMapping("user-checking")
    public ResponseEntity<ModelUserDto> userChecking(){
        ModelUserDto modelUserDto = authorizationService.createModelUserDto();
        return ResponseEntity.ok(modelUserDto);
    }
}
