package com.yakuen.ceisa.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRoleRequest {

    @NotBlank
    @Size(max = 50)
    private String roleCode;

    @NotBlank
    @Size(max = 100)
    private String roleName;
}
