package com.yakuen.ceisa.model.auth;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class User {

  private Long id;
  private String username;
  private String email;
  private String passwordHash;
  private String fullName;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
