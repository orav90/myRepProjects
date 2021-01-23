package com.login.spring.repository;

import com.login.spring.model.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken,String> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByToken(String token);
}
