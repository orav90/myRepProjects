package com.login.spring.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data
public class Role {
  @Id
  private String id;

  private RoleType type;

  public Role() {

  }

  public Role(RoleType type) {
    this.type = type;
  }

}
