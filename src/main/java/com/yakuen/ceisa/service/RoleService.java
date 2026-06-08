package com.yakuen.ceisa.service;

import com.yakuen.ceisa.dto.CreateRoleRequest;
import com.yakuen.ceisa.model.Role;
import java.util.List;

public interface RoleService {

    Role createRole(CreateRoleRequest request);

    List<Role> getAllRoles();

    void assignRolesToUser(Long userId, List<String> roleCodes);

    List<Role> getUserRoles(Long userId);

    void assignDefaultRole(Long userId);
}
