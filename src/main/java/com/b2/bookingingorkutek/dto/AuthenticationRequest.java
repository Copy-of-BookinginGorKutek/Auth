package com.b2.bookingingorkutek.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class AuthenticationRequest {
    private String email;
    String password;

}
