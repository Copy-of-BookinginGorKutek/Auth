package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-notification")
public class UserNotificationController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserNotificationService userNotificationService;
    @GetMapping("/get/{email}")
    public ResponseEntity<Object> getSelfNotification(@PathVariable String email, @CookieValue(name = "token", defaultValue = "") String token){
        List<Object> notifications = userNotificationService.getSelf(email, token);
        return ResponseEntity.ok(notifications);
    }

}
