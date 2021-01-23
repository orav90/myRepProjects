package com.login.spring.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.login.spring.model.RoleType;
import com.login.spring.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByType(RoleType type);
}
