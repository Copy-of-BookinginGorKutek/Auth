package com.b2.bookingingorkutek.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private String role;

}
