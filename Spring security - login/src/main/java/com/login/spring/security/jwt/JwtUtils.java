package com.login.spring.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Autowired
    private JwtConfiguration jwtConfiguration;

    public String getUsernameFromJwt(String jwt){
        return Jwts.parserBuilder().setSigningKey(jwtConfiguration.getSecretKey().getBytes()).build()
                .parseClaimsJws(jwt).getBody().getSubject();
    }

    public String generateJwtToken(Authentication authentication){

        final String token = Jwts
                .builder()
                .setSubject(authentication.getName())//header
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationDays())))
                .signWith(Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes()))
                .compact();

        return token;
    }

    public boolean validateJwtToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(jwtConfiguration.getSecretKey().getBytes()).build().parseClaimsJws(token);//validate jwt
            return true;
        }
        catch (JwtException e){
            logger.error(String.format("Jwt %s cannot be trusted ",token));
        }
        return false;

    }


}
