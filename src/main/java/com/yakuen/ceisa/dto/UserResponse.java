package com.yakuen.ceisa.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponse {
    Long id;
    String username;
    String email;
    String fullName;
    Boolean active;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<String> roles;
}
