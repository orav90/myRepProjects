package com.login.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class VerificationToken {

    @Id
    private String id;

    private String token;

    private User user;

    private LocalDateTime expiresAt;
}
