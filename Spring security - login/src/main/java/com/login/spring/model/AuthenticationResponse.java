package com.login.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    //private String refreshToken;
    private ZonedDateTime expiresAt;
    private String username;
}
