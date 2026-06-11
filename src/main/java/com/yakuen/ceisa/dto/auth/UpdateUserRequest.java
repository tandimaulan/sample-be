package com.yakuen.ceisa.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class UpdateUserRequest {

  @NotBlank
  @Email
  @Size(max = 100)
  private String email;

  @NotBlank
  @Size(max = 100)
  private String fullName;

  private Boolean active;

  private List<String> roleCodes;
}
