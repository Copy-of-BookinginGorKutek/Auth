package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.model.notification.Notification;
import com.b2.bookingingorkutek.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-notification")
public class UserNotificationController {
    @Autowired
    UserNotificationService userNotificationService;
    @GetMapping("/get/{email}")
    public ResponseEntity<List<Notification>> getSelfNotification(@PathVariable String email, @CookieValue(name = "token", defaultValue = "") String token){
        List<Notification> notifications = userNotificationService.getSelf(email, token);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Integer id, @CookieValue(name = "token", defaultValue = "") String token){
        userNotificationService.deleteNotification(id, token);
        return ResponseEntity.ok("\"Deleted notification\"");
    }

}
