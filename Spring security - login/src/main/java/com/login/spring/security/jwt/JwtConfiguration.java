package com.login.spring.security.jwt;

import com.google.common.net.HttpHeaders;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "application.jwt")
@Data
public class JwtConfiguration {

    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationDays;


    public String getAuthHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
