package com.yakuen.ceisa.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

  @NotNull
  private Long userId;

  @NotBlank
  private String currentPassword;

  @NotBlank
  @Size(min = 8, max = 100)
  private String newPassword;
}
