package com.yakuen.ceisa.service.auth;

import java.util.List;

import com.yakuen.ceisa.dto.auth.CreateRoleRequest;
import com.yakuen.ceisa.model.auth.Role;

public interface RoleService {

    Role createRole(CreateRoleRequest request);

    List<Role> getAllRoles();

    void assignRolesToUser(Long userId, List<String> roleCodes);

    List<Role> getUserRoles(Long userId);

    void assignDefaultRole(Long userId);
}
