package com.b2.bookingingorkutek.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class AuthenticationResponse {
    private String token;
    private String role;

}
