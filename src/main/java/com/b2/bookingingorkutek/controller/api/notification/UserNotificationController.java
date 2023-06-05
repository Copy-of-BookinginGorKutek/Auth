package com.b2.bookingingorkutek.controller.api.notification;

import com.b2.bookingingorkutek.model.notification.Notification;
import com.b2.bookingingorkutek.service.UserNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/frontend/user-notification")
public class UserNotificationController {
    @Autowired
    UserNotificationService userNotificationService;
    @Operation(summary = "Get all notifications by email (microservice call)")
    @GetMapping("/get/{email}")
    public ResponseEntity<List<Notification>> getSelfNotification(@PathVariable String email, @CookieValue(name = "token", defaultValue = "") String token){
        List<Notification> notifications = userNotificationService.getSelf(email, token);
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Delete notification by ID (microservice call)")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Integer id, @CookieValue(name = "token", defaultValue = "") String token){
        userNotificationService.deleteNotification(id, token);
        return ResponseEntity.ok("\"Deleted notification\"");
    }

}
