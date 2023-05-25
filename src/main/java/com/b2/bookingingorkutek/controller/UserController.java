package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.model.User;
import com.b2.bookingingorkutek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.findAllUser());
    }
}
