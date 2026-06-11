package com.yakuen.ceisa.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class AssignRolesRequest {

  @NotEmpty
  private List<String> roleCodes;
}
