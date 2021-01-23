package com.login.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationMail {
    private String name;
    private String recipient;
    private String subject;
    private String tokenUrl;
}
