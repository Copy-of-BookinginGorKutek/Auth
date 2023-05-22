package com.b2.bookingingorkutek.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Integer id;
    private String emailUser;
    private String message;
    private LocalDateTime timestamp;
}
