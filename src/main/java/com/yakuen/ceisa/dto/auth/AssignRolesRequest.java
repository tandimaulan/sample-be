package com.yakuen.ceisa.dto.auth;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AssignRolesRequest {

    @NotEmpty
    private List<String> roleCodes;
}
