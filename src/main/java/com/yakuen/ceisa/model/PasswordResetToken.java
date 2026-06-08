package com.yakuen.ceisa.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PasswordResetToken {
    private Long id;
    private Long userId;
    private String token;
    private LocalDateTime expiresAt;
    private Integer used;
    private LocalDateTime createdAt;
}
