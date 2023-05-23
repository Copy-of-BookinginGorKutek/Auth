package com.b2.bookingingorkutek.model.notification;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Notification {
    private Integer id;
    private String emailUser;
    private String message;
    private LocalDateTime timestamp;
}
